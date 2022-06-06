package com.ut.commandsitems;

import javax.swing.JPanel;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.gui.CommandModeJPanel;
import com.ut.util.ConstantsLanguage;

public class RemoveByIdItem implements ActionWhenSelected {

    @Override
    public void showNeededPanels(JPanel jPanel) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        commandJPanel.setAargumentTextFieldEditable(true);
        commandJPanel.addScrollText();
        commandJPanel.addArgumentTextField();
        commandJPanel.updateNorthPanel();
    }

    @Override
    public void executeCommand(JPanel jPanel, ConnectionAndExecutorManager caeManager) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        try {
            Long id = Long.parseLong(commandJPanel.getTextFromArgumentTextField());
            CommandResult result = caeManager.executeCommand(ConstantsLanguage.REMOVE_BY_ID_COMMAND, null, id);
            commandJPanel.printMessage(result.getMessageResult());
        } catch (NumberFormatException e) {
            commandJPanel.printError(ConstantsLanguage.INVALID_ARGUMENTS);
        }
    }
}
