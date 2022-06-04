package gui;


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

import exeptions.ConnectionLostExeption;
import util.Constants;
import util.ConstantsLanguage;
import util.FilterSpaceMarine;
import util.ParsList;
import java.awt.Component;
import util.SortSpaceMarine;

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

    private JTextField fieldForHours;
    private JTextField fieldForMinutes;
    private JTextField fieldForSeconds;

    private JTextField argumentJTextField;
    private JLabel argumentJLabel;
    private JButton submitArgumentJButton;

// TODO Is it good to initialize field in this;

    private JTable jTable;
    private JDateChooser jDateChooser;

    private JComboBox<String> loyaJComboBox;
    private JComboBox<String> categoryJComboBox;
    private JLabel resultOfOperationJLabel;

    private String[] tableHeader;
    private String[][] tableRows;
    private List<SpaceMarine> listForTable;

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

    public void initElements() {
        centerJPanel = new JPanel();
        northJPanel = new JPanel();
        resultOfOperationJLabel = new JLabel();
        resultOfOperationJLabel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        resultOfOperationJLabel.setFont(Constants.SUB_FONT);

        tableHeader = new String[]{("id"), ("name"), ("creation Time"), ("x"), ("y"), ("health"), ("heartCount"), ("loyal"), ("category"), ("chapter"), ("parentLegion"), ("marinesCount"), ("world"), ("ownername")};
        sortOrfilterJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{("Sort"), ("Filter")});
        fieldForSOFJComboBox = basicGUIElementsFabric.createBasicComboBox(tableHeader);
        typeOfSortJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{("increase"), ("decrease")});
        typeOfFilterJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{("equals"), ("greater"), ("lower")});
        typeOfViewJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.TABLE_VIEW, ConstantsLanguage.VISUAL_VIEW, ConstantsLanguage.COMMAND_PANEL});

        listToChooseLanguage = basicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        setSettingForLanguagesList();

        userJButton = new JButton();
        userJButton.setFont(Constants.MAIN_FONT);
        userJButton.setText((caeManager.getUsername()));
        userJButton.setBackground(Constants.SUB_COLOR);
        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitArgumentJButton = basicGUIElementsFabric.createBasicButton(("filter"));
        submitArgumentJButtonOperationJButon = basicGUIElementsFabric.createBasicButton(("sort"));
        loyaJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.TRUE, ConstantsLanguage.FALSE, ConstantsLanguage.FALSE});
        categoryJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.AGGRESSOR, ConstantsLanguage.HELIX, ConstantsLanguage.TACTICAL, ConstantsLanguage.INCEPTOR});
        jDateChooser = new JDateChooser();
        jDateChooser.setPreferredSize(new Dimension(Constants.DATE_CHOOSER_WIDTH, Constants.DATE_CHOOSER_HEIGHT));
        jDateChooser.getJCalendar().setPreferredSize(new Dimension(Constants.CALENDAR_WIDTH, Constants.CALENDAR_HEIGHT));
        fieldForHours = basicGUIElementsFabric.createBasicJTextField();
        fieldForMinutes = basicGUIElementsFabric.createBasicJTextField();
        fieldForSeconds = basicGUIElementsFabric.createBasicJTextField();
        setSettings();
        addElementsToNorthPanel(typeOfSortJComboBox);
        add(northJPanel, BorderLayout.NORTH);
        add(centerJPanel, BorderLayout.CENTER);
        getListOFSpaceMarine();
        setSettingsForTable();
    }

    private void setSettings() {
        setSettingsForElements();
        setListenerForTypeOfViewBox();
        setListenerForSortOrFilterBox();
        setListenerForUserButton();
        setListenerForSubmitButton();
        setSettingForLanguagesList();
    }

    public void getListOFSpaceMarine() {
        try {
            listForTable = caeManager.getListFromServer();
        } catch (ConnectionLostExeption e) {
            printerror(("Error to connect to server"));
            listForTable = new ArrayList<>();
        }
    }

    public void setListenerForUserButton() {
        userJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame exitFrame = new JFrame();
                Container pane = exitFrame.getContentPane();
                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                JButton yesButton = basicGUIElementsFabric.createBasicButton("YES");
                yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                JButton noButton = basicGUIElementsFabric.createBasicButton("NO");
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

    public void setListenerForTypeOfViewBox() {
        typeOfViewJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resourceBundle.getString("Visual View").equals(typeOfViewJComboBox.getSelectedItem().toString())) {
                    guiManager.showVisualPanel(resourceBundle);
                } else if (resourceBundle.getString("Command Panel").equals(typeOfViewJComboBox.getSelectedItem().toString())) {
                    guiManager.showCommandPanel(resourceBundle);
                }
            }
        });
    }

    public void addElementsToNorthPanel(JComboBox<String> currentFieldForOperationJComboBox) {
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
        jTable = new JTable(tableRows, tableHeader){
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
        jTable.setPreferredScrollableViewportSize(new Dimension(Constants.RIGHT_OF_CENTER_SIZE, Constants.SCREEN_HEIGHT));
        jTable.setPreferredSize(new Dimension(Constants.RIGHT_OF_CENTER_SIZE, Constants.SCREEN_HEIGHT));
        jTable.setFont(Constants.SUB_FONT);
        jTable.setRowHeight(Constants.ROW_HEIGHT);
        jTable.getTableHeader().setFont(Constants.SUB_FONT);
        jTable.setFillsViewportHeight(true);
        centerJPanel.add(new JScrollPane(jTable));
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
            ChangeFieldsSpaceMarine changeFieldsSpaceMarine = new ChangeFieldsSpaceMarine(caeManager, resourceBundle, editSpaMar);
            changeFieldsSpaceMarine.showJFrame();
        } else {
            JFrame subFrame = new JFrame();
            JPanel mainPanel = new JPanel();
            JLabel jLabel = basicGUIElementsFabric.createBasicLabel(("It is not your spaceMarine"));
            subFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(jLabel);
            JButton exitButton = basicGUIElementsFabric.createBasicButton(("OK"));
            exitButton.setAlignmentX(CENTER_ALIGNMENT);
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new GridBagLayout());
            subPanel.add(exitButton);
            mainPanel.add(subPanel);
            exitButton.addActionListener(e1 -> subFrame.dispose());
            subFrame.setContentPane(mainPanel);
            subFrame.revalidate();
            subFrame.pack();
            subFrame.setLocationRelativeTo(null);
            subFrame.setVisible(true);
        }
    }

    private void setSettingsForElements() {
        northJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));

        centerJPanel.setBorder(BorderFactory.createEmptyBorder(Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP, Constants.BORDER_GAP));
        centerJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));

        sortOrfilterJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        fieldForSOFJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        typeOfSortJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        typeOfViewJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        typeOfFilterJComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));

        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        submitArgumentJButtonOperationJButon.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        }

    public void setListenerForSortOrFilterBox() {
        sortOrfilterJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                northJPanel.removeAll();
                String type = sortOrfilterJComboBox.getSelectedItem().toString();
                if (resourceBundle.getString("Sort").equals(type)) {
                    submitArgumentJButtonOperationJButon.setText(resourceBundle.getString("sort"));
                    addElementsToNorthPanel(typeOfSortJComboBox);
                } else {
                    submitArgumentJButtonOperationJButon.setText(resourceBundle.getString("input"));
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
                if (resourceBundle.getString("input").equals(submitArgumentJButtonOperationJButon.getText())) {
                    JFrame frame = createJFrameForFilterInput();
                    setListenerForArgumentJButton(frame);
                    frame.setVisible(true);
                } else {
                    try {
                        listForTable = SortSpaceMarine.sortSpaceMarines(findRightName(fieldForSOFJComboBox.getSelectedItem().toString()), findRightName(typeOfSortJComboBox.getSelectedItem().toString()), caeManager.getListFromServer());
                        setSettingsForTable();
                        guiManager.reloadMainScreen();
                    } catch (ConnectionLostExeption e2) {
                        printerror(resourceBundle.getString("Error to connect to server"));
                        listForTable = new ArrayList<>();
                        setSettingsForTable();
                        guiManager.reloadMainScreen();
                    }
                }
            }
        });
    }

    private JFrame createJFrameForFilterInput() {
        JPanel panel = new JPanel();
        argumentJLabel = basicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Enter value of field: ") + fieldForSOFJComboBox.getSelectedItem().toString());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(argumentJLabel);
        if (resourceBundle.getString("loyal").equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
            panel.add(loyaJComboBox);
        } else if (resourceBundle.getString("category").equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
            panel.add(categoryJComboBox);
        } else if (resourceBundle.getString("creation Time").equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
            panel.add(jDateChooser);
            fieldForHours.setText("HH");
            fieldForMinutes.setText("MM");
            fieldForSeconds.setText("SS");
            panel.add(fieldForHours);
            panel.add(fieldForMinutes);
            panel.add(fieldForSeconds);
        } else {
            argumentJTextField = basicGUIElementsFabric.createBasicJTextField();
            panel.add(argumentJTextField);
        }
        JFrame frame = new JFrame();
        panel.add(submitArgumentJButton);
        frame.add(panel);
        frame.setSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
        return frame;
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
                String value;
                if (resourceBundle.getString("loyal").equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
                    value = findRightName(loyaJComboBox.getSelectedItem().toString());
                } else if (resourceBundle.getString("category").equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
                    value = findRightName(categoryJComboBox.getSelectedItem().toString());
                } else if (resourceBundle.getString("creation Time").equals(fieldForSOFJComboBox.getSelectedItem().toString())) {
                    Calendar calendar = jDateChooser.getCalendar();
                    try {
                        int hours = Integer.parseInt(fieldForHours.getText());
                        int minutes = Integer.parseInt(fieldForMinutes.getText());
                        int seconds = Integer.parseInt(fieldForSeconds.getText());
                        Calendar calendarResult = new GregorianCalendar();
                        calendarResult.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendarResult.get(Calendar.DAY_OF_MONTH), hours, minutes, seconds);
                        Date result = calendarResult.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        value = dateFormat.format(result);
                    } catch (NumberFormatException ee) {
                        printerror(resourceBundle.getString("NumberFormatException"));
                        return;
                    }
                } else {
                    value = argumentJTextField.getText();
                }
                try {
                    listForTable = FilterSpaceMarine.filterList(findRightName(fieldForSOFJComboBox.getSelectedItem().toString()), findRightName(typeOfFilterJComboBox.getSelectedItem().toString()), value, caeManager.getListFromServer());
                    setSettingsForTable();
                    frame.dispose();
                    guiManager.reloadMainScreen();
                } catch (ConnectionLostExeption e1) {
                    printerror(resourceBundle.getString("Error to connect to server"));
                    listForTable = new ArrayList<>();
                    setSettingsForTable();
                    frame.dispose();
                    guiManager.reloadMainScreen();
                } catch (NumberFormatException e2) {
                    printerror(resourceBundle.getString("Number format is wrong"));
                    listForTable = new ArrayList<>();
                    setSettingsForTable();
                    frame.dispose();
                    guiManager.reloadMainScreen();
                }
            }
        });
    }

    private void printerror(String error) {
        JLabel errorJLabel = basicGUIElementsFabric.createBasicLabel(error);
        errorJLabel = basicGUIElementsFabric.createBasicLabel(error);
        resultOfOperationJLabel.removeAll();
        resultOfOperationJLabel.add(errorJLabel);
        guiManager.reloadMainScreen();
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
}
