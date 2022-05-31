package com.ut.common.commands;


import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.IOManager;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script", "execute_script file_name : execute script");
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) {
        if (args.length != 1) {
            return null;
        }
        ioManager.turnOnFileMode(args[0]);
        return null;
    }
}
