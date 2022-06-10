package com.ut.common.commands;


import java.util.Objects;

import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.AskerInformation;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.BodyCommandWithSpMar;
import com.ut.common.util.CollectionManager;
import com.ut.common.util.IOManager;


public class AddIfMinCommand extends Command {
    private CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collection) {
        super("add_if_min", "add_if_min {element} : add element if its value is less than minimal value in collection (value is health)");
        collectionManager = collection;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        BodyCommandWithSpMar bodyCommandWithSpMar = (BodyCommandWithSpMar) bodyCommand;
        bodyCommandWithSpMar.getSpaceMarine().setOwnerName(user.getUsername());
        switch (collectionManager.addIfMin(bodyCommandWithSpMar.getSpaceMarine())) {
            case True : return new CommandResult("add_if_min", null, true, "SpaceMarine has been added.");
            case False :  return new CommandResult("add_if_min", null, false, "Element is bigger than minimum.");
            default :  return new CommandResult("add_if_min", null, false, "Database broke down.");
        }
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) {
        if (args.length != 0) {
            return null;
        }
        SpaceMarine newSpMar = AskerInformation.askMarine(ioManager);
        if (Objects.nonNull(newSpMar)) {
            return new BodyCommandWithSpMar(null, newSpMar);
        }
        return null;
    }
}
