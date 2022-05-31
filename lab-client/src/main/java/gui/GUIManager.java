package gui;

import java.util.ResourceBundle;

public class GUIManager {
    private MainJFrame mainFrame;
    public GUIManager(MainJFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    public void reloadMainScreen() {
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showTablePanel(ResourceBundle resourceBundle) {
        mainFrame.showTablePanel(resourceBundle);
    }

    public void showVisualPanel(ResourceBundle resourceBundle) {
        mainFrame.showVisualPanel(resourceBundle);
    }

    public void showLoginPanel(ResourceBundle resourceBundle) {
        mainFrame.showLoginPanel(resourceBundle);
    }

    public void showCommandPanel(ResourceBundle resourceBundle) {
        mainFrame.showCommandPanel(resourceBundle);
    }

    public void showConnectPanel(ResourceBundle resourceBundle) {
        mainFrame.showConnectPanel(resourceBundle);
    }
}
