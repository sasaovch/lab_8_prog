package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ut.client.ConnectionAndExecutorManager;

import util.Constants;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class ConnectJPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JPanel listOfLanguagesJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Constants.VGAP, Constants.HGAP));
    private final JPanel addressLabelJPanel = new JPanel();
    private final JPanel addressTextFieldJPanel = new JPanel();
    private final JPanel portLabelJPanel = new JPanel();
    private final JPanel portTextFielJdPanel = new JPanel();
    private final JPanel submitButtonJPanel =  new JPanel();
    private final JPanel errorFieldJPanel = new JPanel();

    private final int numberOfPanels = 7;
    private final int panelWidth = Constants.SCREEN_WIDTH;
    private final int panelHeight = Constants.SCREEN_HEIGHT / numberOfPanels;

    private JComboBox<String> listToChooseLanguage;
    private JLabel addressJLabel;
    private JTextField addressJTextField;
    private JLabel portLabel;
    private JTextField portJTextField;
    private JButton submitJButton;
    private JLabel errorJLabel;

    private final GUIManager guiManager;
    private final ConnectionAndExecutorManager caeManager;
    private ResourceBundle resourceBundle;

    public ConnectJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.SCREEN_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initElement();
    }

    private void initElement() {
        listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        setSettingForLanguagesList();
        listOfLanguagesJPanel.add(listToChooseLanguage);

        listOfLanguagesJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        addressLabelJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        addressTextFieldJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        portLabelJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        portTextFielJdPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        submitButtonJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        errorFieldJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        add(listOfLanguagesJPanel);
        add(addressLabelJPanel);
        add(addressTextFieldJPanel);
        add(portLabelJPanel);
        add(portTextFielJdPanel);
        add(submitButtonJPanel);
        add(errorFieldJPanel);

        addressJLabel = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Eneter host address"));
        addressLabelJPanel.add(addressJLabel, BorderLayout.CENTER);

        addressJTextField = BasicGUIElementsFabric.createBasicJTextField();
        addressTextFieldJPanel.add(addressJTextField, BorderLayout.CENTER);

        portLabel = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Eneter host port"));
        portLabelJPanel.add(portLabel, BorderLayout.CENTER);

        portJTextField = BasicGUIElementsFabric.createBasicJTextField();
        portTextFielJdPanel.add(portJTextField, BorderLayout.CENTER);

        submitJButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("connect"));
        submitJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH * 2, Constants.BUTTON_HEIGHT));
        setConnectionListenerForFirstSubmitButton();
        submitButtonJPanel.add(submitJButton, BorderLayout.CENTER);
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showConnectPanel(resourceBundle);

            }
        });
    }

    private void printError(String error) {
        errorJLabel = BasicGUIElementsFabric.createBasicLabel(error);
        errorFieldJPanel.removeAll();
        errorFieldJPanel.add(errorJLabel);
        guiManager.reloadMainScreen();
    }

    private void setConnectionListenerForFirstSubmitButton() {
        submitJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFieldJPanel.removeAll();
                guiManager.reloadMainScreen();
                String address = addressJTextField.getText();
                String port = portJTextField.getText();
                boolean answer = caeManager.connectToServer(address, port);
                if (!answer) {
                    printError(resourceBundle.getString("Error to connect to server"));
                } else {
                    guiManager.showLoginPanel(resourceBundle);
                }
            }
        });
    }
}
