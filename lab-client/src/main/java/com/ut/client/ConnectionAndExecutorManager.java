package com.ut.client;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ut.common.commands.Command;
import com.ut.common.commands.CommandManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.BodyCommandWithSpMar;
import com.ut.common.util.IOManager;
import com.ut.common.util.Message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class ConnectionAndExecutorManager {
    private final static int DEFAULT_TIME_OUT = 5000;
    private SendManager sendManager;
    private ReceiveManager receiveManager;
    private Message message;
    private final CommandManager commandManager = CommandManager.getDefaultCommandManager(null, null);


    public boolean connectToServer(String address, String portString) {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            Integer port = Integer.parseInt(portString);
            DatagramSocket socket = new DatagramSocket();
            receiveManager = new ReceiveManager(socket);
            sendManager = new SendManager(inetAddress, socket, port);
            receiveManager.setTimeout(DEFAULT_TIME_OUT);
            return tryConnect();
        } catch (UnknownHostException | SocketException | NumberFormatException e) {
            System.out.println("Error in creating connection.");
        } catch (IOException e) {
            System.out.println("Uppss... Something with input/output went wrong.");
        }
        return false;
        // return true;
    }

    private boolean tryConnect() throws IOException {
        sendManager.sendMessage(new Message("connect_to_server", null));
        CommandResult result = receiveManager.receiveMessage();
        return Objects.nonNull(result);
    }

    public boolean login(String username, String password) {
        try {
            Command command = commandManager.getCommand("login");
            BodyCommand bodyCommand = command.requestBodyCommand(new String[]{username, password}, null);
            message = new Message();
            message.setBodyCommand(bodyCommand);
            message.setCommand("login");
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                log.info(result.getResultStatus().toString());
                message.setUser((User) result.getData());
                log.info(message.getUser().toString() + " " + message.getUser().getAuthenticationStatus());
                return result.getResultStatus();
            }
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
        }
        // return true;
        return false;
    }

    public boolean signup(String username, String password) {
        try {
            Command command = commandManager.getCommand("sign_up");
            BodyCommand bodyCommand = command.requestBodyCommand(new String[]{username, password}, null);
            message = new Message();
            message.setBodyCommand(bodyCommand);
            message.setCommand("sign_up");
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                log.info(result.getResultStatus().toString());
                message.setUser((User) result.getData());
                log.info(message.getUser().toString() + " " + message.getUser().getAuthenticationStatus());
                return result.getResultStatus();
            }
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
        }
        return false;
    }
    
    public CommandResult executeCommand(String nameCommand, SpaceMarine spaceMarine, Object data)  {
        if ("execute_script".equals(nameCommand)) {
            File scriptFile = (File) data;
            IOManager ioManager = new IOManager();
            ioManager.turnOnFileMode(scriptFile.getAbsolutePath());
            Console console = new Console(ioManager, receiveManager, sendManager, message);
            try {
                while (console.isWorkState()) {
                    return console.readNewCommand();
                }
            } catch (IOException e) {

            }
        }
        BodyCommandWithSpMar bodyCommand = new BodyCommandWithSpMar(data, spaceMarine);
        message.setCommand(nameCommand);
        message.setBodyCommand(bodyCommand);
        log.info(message.getUser().getUsername() + " " + message.getUser().getAuthenticationStatus());
        try {
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                log.info(result.getResultStatus().toString());
                log.info(result.getMessageResult());
                return result;
            }
            return new CommandResult("error", null, false, "Connection was lost");
        } catch (IOException e) {
            return new CommandResult("error", null, false, "IOException");
        }
    }

    // TODO Как лучше: один универсальный метод или на каждую команду свой метод?
}
