package com.ut.common.commands;

import java.io.IOException;

import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.IOManager;
import com.ut.common.util.UserManagerInt;

public class LogInCommand extends Command {
    private UserManagerInt userCollection;

    public LogInCommand(UserManagerInt userColl) {
        super("login", "login {user} : authenticate user", false);
        userCollection = userColl;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        User newClient = (User) bodyCommand.getData();
        if (userCollection.checkIn(newClient)) {
            switch (userCollection.authenticate(newClient)) {
                case True : newClient.setAuntificationStatusTrue();
                            return new CommandResult("login", newClient, true, "Authentication completed successfully.");
                case False : return new CommandResult("login", null, false, "Authentication failed.");
                default :
                    return new CommandResult("login", null, false, "Database broke down.");
            }
        }
        return new CommandResult("login", null, false, "Unknown username. Enter another or sign up.");
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) throws IOException {
        String username = args[0];
        String password = args[1];
        return new BodyCommand(new User(username.trim(), password));
    }
}
