package com.ut.common.commands;

import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.CollectionManager;

public class ClearCommand extends Command {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collection) {
        super("clear", "clear : clear the collection");
        collectionManager = collection;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        switch (collectionManager.clearCollection(user.getUsername())) {
            case True : return new CommandResult("clear", null, true, "The collection is cleared.");
            default :  return new CommandResult("clear", null, false, "Database broke down.");
        }
    }
}
