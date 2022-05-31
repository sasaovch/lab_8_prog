package com.ut.common.commands;



import com.ut.common.data.User;
import com.ut.common.util.AskerInformation;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.BodyCommandWithSpMar;
import com.ut.common.util.CollectionManager;
import com.ut.common.util.IOManager;


public class AddCommand extends Command {
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collection) {
        super("add", "add {element} : add new element in collection");
        collectionManager = collection;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        BodyCommandWithSpMar bodyCommandWithSpMar = (BodyCommandWithSpMar) bodyCommand;
        bodyCommandWithSpMar.getSpaceMarine().setOwnerName(user.getUsername());
        switch (collectionManager.addElement(bodyCommandWithSpMar.getSpaceMarine())) {
            case True : return new CommandResult("add", null, true,
                                                    bodyCommandWithSpMar.getSpaceMarine().getName() + " has been added.");
            case False :  return new CommandResult("add", null, false,
                                                    bodyCommandWithSpMar.getSpaceMarine().getName() + " already exists.");
            default :  return new CommandResult("add", null, false, "Database broke down.");
        }
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) {
        if (args.length != 0) {
            return null;
        }
        return new BodyCommandWithSpMar(null, AskerInformation.askMarine(ioManager));
    }
}
