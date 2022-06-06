package com.ut.server.server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


import com.ut.common.commands.Command;
import com.ut.common.commands.CommandManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.Message;
import com.ut.server.util.SQLSpMarCollManager;
import com.ut.server.util.SQLUserManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerApp {
    private final Scanner scanner;
    private CommandManager commands;
    private SQLSpMarCollManager sqlSpMarCollManager;
    private SQLUserManager userManager;
    private Connection connectionDB;
    private SocketAddress client;
    private SocketAddress address;
    private DatagramChannel channel;
    private boolean isWorkState;
    private ReceiveManager receiveManager;
    private ThreadPoolExecutor handleMessExecutorService;
    private ThreadPoolExecutor sendCommandRExecutorService;

    public ServerApp(InetAddress addr, int port, Connection connDB, int numberOfTreads) throws SQLException {
        this.address = new InetSocketAddress(addr, port);
        this.connectionDB = connDB;
        userManager = new SQLUserManager(connectionDB);
        sqlSpMarCollManager = new SQLSpMarCollManager(connectionDB);
        commands = CommandManager.getDefaultCommandManager(sqlSpMarCollManager, userManager);
        handleMessExecutorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfTreads);
        sendCommandRExecutorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfTreads);
    }

    {
        isWorkState = true;
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        try (DatagramChannel datachannel = DatagramChannel.open()) {
            this.channel = datachannel;
            log.info("Open datagram channel. Server started working.");
            receiveManager = new ReceiveManager(channel, client);
            try {
                channel.bind(address);
                log.info(channel.getLocalAddress().toString());
            } catch (BindException z) {
                log.error("Cannot assign requested address.", z);
                isWorkState = false;
            }
            channel.configureBlocking(false);
            Message mess;
            while (isWorkState) {
                checkCommands();
                mess = receiveManager.receiveMessage();
                if (Objects.isNull(mess)) {
                    continue;
                }
                new ClientThread(mess, new SendManager(channel, receiveManager.getClient())).start();
            }
        }
    }

    public CommandResult execute(Message mess) {
        Command command = commands.getMap().get(mess.getCommand());
        BodyCommand data = mess.getBodyCommand();
        CommandResult result;
        if (command.requiresAuthen()) {
            if (mess.getUser().getAuthenticationStatus()) {
                log.info(mess.getUser().getUsername() + " " + mess.getUser().getAuthenticationStatus());
                result = command.run(data, mess.getUser());
            } else {
                log.info(mess.getUser().getUsername() + " " + mess.getUser().getAuthenticationStatus());
                result = new CommandResult(command.getName(), null, false, "User verification failed.");
            }
        } else {
            result = command.run(data, mess.getUser());
        }
        return result;
    }

    public void checkCommands() throws IOException {
        if (System.in.available() > 0) {
            String line = "";
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                line = "exit";
            }
            if ("exit".equals(line)) {
                log.info("Server finished working.");
                handleMessExecutorService.shutdown();
                sendCommandRExecutorService.shutdown();
                isWorkState = false;
            }
        }
    }

    private class ClientThread {
        private final Message mess;
        private final SendManager sendManager;

        ClientThread(Message mess, SendManager sendManager) {
            this.mess = mess;
            this.sendManager = sendManager;
        }

        private void start() {
            handleMessExecutorService.submit(() -> {
                CommandResult commandResult = execute(mess);
                log.info(commandResult.getMessageResult());
                sendCommandRExecutorService.submit(() -> sendManager.sendCommResult(commandResult));
            });
        }
    }
}
