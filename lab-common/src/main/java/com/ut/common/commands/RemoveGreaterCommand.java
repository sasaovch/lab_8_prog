package com.ut.common.commands;

import java.util.Objects;

import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.AskerInformation;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.BodyCommandWithSpMar;
import com.ut.common.util.CollectionManager;
import com.ut.common.util.IOManager;

public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collection) {
        super("remove_greater", "remove_greater {element} : remove all items from collection that exceed the specified");
        collectionManager = collection;
    }


    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        BodyCommandWithSpMar bodyCommWitSpMar = (BodyCommandWithSpMar) bodyCommand;
        switch (collectionManager.removeIf(spaceMar -> {
            return (spaceMar.compareTo(bodyCommWitSpMar.getSpaceMarine()) > 0) && (spaceMar.getOwnerName().equals(user.getUsername()));
            })) {
            case True : return new CommandResult("remove_greater", null, true, "All items have been successfully deleted.");
            case False :  return new CommandResult("remove_greater", null, true, "No element has been deleted.");
            default :  return new CommandResult("remove_greater", null, false, "Database broke down.");
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
