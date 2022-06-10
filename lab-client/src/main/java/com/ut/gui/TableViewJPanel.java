package com.ut.gui;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.toedter.calendar.JDateChooser;
import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.data.SpaceMarine;
import com.ut.exeptions.ConnectionLostExeption;
import com.ut.util.Constants;
import com.ut.util.ConstantsLanguage;
import com.ut.util.FilterSpaceMarine;
import com.ut.util.ParsList;
import com.ut.util.SortSpaceMarine;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

public class TableViewJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> sortOrfilterJComboBox;
    private JComboBox<String> fieldForSOFJComboBox;
    private JComboBox<String> typeOfSortJComboBox;
    private JComboBox<String> typeOfFilterJComboBox;
    private JComboBox<String> typeOfViewJComboBox;
    private JComboBox<String> listToChooseLanguage;

    private JButton userJButton;
    private JButton submitArgumentJButtonOperationJButon;

    private JPanel northJPanel;
    private JPanel centerJPanel;
    private JPanel argumentFilterJPanel;

    private JTextField fieldForHours;
    private JTextField fieldForMinutes;
    private JTextField fieldForSeconds;

    private JTextField argumentJTextField;
    private JLabel argumentJLabel;
    private JButton submitArgumentJButton;

    private JTable jTable;
    private JDateChooser jDateChooser;

    private JComboBox<String> loyaJComboBox;
    private JComboBox<String> categoryJComboBox;
    private JLabel resultOfOperationJLabel;

    private JFrame filterValueFrame;
    private ChangeFieldsSpaceMarine changeFieldsSpaceMarine;

    private String[] tableHeader;
    private String[][] tableRows;
    private List<SpaceMarine> listForTable;
    private int amoutnOfRows;

    private GUIManager guiManager;
    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;
    private BasicGUIElementsFabric basicGUIElementsFabric;

    public TableViewJPanel(GUIManager guiManager, ResourceBundle resourceBundle, ConnectionAndExecutorManager caeManager) {
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        this.resourceBundle = resourceBundle;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        setLayout(new BorderLayout());
        initElements();
    }

    private void initElements() {
        centerJPanel = new JPanel();
        northJPanel = new JPanel();
        argumentFilterJPanel = new JPanel();
        filterValueFrame = new JFrame();
        resultOfOperationJLabel = new JLabel();
        resultOfOperationJLabel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        resultOfOperationJLabel.setFont(Constants.SUB_FONT);
        amoutnOfRows = 0;
        initTableHeader();
        createJComboBox();
        getListOFSpaceMarine();

        userJButton = new JButton();
        userJButton.setFont(Constants.MAIN_FONT);
        userJButton.setText((caeManager.getUsername()));
        userJButton.setBackground(Constants.SUB_COLOR);
        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));

        submitArgumentJButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.FILTER);
        submitArgumentJButtonOperationJButon = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.SORT);
        jDateChooser = new JDateChooser();
        jDateChooser.setPreferredSize(new Dimension(Constants.DATE_CHOOSER_WIDTH, Constants.DATE_CHOOSER_HEIGHT));
        jDateChooser.getJCalendar().setPreferredSize(new Dimension(Constants.CALENDAR_WIDTH, Constants.CALENDAR_HEIGHT));
        fieldForHours = basicGUIElementsFabric.createBasicJTextField(ConstantsLanguage.HH);
        fieldForMinutes = basicGUIElementsFabric.createBasicJTextField(ConstantsLanguage.MM);
        fieldForSeconds = basicGUIElementsFabric.createBasicJTextField(ConstantsLanguage.SS);

        setSettings();
        addElementsToNorthPanel(typeOfSortJComboBox);
        add(northJPanel, BorderLayout.NORTH);
        add(centerJPanel, BorderLayout.CENTER);
    }

    private void createJComboBox() {
        sortOrfilterJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.SORT, ConstantsLanguage.FILTER});
        fieldForSOFJComboBox = basicGUIElementsFabric.createBasicComboBox(tableHeader);
        typeOfSortJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.INCREASE, ConstantsLanguage.DECREASE});
        typeOfFilterJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.EQUALS, ConstantsLanguage.GREATER, ConstantsLanguage.LOWER});
        typeOfViewJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.TABLE_VIEW, ConstantsLanguage.VISUAL_VIEW, ConstantsLanguage.COMMAND_PANEL});
        loyaJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.TRUE, ConstantsLanguage.FALSE, ConstantsLanguage.NULL});
        categoryJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.AGGRESSOR, ConstantsLanguage.HELIX, ConstantsLanguage.TACTICAL, ConstantsLanguage.INCEPTOR});
    }

    private void setSettings() {
        setSettingsForElements();
        setListenerForTypeOfViewBox();
        setListenerForSortOrFilterBox();
        setListenerForUserButton();
        setSettingForLanguagesList();
        setSettingsForTable();
    }

    private void initTableHeader() {
        tableHeader = new String[]{
            ConstantsLanguage.ID,
            ConstantsLanguage.NAME,
            ConstantsLanguage.CREATION_TIME,
            ConstantsLanguage.X,
            ConstantsLanguage.Y,
            ConstantsLanguage.HEALTH,
            ConstantsLanguage.HEART_COUNT,
            ConstantsLanguage.LOYAL,
            ConstantsLanguage.CATEGORY,
            ConstantsLanguage.CHAPTER,
            ConstantsLanguage.PARENT_LEGION,
            ConstantsLanguage.MARINES_COUNT,
            ConstantsLanguage.WORLD,
            ConstantsLanguage.OWNERNAME
        };
    }

    private void getListOFSpaceMarine() {
        try {
            listForTable = caeManager.getListFromServer();
        } catch (ConnectionLostExeption e) {
            printerror(ConstantsLanguage.ERROR_TO_CONNECT_TO_SERVER);
            listForTable = new ArrayList<>();
        }
    }

    private void setListenerForUserButton() {
        userJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame exitFrame = new JFrame();
                Container pane = exitFrame.getContentPane();
                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                JButton yesButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.YES);
                yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                JButton noButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.NO);
                noButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel label = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.EXIT_QUESTION);
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

    private void setListenerForTypeOfViewBox() {
        typeOfViewJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resourceBundle.getString(ConstantsLanguage.VISUAL_VIEW).equals(typeOfViewJComboBox.getSelectedItem().toString())) {
                    guiManager.showVisualPanel(resourceBundle);
                } else if (resourceBundle.getString(ConstantsLanguage.COMMAND_PANEL).equals(typeOfViewJComboBox.getSelectedItem().toString())) {
                    guiManager.showCommandPanel(resourceBundle);
                }
            }
        });
    }

    private void addElementsToNorthPanel(JComboBox<String> currentFieldForOperationJComboBox) {
        northJPanel.add(sortOrfilterJComboBox);
        northJPanel.add(fieldForSOFJComboBox);
        northJPanel.add(currentFieldForOperationJComboBox);
        northJPanel.add(typeOfViewJComboBox);
        northJPanel.add(submitArgumentJButtonOperationJButon);
        northJPanel.add(listToChooseLanguage);
        northJPanel.add(userJButton);
    }

    private void setSettingsForTable() {
        centerJPanel.removeAll();
        tableRows = ParsList.parseList(listForTable, resourceBundle);
        amoutnOfRows = tableRows.length;
        jTable = new JTable(tableRows, tableHeader) {
            private static final long serialVersionUID = 1L;
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                updateRow(rowIndex);
            }
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        jTable.setVisible(true);
        jTable.setPreferredSize(new Dimension(Constants.RIGHT_OF_CENTER_SIZE, Constants.ROW_HEIGHT * amoutnOfRows));
        jTable.setFont(Constants.SUB_FONT);
        jTable.setRowHeight(Constants.ROW_HEIGHT);
        jTable.getTableHeader().setFont(Constants.SUB_FONT);
        jTable.setFillsViewportHeight(true);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(Constants.RIGHT_OF_CENTER_SIZE, Constants.CENTER_PANEL_HEIGHT));
        centerJPanel.add(jScrollPane);
    }

    private void updateRow(int rowIndex) {
        Long id = Long.parseLong(tableRows[rowIndex][0]);
        SpaceMarine editSpaMar = null;
        for (SpaceMarine spMar : listForTable) {
            if (id.equals(spMar.getID())) {
                editSpaMar = spMar;
            }
        }
        if (caeManager.getUsername().equals(editSpaMar.getOwnerName())) {
            changeFieldsSpaceMarine.showJFrame(editSpaMar);
        } else {
            JFrame frameForInvalidRules = new JFrame();
            JPanel mainPanel = new JPanel();
            JLabel jLabel = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.NOT_YOUR_SPMAR_MESSAGE);
            frameForInvalidRules.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(jLabel);
            JButton exitButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.OK);
            exitButton.setAlignmentX(CENTER_ALIGNMENT);
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new GridBagLayout());
            subPanel.add(exitButton);
            mainPanel.add(subPanel);
            exitButton.addActionListener(e1 -> frameForInvalidRules.dispose());
            frameForInvalidRules.setContentPane(mainPanel);
            frameForInvalidRules.revalidate();
            frameForInvalidRules.pack();
            frameForInvalidRules.setLocationRelativeTo(null);
            frameForInvalidRules.setVisible(true);
        }
    }

    private void setSettingsForElements() {
        changeFieldsSpaceMarine = new ChangeFieldsSpaceMarine(guiManager, caeManager, resourceBundle, true);

        northJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));

        centerJPanel.setBorder(BorderFactory.createEmptyBorder(Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP));
        centerJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));

        sortOrfilterJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        fieldForSOFJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        typeOfSortJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        typeOfViewJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        typeOfFilterJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));

        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitArgumentJButtonOperationJButon.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        setListenerForSubmitButton();
        setListenerForArgumentJButton(filterValueFrame);
    }

    private void setListenerForSortOrFilterBox() {
        sortOrfilterJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                northJPanel.removeAll();
                String type = sortOrfilterJComboBox.getSelectedItem().toString();
                if (resourceBundle.getString(ConstantsLanguage.SORT).equals(type)) {
                    submitArgumentJButtonOperationJButon.setText(resourceBundle.getString(ConstantsLanguage.SORT));
                    addElementsToNorthPanel(typeOfSortJComboBox);
                } else {
                    submitArgumentJButtonOperationJButon.setText(resourceBundle.getString(ConstantsLanguage.INPUT));
                    addElementsToNorthPanel(typeOfFilterJComboBox);
                }
                guiManager.reloadMainScreen();
            }
        });
    }

    private void setListenerForSubmitButton() {
        submitArgumentJButtonOperationJButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resourceBundle.getString(ConstantsLanguage.INPUT).equals(submitArgumentJButtonOperationJButon.getText())) {
                    createJFrameForFilterInput();
                    filterValueFrame.setVisible(true);
                } else {
                    try {
                        listForTable = SortSpaceMarine.sortSpaceMarines(findRightName(fieldForSOFJComboBox.getSelectedItem().toString()), findRightName(typeOfSortJComboBox.getSelectedItem().toString()), caeManager.getListFromServer());
                        setSettingsForTable();
                        guiManager.reloadMainScreen();
                    } catch (ConnectionLostExeption e2) {
                        printerror(ConstantsLanguage.ERROR_TO_CONNECT_TO_SERVER);
                        listForTable = new ArrayList<>();
                        setSettingsForTable();
                        guiManager.reloadMainScreen();
                    }
                }
            }
        });
    }

    private void createJFrameForFilterInput() {
        argumentFilterJPanel.removeAll();
        argumentJLabel = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.ENTER_VALUE);
        argumentFilterJPanel.setLayout(new BoxLayout(argumentFilterJPanel, BoxLayout.Y_AXIS));
        argumentFilterJPanel.add(argumentJLabel);
        if (resourceBundle.getString(ConstantsLanguage.LOYAL).equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
            argumentFilterJPanel.add(loyaJComboBox);
        } else if (resourceBundle.getString(ConstantsLanguage.CATEGORY).equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
            argumentFilterJPanel.add(categoryJComboBox);
        } else if (resourceBundle.getString(ConstantsLanguage.CREATION_TIME).equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
            argumentFilterJPanel.add(jDateChooser);
            argumentFilterJPanel.add(fieldForHours);
            argumentFilterJPanel.add(fieldForMinutes);
            argumentFilterJPanel.add(fieldForSeconds);
        } else {
            argumentJTextField = basicGUIElementsFabric.createBasicJTextField();
            argumentFilterJPanel.add(argumentJTextField);
        }
        argumentFilterJPanel.add(submitArgumentJButton);
        filterValueFrame.add(argumentFilterJPanel);
        filterValueFrame.setSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
        filterValueFrame.revalidate();
        filterValueFrame.repaint();
    }

    private String findRightName(String bundleName) {
        String rightName = "";
        for (String key : resourceBundle.keySet()) {
            if (bundleName.equals(resourceBundle.getString(key))) {
                rightName = key;
                break;
            }
        }
        return rightName;
    }

    private void setListenerForArgumentJButton(JFrame frame) {
        submitArgumentJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String value = takeArgumentsFromInput(fieldForSOFJComboBox.getSelectedItem().toString());
                    listForTable = FilterSpaceMarine.filterList(findRightName(fieldForSOFJComboBox.getSelectedItem().toString()), findRightName(typeOfFilterJComboBox.getSelectedItem().toString()), value, caeManager.getListFromServer());
                    setSettingsForTable();
                    frame.dispose();
                    guiManager.reloadMainScreen();
                } catch (ConnectionLostExeption e1) {
                    printerror(ConstantsLanguage.ERROR_TO_CONNECT_TO_SERVER);
                    listForTable = new ArrayList<>();
                    setSettingsForTable();
                    frame.dispose();
                    guiManager.reloadMainScreen();
                } catch (NumberFormatException e2) {
                    printerror(ConstantsLanguage.NUMBER_FORMAT_EXCEPTION);
                    listForTable = new ArrayList<>();
                    setSettingsForTable();
                    frame.dispose();
                    guiManager.reloadMainScreen();
                }
            }
        });
    }

    private String takeArgumentsFromInput(String command) {
        String value = "";
        boolean returnStatus = false;
        if (resourceBundle.getString(ConstantsLanguage.LOYAL).equals(command)) {
            value = findRightName(loyaJComboBox.getSelectedItem().toString());
            returnStatus = true;
        }
        if (resourceBundle.getString(ConstantsLanguage.CATEGORY).equals(command)) {
            value = findRightName(categoryJComboBox.getSelectedItem().toString());
            returnStatus = true;
        }
        if (resourceBundle.getString(ConstantsLanguage.CREATION_TIME).equals(command)) {
            Calendar calendar = jDateChooser.getCalendar();
            int hours = Integer.parseInt(fieldForHours.getText());
            int minutes = Integer.parseInt(fieldForMinutes.getText());
            int seconds = Integer.parseInt(fieldForSeconds.getText());
            Calendar calendarResult = new GregorianCalendar();
            calendarResult.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendarResult.get(Calendar.DAY_OF_MONTH), hours, minutes, seconds);
            Date result = calendarResult.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            value = dateFormat.format(result);
            returnStatus = true;
        }
        if (!returnStatus) {
            value = argumentJTextField.getText();
        }
        return value;
    }

    private void printerror(String error) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
        JButton okButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.OK);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
            }
        });
        JLabel errorLabel = basicGUIElementsFabric.createBasicLabel((error));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.add(errorLabel, BorderLayout.CENTER);
        frame.add(okButton, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage = basicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showTablePanel(resourceBundle);
            }
        });
    }

    public void addRowToTable(SpaceMarine spaceMarine) {
        listForTable.remove(listForTable.stream().filter(s -> s.getID().equals(spaceMarine.getID())).findFirst().get());
        listForTable.add(spaceMarine);
        setSettingsForTable();
        guiManager.reloadMainScreen();
    }
}
