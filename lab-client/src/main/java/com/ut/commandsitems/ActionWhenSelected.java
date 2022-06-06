package com.ut.commandsitems;

import javax.swing.JPanel;

import com.ut.client.ConnectionAndExecutorManager;

public interface ActionWhenSelected {
    void showNeededPanels(JPanel jPanel);
    void executeCommand(JPanel jPanel, ConnectionAndExecutorManager caeManager);
}
