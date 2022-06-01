package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.ut.client.ConnectionAndExecutorManager;

import exeptions.ConnectionLostExeption;
import util.Constants;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class LoginJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel listOfLanguagesJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Constants.VGAP, Constants.HGAP));
    private final JPanel loginLabelJPanel = new JPanel();
    private final JPanel loginTextFieldJPanel = new JPanel();
    private final JPanel passwordLabelJPanel = new JPanel();
    private final JPanel passwordTextFieldJPanel = new JPanel();
    private final JPanel submitButtonJPanel =  new JPanel();
    private final JPanel errorFieldJPanel = new JPanel();

    private final int numberOfPanels = 7;
    private final int panelHeight = Constants.SCREEN_HEIGHT / numberOfPanels;
    private final int panelWidth = Constants.SCREEN_WIDTH;

    private JComboBox<String> listToChooseLanguage;
    private JLabel loginJLabe;
    private JTextField loginJTextField;
    private JLabel passwordJLabel;
    private JPasswordField passwordJPasswordField;
    private JButton loginJButton;
    private JButton signupJButton;
    private JLabel errorJLabel;

    private final GUIManager guiManager;
    private final ConnectionAndExecutorManager caeManager;
    private ResourceBundle resourceBundle;

    public LoginJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initElement();
    }

    private void initElement() {
        listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        setSettingForLanguagesList();
        listOfLanguagesJPanel.add(listToChooseLanguage);
        listOfLanguagesJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        loginLabelJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        loginTextFieldJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        passwordLabelJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        passwordTextFieldJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        submitButtonJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        errorFieldJPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        add(listOfLanguagesJPanel);
        add(loginLabelJPanel);
        add(loginTextFieldJPanel);
        add(passwordLabelJPanel);
        add(passwordTextFieldJPanel);
        add(submitButtonJPanel);
        add(errorFieldJPanel);

        loginJLabe = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Eneter your username"));
        loginLabelJPanel.add(loginJLabe, BorderLayout.CENTER);
        loginJTextField = BasicGUIElementsFabric.createBasicJTextField();
        loginTextFieldJPanel.add(loginJTextField);
        passwordJLabel = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Eneter your password"));
        passwordLabelJPanel.add(passwordJLabel, BorderLayout.CENTER);
        passwordJPasswordField = BasicGUIElementsFabric.createBasicJPasswordFiled();
        passwordTextFieldJPanel.add(passwordJPasswordField);
        loginJButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("Log In"));
        setListenerForLoginButton();
        submitButtonJPanel.add(loginJButton);
        signupJButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("Sign Up"));
        setListenerForSignUpButton();
        submitButtonJPanel.add(signupJButton);
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showLoginPanel(resourceBundle);
            }
        });
    }

    private void printError(String error) {
        errorJLabel = BasicGUIElementsFabric.createBasicLabel(error);
        errorFieldJPanel.removeAll();
        errorFieldJPanel.add(errorJLabel);
        guiManager.reloadMainScreen();
    }

    private void setListenerForLoginButton() {
        loginJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String username = loginJTextField.getText();
                String password = passwordJPasswordField.getText();
                try {
                    boolean answer = caeManager.login(username, password);
                    if (!answer) {
                        printError(resourceBundle.getString("Login or password is incorrect"));
                    } else {
                        guiManager.showTablePanel(resourceBundle);
                    }
                } catch (ConnectionLostExeption e) {
                    printError(resourceBundle.getString("Error to connect to server"));
                }
            }
        });
    }

    private void setListenerForSignUpButton() {
        signupJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String username = loginJTextField.getText();
                String password = passwordJPasswordField.getText();
                try {
                    boolean answer = caeManager.signup(username, password);
                    if (!answer) {
                        printError(resourceBundle.getString("This username is already used"));
                    } else {
                        guiManager.showTablePanel(resourceBundle);
                    }
                } catch (ConnectionLostExeption e) {
                    printError(resourceBundle.getString("Error to connect to server"));
                }
            }
        });
    }
}
