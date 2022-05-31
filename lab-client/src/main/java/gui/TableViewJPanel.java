package gui;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.commands.ConnectToServerCommand;
import com.ut.common.data.SpaceMarine;

import lombok.extern.slf4j.Slf4j;
import util.Constants;
import util.ParsList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

@Slf4j
public class TableViewJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> sortOrfilterBox;
    private JComboBox<String> fieldForSOFBox;
    private JComboBox<String> typeOfSort;
    private JComboBox<String> typeOfFilter;
    private JComboBox<String> typeOfView;
    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);

    private JButton userButton;
    private JButton submitOperation;
    
    private JPanel northPanel;
    private JPanel centerPanel;

    private JTextField argumentTextField;
    private JLabel argumentTextLabel;
    private JButton submit = new JButton();

// TODO Is it good to initialize field in this;
    
    private JTable jTable;

    private JLabel resultOfOperation = new JLabel();

    private String[] columnNames;
    private List<SpaceMarine> listForTable;
    private Map<String, Function<SpaceMarine, Object>> sortFieldMap;

    private GUIManager guiManager;
    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;
    private Locale locale;

    public TableViewJPanel(GUIManager guiManager, ResourceBundle resourceBundle, ConnectionAndExecutorManager caeManager, String[] columnNames, List<SpaceMarine> listForTable, Locale locale) {
        this.guiManager = guiManager;
        this.columnNames = columnNames;
        this.listForTable = listForTable;
        this.caeManager = caeManager;
        this.resourceBundle = resourceBundle;
        this.locale = locale;
        setLayout(new BorderLayout());

        sortFieldMap = new HashMap<>();
        sortFieldMap.put("id", SpaceMarine::getID);
        sortFieldMap.put("name", SpaceMarine::getName);
        sortFieldMap.put("creation Time", SpaceMarine::getCreationDateTime);
        sortFieldMap.put("coordinate X", s -> s.getCoordinates().getX());
        sortFieldMap.put("coordinate Y", s -> s.getCoordinates().getY());
        sortFieldMap.put("health", SpaceMarine::getHealth);
        sortFieldMap.put("heart count", SpaceMarine::getHeartCount);
        
        initElements();
    }
    
    public void initElements() {

        sortOrfilterBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{"Sort", "Filter"});
        fieldForSOFBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{"id", "name", "creation Time", "health", "heart count"});
        typeOfSort = BasicGUIElementsFabric.createBasicComboBox(new String[]{"increase", "decrease"});
        typeOfFilter = BasicGUIElementsFabric.createBasicComboBox(new String[]{"equals", "greater", "lower"});
        typeOfView = BasicGUIElementsFabric.createBasicComboBox(new String[]{"Table View", "Visual View", "Command Panel"});
        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setBackground(Constants.SUB_COLOR);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));

        userButton = BasicGUIElementsFabric.createBasicButton("username");
        submitOperation = BasicGUIElementsFabric.createBasicButton("sort");
    
        centerPanel = new JPanel();
        northPanel = new JPanel();
    
        setListenerForTypeOfViewBox();
        setSettingsForElements();
        setListenerForSortOrFilterBox();
        setListenerForUserButton();
        setListenerForSubmitButton();
        divideCentralPanelIntoSeveralParts();
        setSettingForLanguagesList();
        addElementsToNorthPanel();
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        setSettingsForTable();
        resultOfOperation = new JLabel();
        resultOfOperation.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 9 * 8, Constants.SCREEN_HEIGHT));
        resultOfOperation.setFont(Constants.SUB_FONT);
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
                exitFrame.setSize(new Dimension(Constants.SCREEN_WIDTH / 9 * 8, Constants.SCREEN_HEIGHT));
                exitFrame.setVisible(true);
            }
        });
    }

    public void setListenerForTypeOfViewBox() {
        typeOfView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Visual View".equals(typeOfView.getSelectedItem().toString())) {
                    log.info("Choosed Visual View");
                    guiManager.showVisualPanel(resourceBundle);
                } else if ("Command Panel".equals(typeOfView.getSelectedItem().toString())) {
                    log.info("Choosed Command Panel");
                    guiManager.showCommandPanel(resourceBundle);
                }
            }
        });
    }

    public void addElementsToNorthPanel() {
        northPanel.add(sortOrfilterBox);
        northPanel.add(fieldForSOFBox);
        northPanel.add(typeOfSort);
        northPanel.add(typeOfView);
        northPanel.add(submitOperation);
        northPanel.add(listToChooseLanguage);
        northPanel.add(userButton);
    }

    private void setSettingsForTable() {
        centerPanel.removeAll();
        jTable = new JTable(ParsList.parseList(listForTable, locale), columnNames);
        jTable.setEnabled(false);
        jTable.setVisible(true);
        jTable.setPreferredScrollableViewportSize(new Dimension(Constants.RIGHT_OF_CENTER_SIZE / 8 * 7, Constants.SCREEN_HEIGHT));
        jTable.setPreferredSize(new Dimension(Constants.RIGHT_OF_CENTER_SIZE / 8 * 7, Constants.SCREEN_HEIGHT));
        jTable.setFont(Constants.SUB_FONT);
        jTable.setRowHeight(Constants.ROW_HEIGHT);
        jTable.getTableHeader().setFont(Constants.SUB_FONT);
        jTable.setFillsViewportHeight(true);
        centerPanel.add(new JScrollPane(jTable));
        log.info("relodMainScreen");
    }

    private void setSettingsForElements() {
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        northPanel.setBackground(Color.PINK);

        centerPanel.setBackground(Color.BLUE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        centerPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 8 * 7, Constants.CENTER_PANEL_HEIGHT));

        sortOrfilterBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        fieldForSOFBox.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        typeOfSort.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        typeOfView.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        typeOfFilter.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));

        userButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitOperation.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        }

    public void setListenerForSortOrFilterBox() {
        sortOrfilterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                northPanel.removeAll();
                String type = sortOrfilterBox.getSelectedItem().toString();
                log.info("In listener");
                if ("Sort".equals(type)) {
                    log.info("In sort listener");
                    northPanel.add(sortOrfilterBox);
                    northPanel.add(fieldForSOFBox);
                    northPanel.add(typeOfSort);
                    northPanel.add(typeOfView);
                    northPanel.add(listToChooseLanguage);
                    submitOperation.setText("sort");
                    northPanel.add(submitOperation);
                    northPanel.add(listToChooseLanguage);
                    northPanel.add(userButton);
                } else {
                    log.info("In filter listener");
                    northPanel.add(sortOrfilterBox);
                    northPanel.add(fieldForSOFBox);
                    northPanel.add(typeOfFilter);
                    northPanel.add(typeOfView);
                    northPanel.add(listToChooseLanguage);
                    submitOperation.setText("input");
                    northPanel.add(submitOperation);
                    northPanel.add(listToChooseLanguage);
                    northPanel.add(userButton);
                }
                guiManager.reloadMainScreen();
            }
        });
    }

    private void divideCentralPanelIntoSeveralParts() {
        centerPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 9 * 8, Constants.CENTER_PANEL_HEIGHT));
    }

    private void setListenerForSubmitButton() {
        submitOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (submitOperation.getText().equals("input")) {
                    log.info("Filter operation filtered");
                    JFrame frame = new JFrame();
                    JPanel panel = new JPanel();
                    argumentTextField = BasicGUIElementsFabric.createBasicJTextField();
                    argumentTextLabel = BasicGUIElementsFabric.createBasicLabel("Enter value of field: " + fieldForSOFBox.getSelectedItem().toString());
                    submit = BasicGUIElementsFabric.createBasicButton("filter");
                    submit.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String value = (String) argumentTextField.getText();
                            String field = (String) fieldForSOFBox.getSelectedItem().toString();
                            String typeFilter = (String) typeOfFilter.getSelectedItem().toString();
                            CommandResult newCommandResult = caeManager.executeCommand("show", null, null);
                            if (Objects.isNull(newCommandResult)) {
                                printerror("Connection lost");
                            }
                            listForTable = FilterSpaceMarine.filterList(field, typeFilter, value, (List<SpaceMarine>) newCommandResult.getData());
                            listForTable.stream().forEach(System.out::println);
                            setSettingsForTable();
                            frame.dispose();
                            guiManager.reloadMainScreen();
                        }
                    });
                    panel.add(argumentTextField);
                    panel.add(argumentTextLabel);
                    panel.add(submit);
                    frame.add(panel);
                    frame.setSize(new Dimension(Constants.SCREEN_WIDTH / 9 * 8, Constants.SCREEN_HEIGHT));
                    frame.setVisible(true);
                }
                if (submitOperation.getText().equals("sort")) {
                    String sortField = fieldForSOFBox.getSelectedItem().toString();
                    String typeSort = typeOfSort.getSelectedItem().toString();
                    CommandResult newCommandResult = caeManager.executeCommand("show", null, null);
                    if (Objects.isNull(newCommandResult)) {
                        printerror("Connection lost");
                    }
                    listForTable = SortSpaceMarine.sortSpaceMarines(sortField, typeSort, (List<SpaceMarine>) newCommandResult.getData());
                    listForTable.stream().forEach(System.out::println);
                    setSettingsForTable();
                    guiManager.reloadMainScreen();
                }
            }
        });
    }

    private void printerror(String message) {

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
}
