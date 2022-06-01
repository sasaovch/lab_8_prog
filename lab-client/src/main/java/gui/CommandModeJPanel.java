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

import util.Constants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CommandModeJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JFileChooser executeScriptFileChooser = new JFileChooser();

    private JComboBox<String> commandsJComboBox;
    private JComboBox<String> typeOfViewJComboBox;
    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
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

    public CommandModeJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setLayout(new BorderLayout());
        initElements();
    }

    public void initElements() {
        chooseCommLabel = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Choose command"));
        resultOfCommandWithSpMar = BasicGUIElementsFabric.createBasicLabel("");

        List<String> commandsBoxList = caeManager.getCommendsForGUI().stream().map(resourceBundle::getString).collect(Collectors.toList());
        String[] commandsBoxString = new String[commandsBoxList.size()];
        int i = 0;
        for (String command : commandsBoxList) {
            commandsBoxString[i] = command;
            i++;
        }
        commandsJComboBox = BasicGUIElementsFabric.createBasicComboBox(commandsBoxString);
        typeOfViewJComboBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{resourceBundle.getString("Command Panel"), resourceBundle.getString("Table View"), resourceBundle.getString("Visual View")});
        loyalBoxJComboBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{resourceBundle.getString("null"), resourceBundle.getString("true"), resourceBundle.getString("false")});
        userJButton = BasicGUIElementsFabric.createBasicButton(caeManager.getUsername());
        submitJOperation = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("push"));

        centerJPanel = new JPanel();
        northJPanel = new JPanel();
        resultJPanel = new JPanel();
        argumentJPanel = new JPanel();

        argumentTextField = BasicGUIElementsFabric.createBasicJTextField();

        textResult = new JTextArea();
        textResult.setEditable(false);
        textResult.setLineWrap(true);
        textResult.setFont(Constants.SUB_FONT);
        textResult.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        scrollText = new JScrollPane(textResult);
        scrollText.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        scrollText.setBorder(BorderFactory.createEmptyBorder(Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP));
        argumentJPanel.add(scrollText);

        setSettings();
        add(northJPanel, BorderLayout.NORTH);
        add(centerJPanel, BorderLayout.SOUTH);
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
        userJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultJPanel.removeAll();
                JFrame exitFrame = new JFrame();
                JPanel panel = new JPanel();
                JButton yesButton = new JButton(resourceBundle.getString("YES"));
                JButton noButton = new JButton(resourceBundle.getString("NO"));
                JLabel label = new JLabel(resourceBundle.getString("Do you want exit?"));
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
                panel.add(yesButton);
                panel.add(noButton);
                panel.add(label);
                exitFrame.add(panel);
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
                    argumentJPanel.add(new AddJPanel(caeManager, resourceBundle));
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
                String commandToExecute = findRightCommand();
                if (resourceBundle.getString("add").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("add_if_min").equals(commandsJComboBox.getSelectedItem().toString())
                || resourceBundle.getString("remove_greater").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("remove_lower").equals(commandsJComboBox.getSelectedItem().toString())
                || resourceBundle.getString("update").equals(commandsJComboBox.getSelectedItem().toString())) {
                    spMar = addJPanel.getSpaceMarine();
                    if (spMar == null) {
                        printError(resourceBundle.getString("Invalid arguments"));
                        return;
                    }
                    CommandResult result = caeManager.executeCommand(commandToExecute, spMar, null);
                    resultOfCommandWithSpMar.setText(result.getMessageResult());
                } else if (resourceBundle.getString("update").equals(commandsJComboBox.getSelectedItem().toString()) || resourceBundle.getString("remove_by_id").equals(commandsJComboBox.getSelectedItem().toString())) {
                    try {
                        Long id = Long.parseLong(argumentTextField.getText());
                        CommandResult result = caeManager.executeCommand(commandToExecute, spMar, id);
                        resultOfCommandWithSpMar.setText(result.getMessageResult());
                    } catch (NumberFormatException exception) {
                        printError(resourceBundle.getString("Invalid value for id"));
                    }
                } else if (resourceBundle.getString("count_by_loyal").equals(commandsJComboBox.getSelectedItem().toString())) {
                    Boolean loyalPars;
                    String loyal = loyalBoxJComboBox.getSelectedItem().toString();
                    if (resourceBundle.getString("null").equals(loyal)) {
                        loyalPars = null;
                    } else {
                        loyalPars = Boolean.parseBoolean(loyal);
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
        northJPanel.setBackground(Color.ORANGE);

        centerJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        centerJPanel.setLayout(new BorderLayout());
        centerJPanel.setBackground(Color.BLACK);

        resultJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        resultJPanel.setBackground(Color.BLUE);

        argumentJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        argumentJPanel.setBackground(Color.GREEN);

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
