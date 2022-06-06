package com.ut.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.data.SpaceMarine;
import com.ut.util.Constants;

import java.awt.Dimension;
import java.util.ResourceBundle;
import java.awt.Frame;

public final class MainJFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel commandJPanel;
    private JPanel connectJPanel;
    private TableViewJPanel tableJPanel;
    private JPanel visualJPanel;
    private JPanel loginJPanel;

    private ConnectionAndExecutorManager caeManager;
    private GUIManager guiManager;

    private MainJFrame() {
        caeManager = new ConnectionAndExecutorManager();
        guiManager = new GUIManager(this);
        connectJPanel = new ConnectJPanel(guiManager, caeManager, Constants.getBundleFromLanguageName(Constants.DEFAUTL_LANGUAGE));
        add(connectJPanel);
    }

    public void showLoginPanel(ResourceBundle resourceBundle) {
        loginJPanel = new LoginJPanel(guiManager, caeManager, resourceBundle);
        getContentPane().removeAll();
        setContentPane(loginJPanel);
        revalidate();
        repaint();
    }

    public void showTablePanel(ResourceBundle resourceBundle) {
        tableJPanel = new TableViewJPanel(guiManager, resourceBundle, caeManager);
        getContentPane().removeAll();
        setContentPane(tableJPanel);
        revalidate();
        repaint();
    }

    public void showVisualPanel(ResourceBundle resourceBundle) {
        visualJPanel = new VisualJPanel(guiManager, caeManager, resourceBundle);
        getContentPane().removeAll();
        setContentPane(visualJPanel);
        revalidate();
        repaint();
    }

    public void showCommandPanel(ResourceBundle resourceBundle) {
        commandJPanel = new CommandModeJPanel(guiManager, caeManager, resourceBundle);
        getContentPane().removeAll();
        setContentPane(commandJPanel);
        revalidate();
        repaint();
    }

    public void showConnectPanel(ResourceBundle resourceBundle) {
        connectJPanel = new ConnectJPanel(guiManager, caeManager, resourceBundle);
        getContentPane().removeAll();
        setContentPane(connectJPanel);
        revalidate();
        repaint();
    }

    public void addRowToTable(SpaceMarine spaceMarine) {
        tableJPanel.addRowToTable(spaceMarine);
    }

    public static void main(String[] args) throws Exception {
        MainJFrame mainFrame = new MainJFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainFrame.setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        mainFrame.setVisible(true);
    }
}
