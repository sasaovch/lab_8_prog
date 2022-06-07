package com.ut.client;

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
import com.ut.common.util.Message;
import com.ut.exeptions.ConnectionLostExeption;
import com.ut.util.ConstantsLanguage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public final class ConnectionAndExecutorManager {
    private static final int DEFAULT_TIME_OUT = 1000;
    private SendManager sendManager;
    private ReceiveManager receiveManager;
    private Message message;
    private final CommandManager commandManager = CommandManager.getDefaultCommandManager(null, null);
    private final List<String> commendsForGUI = new ArrayList<String>();
    private List<SpaceMarine> lastListSpaceMarines;

    {
        commendsForGUI.add(ConstantsLanguage.HELP_COMMAND);
        commendsForGUI.add(ConstantsLanguage.INFO_COMMAND);
        commendsForGUI.add(ConstantsLanguage.SHOW_COMMAND);
        commendsForGUI.add(ConstantsLanguage.ADD_COMMAND);
        commendsForGUI.add(ConstantsLanguage.ADD_IF_MIN_COMMAND);
        commendsForGUI.add(ConstantsLanguage.UPDATE_COMMAND);
        commendsForGUI.add(ConstantsLanguage.REMOVE_BY_ID_COMMAND);
        commendsForGUI.add(ConstantsLanguage.REMOVE_GREATER_COMMAND);
        commendsForGUI.add(ConstantsLanguage.REMOVE_LOWER_COMMAND);
        commendsForGUI.add(ConstantsLanguage.GROUP_COUNT_BY_NAME_COMMAND);
        commendsForGUI.add(ConstantsLanguage.PRINT_DESCENDING_COMMAND);
        commendsForGUI.add(ConstantsLanguage.COUNT_BY_LOAYL_COMMAND);
        commendsForGUI.add(ConstantsLanguage.EXECUTE_SCRIPT_COMMAND);
        commendsForGUI.add(ConstantsLanguage.CLEAR_COMMAND);
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
            log.info("Error in creating connection.");
        } catch (IOException e) {
            log.info("Uppss... Something with input/output went wrong.");
        }
        return false;
    }

    private boolean tryConnect() throws IOException {
        sendManager.sendMessage(new Message(ConstantsLanguage.CONNECT_TO_SERVER_COMMAND, null));
        CommandResult result = receiveManager.receiveMessage();
        return Objects.nonNull(result);
    }

    public boolean login(String username, String password) throws ConnectionLostExeption {
        try {
            Command command = commandManager.getCommand(ConstantsLanguage.LOGIN_COMMAND);
            BodyCommand bodyCommand = command.requestBodyCommand(new String[]{username, password}, null);
            message = new Message();
            message.setBodyCommand(bodyCommand);
            message.setCommand(ConstantsLanguage.LOGIN_COMMAND);
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
            Command command = commandManager.getCommand(ConstantsLanguage.SIGN_UP_COMMAND);
            BodyCommand bodyCommand = command.requestBodyCommand(new String[]{username, password}, null);
            message = new Message();
            message.setBodyCommand(bodyCommand);
            message.setCommand(ConstantsLanguage.SIGN_UP_COMMAND);
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
        BodyCommandWithSpMar bodyCommand = new BodyCommandWithSpMar(data, spaceMarine);
        message.setCommand(nameCommand);
        message.setBodyCommand(bodyCommand);
        try {
            sendManager.sendMessage(message);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                return result;
            }
            return new CommandResult(ConstantsLanguage.ERROR_COMMAND, null, false, ConstantsLanguage.ERROR_TO_CONNECT_TO_SERVER);
        } catch (IOException e) {
            return new CommandResult(ConstantsLanguage.ERROR_COMMAND, null, false, ConstantsLanguage.IOEXCEPTION);
        }
    }

    public CommandResult executeCommand(Message sendMessage) {
        try {
            sendManager.sendMessage(sendMessage);
            CommandResult result = receiveManager.receiveMessage();
            if (Objects.nonNull(result)) {
                return result;
            }
            return new CommandResult(ConstantsLanguage.ERROR_COMMAND, null, false, ConstantsLanguage.ERROR_TO_CONNECT_TO_SERVER);
        } catch (IOException e) {
            return new CommandResult(ConstantsLanguage.ERROR_COMMAND, null, false, ConstantsLanguage.IOEXCEPTION);
        }
    }

    @SuppressWarnings({"unchecked"})
    public List<SpaceMarine> getListFromServer() throws ConnectionLostExeption {
        CommandResult result = executeCommand(ConstantsLanguage.SHOW_COMMAND, null, null);
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
