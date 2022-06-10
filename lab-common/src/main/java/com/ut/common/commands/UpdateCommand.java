package com.ut.common.commands;

import java.util.Objects;

import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.AskerInformation;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.BodyCommandWithSpMar;
import com.ut.common.util.CollectionManager;
import com.ut.common.util.IOManager;

public class UpdateCommand extends Command {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collection) {
        super("update", "update id {element} : update element info by it's id");
        collectionManager = collection;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        BodyCommandWithSpMar bodyCommandWithSpMar = (BodyCommandWithSpMar) bodyCommand;
        SpaceMarine newSpaceMarine = bodyCommandWithSpMar.getSpaceMarine();
        newSpaceMarine.setOwnerName(user.getUsername());
        Long id = (Long) bodyCommand.getData();
        if (collectionManager.getSize() == 0) {
            return new CommandResult("update", null, false, "There are no such element in the collection.");
        }
        switch (collectionManager.updateSpaceMarine(newSpaceMarine, id)) {
            case True : return new CommandResult("update", newSpaceMarine, true, "Marine has been successfully updated.");
            case False : return new CommandResult("update", null, false, "Id is not correct or insufficient access rights or dublicated elements.");
            default :  return new CommandResult("update", null, false, "Database broke down.");
        }
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) {
        if (args.length != 1) {
            return null;
        }
        try {
            SpaceMarine newSpMar = AskerInformation.askMarine(ioManager);
            if (Objects.nonNull(newSpMar)) {
                return new BodyCommandWithSpMar(Long.parseLong(args[0]), newSpMar);
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
