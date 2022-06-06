package com.ut.commandsitems;

import javax.swing.JPanel;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.gui.CommandModeJPanel;
import com.ut.util.ConstantsLanguage;

public class CountByLoyalItem implements ActionWhenSelected {

    @Override
    public void showNeededPanels(JPanel jPanel) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        commandJPanel.addScrollText();
        commandJPanel.addLoyalBoxJComboBox();
        commandJPanel.updateNorthPanel();
    }

    @Override
    public void executeCommand(JPanel jPanel, ConnectionAndExecutorManager caeManager) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        Boolean loyalPars = commandJPanel.getLoyalFromJComboBox();
        commandJPanel.showResultOfExecuteCommand(caeManager.executeCommand(ConstantsLanguage.COUNT_BY_LOAYL_COMMAND, null, loyalPars));
    }
}
