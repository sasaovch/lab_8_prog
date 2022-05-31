package com.ut.common.commands;

import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import com.ut.common.data.SpaceMarine;
import com.ut.common.data.User;
import com.ut.common.util.BodyCommand;
import com.ut.common.util.CollectionManager;

public class InfoCommand extends Command {
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collection) {
        super("info", "info : print info about collection: type, initialization date, number of elements");
        collectionManager = collection;
    }

    @Override
    public CommandResult run(BodyCommand bodyCommand, User user) {
        TreeMap<String, Object> outMap = new TreeMap<>();
        outMap.put("Initialization time", collectionManager.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        outMap.put("Number of Marines", collectionManager.getSize());
        outMap.put("Type", SpaceMarine.class);
        StringBuilder messageResult = new StringBuilder();
        outMap.entrySet().stream().forEach(s -> messageResult.append(s.getKey() + ": " + s.getValue() + "\n"));
        return new CommandResult("info", outMap, true, messageResult.toString());
    }
}
