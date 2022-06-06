package com.ut.commandsitems;

import javax.swing.JPanel;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;
import com.ut.gui.AddJPanel;
import com.ut.gui.CommandModeJPanel;
import com.ut.util.ConstantsLanguage;

public class UpdateItem implements ActionWhenSelected {

    @Override
    public void showNeededPanels(JPanel jPanel) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        commandJPanel.setAargumentTextFieldEditable(true);
        commandJPanel.addArgumentTextField();
        commandJPanel.addAddJPanel();
        commandJPanel.updateNorthPanel();
    }

    @Override
    public void executeCommand(JPanel jPanel, ConnectionAndExecutorManager caeManager) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        AddJPanel addJPanel = commandJPanel.getAddJPanel();
        SpaceMarine spMar = addJPanel.getSpaceMarine();
        try {
            Long id = Long.parseLong(commandJPanel.getTextFromArgumentTextField());
            if (spMar == null) {
                commandJPanel.printError(ConstantsLanguage.INVALID_ARGUMENTS);
                return;
            }
            CommandResult result = caeManager.executeCommand(ConstantsLanguage.UPDATE_COMMAND, spMar, id);
            commandJPanel.printMessage(result.getMessageResult());
        } catch (NumberFormatException e) {
            commandJPanel.printError(ConstantsLanguage.INVALID_ARGUMENTS);
        }
    }
}
