package com.ut.commandsitems;

import javax.swing.JPanel;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.gui.CommandModeJPanel;

public class StandartItem implements ActionWhenSelected {

    @Override
    public void showNeededPanels(JPanel jPanel) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        commandJPanel.setAargumentTextFieldEditable(false);
        commandJPanel.addScrollText();
        commandJPanel.addArgumentTextField();
        commandJPanel.updateNorthPanel();
    }

    @Override
    public void executeCommand(JPanel jPanel, ConnectionAndExecutorManager caeManager) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        commandJPanel.showResultOfExecuteCommand(caeManager.executeCommand(commandJPanel.findRightCommand(), null, null));
    }
}
