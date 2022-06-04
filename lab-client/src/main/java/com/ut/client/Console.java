package com.ut.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import com.ut.common.commands.Command;
import com.ut.common.commands.CommandManager;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.IOManager;
import com.ut.common.util.Message;

import lombok.Getter;

@Getter
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
                    ioManager.println(receiveManager.receiveMessage().getMessageResult());
                    continue;
                }
                ioManager.turnOffFileMode();
                if (!ioManager.getFileMode()) {
                    isWorkState = false;
                }
            }
            ioManager.println("Incorrect command in file");
        }
    }

    public Command parsCommand(String name) {
        Command command = commandManager.getCommand(name);
        if (Objects.isNull(command)) {
            if (ioManager.getFileMode()) {
                ioManager.printerr("Unknow command in file.");
                ioManager.turnOffFileMode();
                return null;
            } else {
                ioManager.printerr("Unknown commands. Print help for getting info about commands");
                return null;
            }
        }
        if ("exit".equals(name)) {
            isWorkState = false;
        }
        return command;
    }

    public boolean endFile(String line) {
        boolean endFile = false;
        if (Objects.isNull(line)) {
            ioManager.turnOffFileMode();
            endFile = !ioManager.getFileMode();
        } else if ("".equals(line.trim())) {
            ioManager.turnOffFileMode();
            endFile = !ioManager.getFileMode();
        }
        if (endFile) {
            isWorkState = false;
        }
        return endFile;
    }
}
