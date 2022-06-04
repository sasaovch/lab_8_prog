package gui;

import javax.swing.JPanel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

import util.Constants;
import util.ConstantsLanguage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.Component;
import java.util.stream.Collectors;

public class CommandModeJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JFileChooser executeScriptFileChooser = new JFileChooser();

    private JComboBox<String> commandsJComboBox;
    private JComboBox<String> typeOfViewJComboBox;
    private JComboBox<String> listToChooseLanguage;
    private JComboBox<String> loyalBoxJComboBox;

    private JButton userJButton;
    private JButton submitJOperation;

    private JPanel northJPanel;
    private JPanel centerJPanel;
    private JPanel argumentJPanel;
    private JPanel resultJPanel;

    private AddJPanel addJPanel;

    private JLabel chooseCommLabel;
    private JLabel resultOfCommandWithSpMar;
    private JTextField argumentTextField;
    private JTextArea textResult;
    private JScrollPane scrollText;

// TODO Is it good to initialize field here?;

    private GUIManager guiManager;
    private ConnectionAndExecutorManager caeManager;
    private ResourceBundle resourceBundle;
    private BasicGUIElementsFabric basicGUIElementsFabric;

    public CommandModeJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        setLayout(new BorderLayout());
        initElements();
    }

    public void initElements() {
        chooseCommLabel = basicGUIElementsFabric.createBasicLabel("Choose command");
        resultOfCommandWithSpMar = basicGUIElementsFabric.createBasicLabel("");
        listToChooseLanguage = basicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        addJPanel = new AddJPanel(resourceBundle);
        List<String> commandsBoxList = caeManager.getCommendsForGUI().stream().collect(Collectors.toList());
        String[] commandsBoxString = new String[commandsBoxList.size()];
        int i = 0;
        for (String command : commandsBoxList) {
            commandsBoxString[i] = command;
            i++;
        }
        commandsJComboBox = basicGUIElementsFabric.createBasicComboBox(commandsBoxString);
        typeOfViewJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.COMMAND_PANEL, ConstantsLanguage.TABLE_VIEW, ConstantsLanguage.VISUAL_VIEW});
        loyalBoxJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.TRUE, ConstantsLanguage.FALSE, ConstantsLanguage.FALSE});
        userJButton = new JButton();
        userJButton.setFont(Constants.MAIN_FONT);
        userJButton.setText((caeManager.getUsername()));
        userJButton.setBackground(Constants.SUB_COLOR);
        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitJOperation = basicGUIElementsFabric.createBasicButton("push");
        centerJPanel = new JPanel();
        northJPanel = new JPanel();
        resultJPanel = new JPanel();
        argumentJPanel = new JPanel();
        argumentTextField = basicGUIElementsFabric.createBasicJTextField();
        argumentTextField.setEditable(false);

        initTextArea();

        setSettings();
        add(northJPanel, BorderLayout.NORTH);
        add(centerJPanel, BorderLayout.CENTER);
    }

    private void initTextArea() {
        textResult = new JTextArea();
        textResult.setEditable(false);
        textResult.setLineWrap(true);
        textResult.setFont(Constants.SUB_FONT);
        textResult.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        scrollText = new JScrollPane(textResult);
        scrollText.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        scrollText.setBorder(BorderFactory.createEmptyBorder(Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP));
        argumentJPanel.add(scrollText);
    }

    private void setSettings() {
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
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showCommandPanel(resourceBundle);
            }
        });
    }

    private void setListenerForFileChooser() {
        executeScriptFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExecutorScriptService executor = new ExecutorScriptService(caeManager, executeScriptFileChooser.getSelectedFile(), textResult);
                executor.executeScript();
                argumentJPanel.removeAll();
                argumentJPanel.add(scrollText);
                guiManager.reloadMainScreen();
            }
        });
    }

    public void setListenerForUserButton() {
        userJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultJPanel.removeAll();
                JFrame exitFrame = new JFrame();
                Container pane = exitFrame.getContentPane();
                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                JButton yesButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.YES);
                yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                JButton noButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.NO);
                noButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel label = basicGUIElementsFabric.createBasicLabel("Do you want exit?");
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                yesButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitFrame.dispose();
                        guiManager.showLoginPanel(resourceBundle);
                    }
                });
                noButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitFrame.dispose();
                    }
                });
                exitFrame.add(label, exitFrame.getContentPane());
                exitFrame.add(yesButton, exitFrame.getContentPane());
                exitFrame.add(noButton, exitFrame.getContentPane());
                exitFrame.setSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
                exitFrame.setVisible(true);
            }
        });
    }

    private void setListenerForCommandsBox() {
        commandsJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultJPanel.removeAll();
                northJPanel.removeAll();
                northJPanel.add(chooseCommLabel);
                northJPanel.add(commandsJComboBox);
                northJPanel.add(submitJOperation);
                argumentJPanel.removeAll();
                if (resourceBundle.getString("add").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("add_if_min").equals(commandsJComboBox.getSelectedItem().toString())
                    || resourceBundle.getString("remove_greater").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("remove_lower").equals(commandsJComboBox.getSelectedItem().toString())
                    || resourceBundle.getString("update").equals(commandsJComboBox.getSelectedItem().toString())) {
                        if (resourceBundle.getString("update").equals(commandsJComboBox.getSelectedItem().toString())) {
                            argumentTextField.setEditable(true);
                            northJPanel.add(argumentTextField);
                        } else {
                            argumentTextField.setEditable(false);
                        }
                    argumentJPanel.add(addJPanel);
                } else if (resourceBundle.getString("execute_script").equals(commandsJComboBox.getSelectedItem().toString())) {
                    argumentJPanel.add(executeScriptFileChooser);
                } else if (resourceBundle.getString("count_by_loyal").equals(commandsJComboBox.getSelectedItem().toString())) {
                    argumentJPanel.add(scrollText);
                    northJPanel.add(loyalBoxJComboBox);
                } else {
                    if (resourceBundle.getString("remove_by_id").equals(commandsJComboBox.getSelectedItem().toString())) {
                        argumentTextField.setEditable(true);
                    } else {
                        argumentTextField.setEditable(false);
                    }
                    argumentJPanel.add(scrollText);
                    northJPanel.add(argumentTextField);
                }
                northJPanel.add(typeOfViewJComboBox);
                northJPanel.add(listToChooseLanguage);
                northJPanel.add(userJButton);
                guiManager.reloadMainScreen();
            }
        });
    }

    private String findRightCommand() {
        String commandChoosed = commandsJComboBox.getSelectedItem().toString();
        String commandToExecute = "";
        for (String commandName : resourceBundle.keySet()) {
            if (commandChoosed.equals(resourceBundle.getString(commandName))) {
                commandToExecute = commandName;
                break;
            }
        }
        return commandToExecute;
    }

    private void setListenerForSubmitButton() {
        submitJOperation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultJPanel.removeAll();
                SpaceMarine spMar = null;
                spMar = addJPanel.getSpaceMarine();
                String commandToExecute = findRightCommand();
                if (resourceBundle.getString("add").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("add_if_min").equals(commandsJComboBox.getSelectedItem().toString())|| resourceBundle.getString("remove_greater").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("remove_lower").equals(commandsJComboBox.getSelectedItem().toString())) {
                    if (spMar == null) {
                        printError(("Invalid arguments"));
                        return;
                    }
                    CommandResult result = caeManager.executeCommand(commandToExecute, spMar, null);
                    printMessage(result.getMessageResult());
                } else if (resourceBundle.getString("remove_by_id").equals(commandsJComboBox.getSelectedItem().toString())) {
                    try {
                        Long id = Long.parseLong(argumentTextField.getText());
                        CommandResult result = caeManager.executeCommand(commandToExecute, spMar, id);
                        printMessage(result.getMessageResult());
                    } catch (NumberFormatException exception) {
                        printError(("Invalid value for id"));
                    }
                } else if (resourceBundle.getString("update").equals(commandsJComboBox.getSelectedItem().toString())) {
                    try {
                        Long id = Long.parseLong(argumentTextField.getText());
                        if (spMar == null) {
                            printError(("Invalid arguments"));
                            return;
                        }
                        CommandResult result = caeManager.executeCommand(commandToExecute, spMar, id);
                        printMessage(result.getMessageResult());
                    } catch (NumberFormatException exception) {
                        printError("Invalid value for id");
                    }
                } else if (resourceBundle.getString("count_by_loyal").equals(commandsJComboBox.getSelectedItem().toString())) {
                    Boolean loyalPars = null;
                    String loyal = loyalBoxJComboBox.getSelectedItem().toString();
                    if (resourceBundle.getString(ConstantsLanguage.TRUE).equals(loyal)) {
                        loyalPars = true;
                    } else if (resourceBundle.getString(ConstantsLanguage.FALSE).equals(loyal)) {
                        loyalPars = false;
                    }
                    showResultOfExecuteCommand(caeManager.executeCommand("count_by_loyal", null, loyalPars));
                } else {
                    showResultOfExecuteCommand(caeManager.executeCommand(commandToExecute, null, null));
                }
                guiManager.reloadMainScreen();
            }
        });
    }

    private void printError(String message) {
        resultOfCommandWithSpMar.setText(resourceBundle.getString(message));
        resultJPanel.add(resultOfCommandWithSpMar);
        guiManager.reloadMainScreen();
    }

    private void printMessage(String message) {
        resultOfCommandWithSpMar.setText(message);
        resultJPanel.add(resultOfCommandWithSpMar);
        guiManager.reloadMainScreen();
    }

    public void setListenerForTypeOfViewBox() {
        typeOfViewJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resourceBundle.getString("Visual View").equals(typeOfViewJComboBox.getSelectedItem().toString())) {
                    guiManager.showVisualPanel(resourceBundle);
                } else if (resourceBundle.getString("Table View").equals(typeOfViewJComboBox.getSelectedItem().toString())) {
                    guiManager.showTablePanel(resourceBundle);
                }
            }
        });
    }


    public void addElementsToNorthPanel() {
        northJPanel.add(chooseCommLabel);
        northJPanel.add(commandsJComboBox);
        northJPanel.add(submitJOperation);
        northJPanel.add(argumentTextField);
        northJPanel.add(typeOfViewJComboBox);
        northJPanel.add(listToChooseLanguage);
        northJPanel.add(userJButton);
    }

    private void setSettingsForElements() {
        northJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        northJPanel.setMaximumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));

        centerJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        centerJPanel.setLayout(new BorderLayout());
        resultJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        argumentJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));

        chooseCommLabel.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        commandsJComboBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        loyalBoxJComboBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        argumentTextField.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitJOperation.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        typeOfViewJComboBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
    }

    private void addElementsToCenterPanel() {
        centerJPanel.add(argumentJPanel, BorderLayout.CENTER);
        centerJPanel.add(resultJPanel, BorderLayout.SOUTH);
    }

    private void showResultOfExecuteCommand(CommandResult commandResult) {
        textResult.setText(commandResult.getMessageResult());
        resultOfCommandWithSpMar.setText("");
        guiManager.reloadMainScreen();
    }

    private void setSettingsForSortPanel() {
        submitJOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
