package com.ut.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import com.ut.common.commands.Command;
import com.ut.common.commands.CommandManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.IOManager;
import com.ut.common.util.Message;
import com.ut.util.ConstantsLanguage;

public class Console {
    private final IOManager ioManager;
    private final ReceiveManager receiveManager;
    private final SendManager sendManager;
    private final CommandManager commandManager = CommandManager.getDefaultCommandManager(null, null);
    private Message message;
    private boolean isWorkState = true;

    public Console(IOManager ioManager, ReceiveManager receiveManager, SendManager sendManager, Message message) {
        this.ioManager = ioManager;
        this.receiveManager = receiveManager;
        this.sendManager = sendManager;
        this.message = message;
    }

    public boolean isWorkState() {
        return isWorkState;
    }

    public void executeScript() throws IOException {
        String line = "";
        String nameCommand;
        String[] value;
        String[] commandline;
        while (isWorkState) {
            line = ioManager.readLine();
            if (endFile(line)) {
                break;
            }
            commandline = (line + " " + " ").split(" ");
            nameCommand = commandline[0];
            value = Arrays.copyOfRange(commandline, 1, commandline.length);
            Command command = parsCommand(nameCommand);
            if (Objects.nonNull(command)) {
                BodyCommand bodyCommand = command.requestBodyCommand(value, ioManager);
                if (Objects.nonNull(bodyCommand)) {
                    message.setCommand(nameCommand);
                    message.setBodyCommand(bodyCommand);
                    sendManager.sendMessage(message);
                    CommandResult result = receiveManager.receiveMessage();
                    if (ConstantsLanguage.ERROR_COMMAND.equals(result.getCommandName())) {
                        ioManager.println(receiveManager.receiveMessage().getMessageResult());
                        ioManager.turnOffFileMode();
                    } else {
                        ioManager.println(result.getMessageResult());
                    }
                    continue;
                }
                ioManager.println(ConstantsLanguage.ERROR_IN_FILE_MESSAGE);
                ioManager.turnOffFileMode();
                if (!ioManager.getFileMode()) {
                    isWorkState = false;
                }
            }
        }
    }

    public Command parsCommand(String name) {
        Command command = commandManager.getCommand(name);
        if (Objects.isNull(command)) {
            if (ioManager.getFileMode()) {
                ioManager.printerr(ConstantsLanguage.UNKNOW_COMMAND_EX_MESSAGE);
                ioManager.turnOffFileMode();
                return null;
            }
        }
        return command;
    }

    public boolean endFile(String line) {
        boolean endFile = false;
        if (Objects.isNull(line) || ("".equals(line.trim()))) {
            ioManager.turnOffFileMode();
            endFile = !ioManager.getFileMode();
        }
        if (endFile) {
            isWorkState = false;
        }
        return endFile;
    }
}
