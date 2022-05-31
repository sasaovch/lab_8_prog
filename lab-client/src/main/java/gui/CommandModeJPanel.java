package gui;

import javax.swing.JPanel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;

import lombok.extern.slf4j.Slf4j;
import util.Constants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Set;

@Slf4j
public class CommandModeJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JFileChooser executeScriptFileChooser = new JFileChooser();
    private JComboBox<String> commandsBox;
    private JComboBox<String> typeOfView;
    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
    private JComboBox<String> loyalBox;

    private JButton userButton;
    private JButton submitOperation;
    
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel argumentPanel;
    private JPanel resultPanel;

    private AddJPanel addJPanel;

    private JLabel chooseCommLabel;
    private JLabel resultOfCommandWithSpMar;
    private JTextField argumentTextField;
    private JTextArea textResult;
    private JScrollPane scrollText;

// TODO Is it good to initialize field in this;
    
    private GUIManager guiManager;
    private ConnectionAndExecutorManager caeManager;
    private ResourceBundle resourceBundle;

    public CommandModeJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setLayout(new BorderLayout());
        initElements();
    }
    
    public void initElements() {
        chooseCommLabel = BasicGUIElementsFabric.createBasicLabel("Choose command");
        resultOfCommandWithSpMar = BasicGUIElementsFabric.createBasicLabel("");

        Set<String> commandsBoxList = caeManager.getCommandManager().getMap().keySet();
        String[] commandsBoxString = new String[commandsBoxList.size()];
        int i = 0;
        for (String command : commandsBoxList) {
            commandsBoxString[i] = command;
            i++;
        }

        commandsBox = BasicGUIElementsFabric.createBasicComboBox(commandsBoxString);
        typeOfView = BasicGUIElementsFabric.createBasicComboBox(new String[]{"Command Panel", "Table View", "Visual View"});
        loyalBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{"NULL", "TRUE", "FALSE"});

        userButton = BasicGUIElementsFabric.createBasicButton("username");
        submitOperation = BasicGUIElementsFabric.createBasicButton("push");
    
        centerPanel = new JPanel();
        northPanel = new JPanel();
        resultPanel = new JPanel();
        argumentPanel = new JPanel();

        argumentTextField = BasicGUIElementsFabric.createBasicJTextField();

        textResult = new JTextArea();
        textResult.setEditable(false);
        textResult.setLineWrap(true);
        textResult.setFont(Constants.SUB_FONT);
        textResult.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        scrollText = new JScrollPane(textResult);
        scrollText.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH  / 7 * 6, Constants.CENTER_PANEL_HEIGHT / 7 * 5));
        scrollText.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        argumentPanel.add(scrollText);

        addJPanel = new AddJPanel(caeManager, resourceBundle);
    
        setListenerForCommandsBox();
        setSettingsForElements();
        setListenerForUserButton();
        setListenerForSubmitButton();
        setListenerForFileChooser();
        setSettingForLanguagesList();
        setSettingsForSortPanel();
        addElementsToNorthPanel();
        addElementsToCenterPanel();
        setListenerForTypeOfViewBox();
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.SOUTH);
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Change language");
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showTablePanel(resourceBundle);
            }
        });
    }

    private void setListenerForFileChooser() {
        executeScriptFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caeManager.executeCommand("execute_script", null, executeScriptFileChooser.getSelectedFile());
            }
        });
    }

    public void setListenerForUserButton() {
        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                log.info("In userButton action");
                JFrame exitFrame = new JFrame();
                JPanel panel = new JPanel();
                JButton yesButton = new JButton("yes");
                JButton noButton = new JButton("no");
                JLabel label = new JLabel("Do you want exit?");
                yesButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        guiManager.showLoginPanel(resourceBundle);
                    }
                });
                noButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitFrame.dispose();
                    }
                });
                panel.add(yesButton);
                panel.add(noButton);
                panel.add(label);
                exitFrame.add(panel);
                exitFrame.setSize(new Dimension(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 4));
                exitFrame.setVisible(true);
            }
        });
    }

    private void setListenerForCommandsBox() {
        commandsBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                log.info("in Command box Action");
                if ("add".equals(commandsBox.getSelectedItem().toString()) || "add_if_min".equals(commandsBox.getSelectedItem().toString()) 
                    || "remove_greater".equals(commandsBox.getSelectedItem().toString()) || "remove_lower".equals(commandsBox.getSelectedItem().toString())
                    || "update".equals(commandsBox.getSelectedItem().toString())) {
                    argumentTextField.setEditable(false);
                    argumentPanel.removeAll();
                    argumentPanel.add(addJPanel);
                    northPanel.removeAll();
                    northPanel.add(chooseCommLabel);
                    northPanel.add(commandsBox);
                    northPanel.add(submitOperation);
                    northPanel.add(argumentTextField);
                    northPanel.add(typeOfView);
                    northPanel.add(listToChooseLanguage);
                    northPanel.add(userButton);
                    guiManager.reloadMainScreen();
                    if ("update".equals(commandsBox.getSelectedItem().toString())) {
                        argumentTextField.setEditable(true);
                        log.info("Set text fiel editable");
                    } 
                } else if ("execute_script".equals(commandsBox.getSelectedItem().toString())) {
                    log.info("Execute scti");
                    northPanel.removeAll();
                    northPanel.add(chooseCommLabel);
                    northPanel.add(commandsBox);
                    northPanel.add(submitOperation);
                    northPanel.add(executeScriptFileChooser);
                    northPanel.add(typeOfView);
                    northPanel.add(listToChooseLanguage);
                    northPanel.add(userButton);
                    argumentPanel.removeAll();
                    argumentPanel.add(scrollText);
                    guiManager.reloadMainScreen();
                } else if ("count_by_loyal".equals(commandsBox.getSelectedItem().toString())) {
                    northPanel.removeAll();
                    northPanel.add(chooseCommLabel);
                    northPanel.add(commandsBox);
                    northPanel.add(submitOperation);
                    northPanel.add(loyalBox);
                    northPanel.add(typeOfView);
                    northPanel.add(listToChooseLanguage);
                    northPanel.add(userButton);
                    argumentPanel.removeAll();
                    argumentPanel.add(scrollText);
                    guiManager.reloadMainScreen();
                } else {
                    log.info("Comon help, info");
                    if ("remove_by_id".equals(commandsBox.getSelectedItem().toString())) {
                        argumentTextField.setEditable(true);
                        log.info("Set text fiel editable");
                    }
                    argumentTextField.setEditable(false);
                    northPanel.removeAll();
                    northPanel.add(chooseCommLabel);
                    northPanel.add(commandsBox);
                    northPanel.add(submitOperation);
                    northPanel.add(argumentTextField);
                    northPanel.add(typeOfView);
                    northPanel.add(listToChooseLanguage);
                    northPanel.add(userButton);
                    argumentPanel.removeAll();
                    argumentPanel.add(scrollText);
                    guiManager.reloadMainScreen();
                }
            }
        });
    }

    private void setListenerForSubmitButton() {
        submitOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("add".equals(commandsBox.getSelectedItem().toString()) || "add_if_min".equals(commandsBox.getSelectedItem().toString()) 
                || "remove_greater".equals(commandsBox.getSelectedItem().toString()) || "remove_lower".equals(commandsBox.getSelectedItem().toString())
                || "update".equals(commandsBox.getSelectedItem().toString())) {
                    log.info("Try to show addJPanel");
                    SpaceMarine spMar = addJPanel.getSpaceMarine();
                    if (spMar == null) {
                        printError("Invalid arguments");
                        return;
                    }
                    CommandResult result = caeManager.executeCommand(commandsBox.getSelectedItem().toString(), spMar, null);
                    resultOfCommandWithSpMar.setText(result.getMessageResult());
                    guiManager.reloadMainScreen();
                } else if ("update".equals(commandsBox.getSelectedItem().toString()) || "remove_by_id".equals(commandsBox.getSelectedItem().toString())) {
                    try {
                        Long id = Long.parseLong(argumentTextField.getText());
                        SpaceMarine spMar = addJPanel.getSpaceMarine();
                        CommandResult result = caeManager.executeCommand(commandsBox.getSelectedItem().toString(), spMar, id);
                        resultOfCommandWithSpMar.setText(result.getMessageResult());
                        guiManager.reloadMainScreen();
                    } catch (NumberFormatException exception) {
                        printError("Invalid value for id");
                    }
                    log.info("Update SpaceMarine window");
                    return;
                } else if ("count_by_loyal".equals(commandsBox.getSelectedItem().toString())) {
                    String loyal = loyalBox.getSelectedItem().toString();
                    Boolean loyalPars;
                    switch (loyal) {
                        case("TRUE") : loyalPars = true;
                                break;
                        case("FALSE") : loyalPars = false;
                                break;
                        default : loyalPars = null;
                    }
                    CommandResult result = caeManager.executeCommand("count_by_loyal", null, loyalPars);
                    showResultOfExecuteCommand(result);
                } else {
                    showResultOfExecuteCommand(caeManager.executeCommand(commandsBox.getSelectedItem().toString(), null, null));
                }
            }
        });
    }

    private void printError(String message) {
        resultOfCommandWithSpMar.setText(message);
        resultPanel.add(resultOfCommandWithSpMar);
        guiManager.reloadMainScreen();
    }  

    public void setListenerForTypeOfViewBox() {
        typeOfView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Visual View".equals(typeOfView.getSelectedItem().toString())) {
                    log.info("Choosed Visual View");
                    guiManager.showVisualPanel(resourceBundle);
                } else if ("Table View".equals(typeOfView.getSelectedItem().toString())) {
                    log.info("Choosed Table view");
                    guiManager.showTablePanel(resourceBundle);
                }
            }
        });
    }


    public void addElementsToNorthPanel() {
        northPanel.add(chooseCommLabel);
        northPanel.add(commandsBox);
        northPanel.add(submitOperation);
        northPanel.add(argumentTextField);
        northPanel.add(typeOfView);
        northPanel.add(listToChooseLanguage);
        northPanel.add(userButton);
    }

    private void setSettingsForElements() {
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 8 * 7, Constants.NORTH_PANEL_HEIGHT));
        northPanel.setBackground(Color.PINK);

        centerPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 8 * 7, Constants.CENTER_PANEL_HEIGHT));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.BLUE);

        resultPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 8 * 7, Constants.NORTH_PANEL_HEIGHT / 2));
        resultPanel.setBackground(Color.MAGENTA);


        chooseCommLabel.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        commandsBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        loyalBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        argumentTextField.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitOperation.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        typeOfView.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        userButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    }

    private void addElementsToCenterPanel() {
        argumentPanel.setBackground(Color.RED);
        argumentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        centerPanel.add(argumentPanel, BorderLayout.CENTER);
        centerPanel.add(resultPanel, BorderLayout.SOUTH);
    }

    private void showResultOfExecuteCommand(CommandResult commandResult) {
        textResult.setText(commandResult.getMessageResult());
        resultOfCommandWithSpMar.setText("");
        guiManager.reloadMainScreen();
    }

    private void setSettingsForSortPanel() {
        submitOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
