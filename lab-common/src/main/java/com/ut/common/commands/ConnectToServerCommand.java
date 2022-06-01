package com.ut.common.commands;

import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.IOManager;

public class ConnectToServerCommand extends Command {

    public ConnectToServerCommand() {
        super("connect_to_server", "connect to server", false);
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        return new CommandResult("connectToServer", null, true, null);
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) {
        throw new UnsupportedOperationException();
    }
}
