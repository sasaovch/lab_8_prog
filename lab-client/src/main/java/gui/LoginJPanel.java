package gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ut.client.ConnectionAndExecutorManager;

import lombok.extern.slf4j.Slf4j;
import util.Constants;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

@Slf4j
public class LoginJPanel extends JPanel {
    private final int relativeWidthDisplacementForLanguagesList = Constants.SCREEN_WIDTH / 100;
    private final int relativeHeightDisplacementForLanguagesList = Constants.SCREEN_HEIGHT / 25; //Константы, управляющие размерами объектов и зависящие от размера экрана
    private final int relativeWidthDisplacementForTextFields = Constants.SCREEN_WIDTH * 8 / 10;
    private final int relativeHeightDisplacementForTextFields = Constants.SCREEN_HEIGHT / 20;

    private final JPanel languageListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, relativeWidthDisplacementForLanguagesList, relativeHeightDisplacementForLanguagesList));
    private final JPanel loginTextPanel = new JPanel(new BorderLayout());
    private final JPanel loginTextFielPanel = new JPanel(new GridBagLayout());
    private final JPanel passwordPasswordField = new JPanel(new GridBagLayout());
    private final JPanel passwordTextPanel = new JPanel(new BorderLayout());
    private final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, relativeWidthDisplacementForLanguagesList, relativeHeightDisplacementForLanguagesList));
    private final JPanel errorFieldPanel = new JPanel(new GridBagLayout());

    private JButton loginButton;
    private JButton signupButton;

    private JLabel usernameText;
    private JLabel passwordText;

    private JTextField fieldForUsername;
    private JPasswordField fieldForPassword;
    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);

    private GUIManager guiManager;
    private ConnectionAndExecutorManager caeManager;
    private ResourceBundle resourceBundle;

    private static final long serialVersionUID = 1L;

    public LoginJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        this.resourceBundle = resourceBundle;
        initElement();
    }
    
    private void initElement() {
        int amountOfPanels = 8;

        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setBackground(Constants.SUB_COLOR);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));

        setSettingForLanguagesList();

        languageListPanel.add(listToChooseLanguage);
        languageListPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        loginTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        loginTextFielPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        passwordTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        passwordPasswordField.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        buttonsPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        errorFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        languageListPanel.setBackground(Color.PINK);
        loginTextFielPanel.setBackground(Color.GREEN);
        loginTextPanel.setBackground(Color.BLUE);
        passwordPasswordField.setBackground(Color.BLACK);
        passwordTextPanel.setBackground(Color.CYAN);
        buttonsPanel.setBackground(Color.MAGENTA);
        errorFieldPanel.setBackground(Color.ORANGE);

        add(languageListPanel);
        add(loginTextPanel);
        add(loginTextFielPanel);
        add(passwordTextPanel);
        add(passwordPasswordField);
        add(buttonsPanel);
        add(errorFieldPanel);

        usernameText = new JLabel(resourceBundle.getString("LOGIN"), SwingConstants.CENTER);
        usernameText.setFont(Constants.MAIN_FONT);
        loginTextPanel.add(usernameText, BorderLayout.CENTER);

        fieldForUsername = createCenteredJField();
        loginTextFielPanel.add(fieldForUsername);

        passwordText = new JLabel(resourceBundle.getString("PASSWORD"), SwingConstants.CENTER);
        passwordText.setFont(Constants.MAIN_FONT);
        passwordTextPanel.add(passwordText, BorderLayout.CENTER);
        
        fieldForPassword = createPasswordField();
        passwordPasswordField.add(fieldForPassword);

        loginButton = BasicGUIElementsFabric.createBasicButton("login"/*currentBundle.getString(textOfTheFirstButton)*/);
        loginButton.setFont(Constants.MAIN_FONT);
        setListenerForLoginButton();
        buttonsPanel.add(loginButton);
        
        signupButton = BasicGUIElementsFabric.createBasicButton("signup"/*currentBundle.getString(textOfTheFirstButton)*/);
        signupButton.setFont(Constants.MAIN_FONT);
        setListenerForSignUpButton();
        buttonsPanel.add(signupButton);
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Change language");
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showLoginPanel(resourceBundle);
            }
        });
    }

    public JTextField createCenteredJField() {
        JTextField jTextField = new JTextField(SwingConstants.CENTER);
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setPreferredSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setMinimumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setMaximumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 3));
        return jTextField;
    }
    public JPasswordField createPasswordField() {
        JPasswordField jPasswordField = new JPasswordField(SwingConstants.CENTER);
        jPasswordField.setFont(Constants.MAIN_FONT);
        jPasswordField.setPreferredSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jPasswordField.setMinimumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jPasswordField.setMaximumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jPasswordField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 3));
        return jPasswordField;
    }

    private void printError(String error) {
        JLabel jLabel = new JLabel();
        jLabel.setText(error);
        jLabel.setFont(Constants.MAIN_FONT);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        errorFieldPanel.removeAll();
        errorFieldPanel.add(jLabel);

        guiManager.reloadMainScreen();
    }

    private void setListenerForLoginButton() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldForUsername.getText();
                String password = fieldForPassword.getSelectedText();
                boolean answer = caeManager.login(username, password);

                if (!answer) {
                    printError("Error");
                } else {
                    guiManager.showTablePanel(resourceBundle);
                }

            }
        });
    }   
    
    private void setListenerForSignUpButton() {
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldForUsername.getText();
                String password = fieldForPassword.getSelectedText();
                boolean answer = caeManager.signup(username, password);
                
                if (!answer) {
                    printError("Error");
                } else {
                    guiManager.showTablePanel(resourceBundle);
                }
            }
        });
    }
}
