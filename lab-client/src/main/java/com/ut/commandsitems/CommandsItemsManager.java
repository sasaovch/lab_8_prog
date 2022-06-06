package com.ut.commandsitems;

import java.util.HashMap;
import java.util.Map;

import com.ut.util.ConstantsLanguage;

public final class CommandsItemsManager {
    private static CommandsItemsManager commandsItemsManager;
    private Map<String, ActionWhenSelected> commandsMap;

    static {
        commandsItemsManager = new CommandsItemsManager();
    }

    private CommandsItemsManager() {
        commandsMap = new HashMap<>();
        commandsMap.put(ConstantsLanguage.ADD_COMMAND, new AddItem());
        commandsMap.put(ConstantsLanguage.ADD_IF_MIN_COMMAND, new AddIfMinItem());
        commandsMap.put(ConstantsLanguage.REMOVE_BY_ID_COMMAND, new RemoveByIdItem());
        commandsMap.put(ConstantsLanguage.REMOVE_GREATER_COMMAND, new RemoveGreaterItem());
        commandsMap.put(ConstantsLanguage.REMOVE_LOWER_COMMAND, new RemoveLowerItem());
        commandsMap.put(ConstantsLanguage.UPDATE_COMMAND, new UpdateItem());
        commandsMap.put(ConstantsLanguage.EXECUTE_SCRIPT_COMMAND, new ExecuteScriptItem());
        commandsMap.put(ConstantsLanguage.COUNT_BY_LOAYL_COMMAND, new CountByLoyalItem());
        commandsMap.put(ConstantsLanguage.HELP_COMMAND, new StandartItem());
        commandsMap.put(ConstantsLanguage.INFO_COMMAND, new StandartItem());
        commandsMap.put(ConstantsLanguage.SHOW_COMMAND, new StandartItem());
        commandsMap.put(ConstantsLanguage.PRINT_DESCENDING_COMMAND, new StandartItem());
        commandsMap.put(ConstantsLanguage.GROUP_COUNT_BY_NAME_COMMAND, new StandartItem());
        commandsMap.put(ConstantsLanguage.CLEAR_COMMAND, new StandartItem());
    }

    public static CommandsItemsManager getCommandsItemsManager() {
        return commandsItemsManager;
    }

    public ActionWhenSelected getActionForCommand(String command) {
        return commandsMap.entrySet().stream().filter(s -> s.getKey().equals(command)).findFirst().get().getValue();
    }
}
