package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
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
public class ConnectJPanel extends JPanel {
    private final int relativeWidthDisplacementForLanguagesList = Constants.SCREEN_WIDTH / 100;
    private final int relativeHeightDisplacementForLanguagesList = Constants.SCREEN_HEIGHT / 25; //Константы, управляющие размерами объектов и зависящие от размера экрана
    private final int relativeWidthDisplacementForTextFields = Constants.SCREEN_WIDTH * 8 / 10;
    private final int relativeHeightDisplacementForTextFields = Constants.SCREEN_HEIGHT / 20;

    private final JPanel languageListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, relativeWidthDisplacementForLanguagesList, relativeHeightDisplacementForLanguagesList));
    private final JPanel addressTextPanel = new JPanel(new BorderLayout());
    private final JPanel addressTextFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel portTextFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel portTextPanel = new JPanel(new BorderLayout());
    private final JPanel submitButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel errorFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel secondButtonPanel = new JPanel(new GridBagLayout()); //can be not in use, if it not in use than we don't count it 

    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
    private JButton submitButton;
    private JLabel addressText;
    private JTextField fieldForAddress;
    private JLabel portText;
    private JTextField fieldForPort;

    private final GUIManager guiManager;
    private final ConnectionAndExecutorManager caeManager;
    private ResourceBundle resourceBundle;

    private static final long serialVersionUID = 1L;

    public ConnectJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        initElement();
    }
    
    private void initElement() {
        int amountOfPanels = 7;

        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setBackground(Constants.SUB_COLOR);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        setSettingForLanguagesList();
        languageListPanel.add(listToChooseLanguage);
        languageListPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        addressTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        addressTextFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        portTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));

        portTextFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        submitButtonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        errorFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        secondButtonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));

        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        languageListPanel.setBackground(Color.PINK);
        addressTextFieldPanel.setBackground(Color.GREEN);
        addressTextPanel.setBackground(Color.BLUE);
        portTextFieldPanel.setBackground(Color.BLACK);
        portTextPanel.setBackground(Color.CYAN);
        submitButtonPanel.setBackground(Color.MAGENTA);
        errorFieldPanel.setBackground(Color.ORANGE);
        add(languageListPanel);
        add(addressTextPanel);
        add(addressTextFieldPanel);
        add(portTextPanel);
        add(portTextFieldPanel);
        add(submitButtonPanel);
        add(errorFieldPanel);

        
        addressText = new JLabel(resourceBundle.getString("REMOTE HOST ADDRESS:"), SwingConstants.CENTER);
        addressText.setFont(Constants.MAIN_FONT);
        addressTextPanel.add(addressText, BorderLayout.CENTER);

        fieldForAddress = createCenteredJField();
        addressTextFieldPanel.add(fieldForAddress);

        portText = new JLabel(resourceBundle.getString("REMOTE HOST PORT:"), SwingConstants.CENTER);
        portText.setFont(Constants.MAIN_FONT);
        portTextPanel.add(portText, BorderLayout.CENTER);
        
        fieldForPort = createCenteredJField();
        portTextFieldPanel.add(fieldForPort);

        submitButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("SUBMIT"));
        submitButton.setFont(Constants.MAIN_FONT);
        setConnectionListenerForFirstButton();
        submitButtonPanel.add(submitButton);
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Change language");
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showConnectPanel(resourceBundle);

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

    private void printError(String error) {
        JLabel jLabel = new JLabel();
        jLabel.setText(error);
        jLabel.setFont(Constants.MAIN_FONT);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

        errorFieldPanel.removeAll();
        errorFieldPanel.add(jLabel);

        guiManager.reloadMainScreen();
    }

    private void setConnectionListenerForFirstButton() {
        log.info("setConnection");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = fieldForAddress.getText();
                String port = fieldForPort.getText();
                log.info("Try to connect");
                boolean answer = caeManager.connectToServer("localhost", "8713");
                if (!answer) {
                    printError("Error");
                } else {
                    guiManager.showLoginPanel(resourceBundle);
                }
            }
        });
    }
}