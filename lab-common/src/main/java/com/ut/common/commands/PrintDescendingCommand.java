package com.ut.common.commands;

import java.util.ArrayList;
import java.util.Collections;

import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.CollectionManager;

public class PrintDescendingCommand extends Command {
    private CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collection) {
        super("print_descending", "print_descending : print all the elements of the collection in descending order");
        collectionManager = collection;
    }


    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        if (collectionManager.getSize() == 0) {
            return new CommandResult("print_descending", null, true, "The collection is empty.");
        }
        ArrayList<SpaceMarine> list = collectionManager.sortCollection();
        Collections.reverse(list);
        StringBuilder messageResult = new StringBuilder();
        list.stream().forEach(spMar -> messageResult.append(spMar.toString() + "\n"));
        return new CommandResult("print_descending", list, true, messageResult.toString());
    }
}
