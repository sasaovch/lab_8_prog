package com.ut.common.commands;

import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.CollectionManager;
import com.ut.common.util.IOManager;

public class CountByLoyalCommand extends Command {
    private CollectionManager collectionManager;

    public CountByLoyalCommand(CollectionManager collection) {
        super("count_by_loyal", "count_by_loyal loyal : print the number of elements whose value of the loyal field is equal to the specified");
        collectionManager = collection;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        Integer count = collectionManager.countBySomeThing(SpaceMarine::getLoyal, (Boolean) bodyCommand.getData());
        return new CommandResult("count_by_loyal", count, true, "Count by loyal - " + (Boolean) bodyCommand.getData() + ": " + count);
    }

    @Override
    public BodyCommand requestBodyCommand(String[] args, IOManager ioManager) {
        if (args.length == 0) {
            return new BodyCommand(null);
        } else if (args.length == 1) {
            if (args[0].equals("true") || args[0].equals("false")) {
                return new BodyCommand(Boolean.parseBoolean(args[0]));
            }
        }
        return null;
    }
}
