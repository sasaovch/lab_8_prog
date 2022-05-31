package com.ut.common.commands;

import java.io.IOException;
import java.util.Objects;

import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.IOManager;
import com.ut.common.util.UserManagerInt;

public class SignUpCommand extends Command {
    private UserManagerInt userCollection;

    public SignUpCommand(UserManagerInt userColl) {
        super("sign_up", "sign_up {user} : register new user", false);
        userCollection = userColl;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        User newClient = (User) bodyCommand.getData();
        if (!userCollection.checkIn(newClient)) {
            User authentClient = userCollection.register(newClient);
            if (Objects.nonNull(authentClient)) {
                return new CommandResult("sign_up", authentClient, true, "Registration completed successfully.");
            }
            return new CommandResult("sign_up", null, false, "Something with database went wrong.");
        }
        return new CommandResult("sign_up", null, false, "This username is used.");
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) throws IOException {
        String username = args[0];
        String password = args[1];
        return new BodyCommand(new User(username.trim(), password));
    }
}
