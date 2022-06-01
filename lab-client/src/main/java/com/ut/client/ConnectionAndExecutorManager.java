package com.ut.client;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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

import exeptions.ConnectionLostExeption;
import lombok.Getter;

@Getter
public final class ConnectionAndExecutorManager {
    private static final int DEFAULT_TIME_OUT = 5000;
    private SendManager sendManager;
    private ReceiveManager receiveManager;
    private Message message;
    private final CommandManager commandManager = CommandManager.getDefaultCommandManager(null, null);
    private final List<String> commendsForGUI = new ArrayList<String>();
    private List<SpaceMarine> lastListSpaceMarines;

    {
        commendsForGUI.add("help");
        commendsForGUI.add("info");
        commendsForGUI.add("show");
        commendsForGUI.add("add");
        commendsForGUI.add("add_if_min");
        commendsForGUI.add("update");
        commendsForGUI.add("remove_by_id");
        commendsForGUI.add("remove_greater");
        commendsForGUI.add("remove_lower");
        commendsForGUI.add("group_counting_by_name");
        commendsForGUI.add("print_descending");
        commendsForGUI.add("count_by_loyal");
        commendsForGUI.add("execute_script");
        commendsForGUI.add("clear");
    }


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
    }

    private boolean tryConnect() throws IOException {
        sendManager.sendMessage(new Message("connect_to_server", null));
        CommandResult result = receiveManager.receiveMessage();
        return Objects.nonNull(result);
    }

    public boolean login(String username, String password) throws ConnectionLostExeption {
        try {
            Command command = commandManager.getCommand("login");
            BodyCommand bodyCommand = command.requestBodyCommand(new String[]{username, password}, null);
            message = new Message();
            message.setBodyCommand(bodyCommand);
            message.setCommand("login");
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                message.setUser((User) result.getData());
                return result.getResultStatus();
            }
            throw new ConnectionLostExeption();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
       }
        return false;
    }

    public boolean signup(String username, String password) throws ConnectionLostExeption {
        try {
            Command command = commandManager.getCommand("sign_up");
            BodyCommand bodyCommand = command.requestBodyCommand(new String[]{username, password}, null);
            message = new Message();
            message.setBodyCommand(bodyCommand);
            message.setCommand("sign_up");
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                message.setUser((User) result.getData());
                return result.getResultStatus();
            }
            throw new ConnectionLostExeption();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        BodyCommandWithSpMar bodyCommand = new BodyCommandWithSpMar(data, spaceMarine);
        message.setCommand(nameCommand);
        message.setBodyCommand(bodyCommand);
        try {
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                return result;
            }
            return new CommandResult("error", null, false, "Connection was lost");
        } catch (IOException e) {
            return new CommandResult("error", null, false, "IOException");
        }
    }

    @SuppressWarnings({"unchecked"})
    public List<SpaceMarine> getListFromServer() throws ConnectionLostExeption {
        CommandResult result = executeCommand("show", null, null);
        if (Objects.isNull(result)) {
            throw new ConnectionLostExeption();
        }
        if (!result.getResultStatus()) {
            throw new ConnectionLostExeption();
        }
        if (Objects.isNull(result.getData())) {
            lastListSpaceMarines = new ArrayList<>();
            return new ArrayList<>();
        }
        lastListSpaceMarines = (List<SpaceMarine>) result.getData();
        return (List<SpaceMarine>) result.getData();
    }

    public List<SpaceMarine> getLastList() {
        return lastListSpaceMarines;
    }

    public String getUsername() {
        return message.getUser().getUsername();
    }
}
