package gui;



import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.data.AstartesCategory;
import com.ut.common.data.Chapter;
import com.ut.common.data.Coordinates;
import com.ut.common.data.SpaceMarine;

import util.ArgumentParser;
import util.Constants;
import util.SpaceMarineValidarot;
import util.StringConverter;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class AddJPanel extends JPanel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static int AMOUNT_OF_PARTS = 10;
    private static int PARTS_TO_CENTER = 6;
    private static int PARTS_OF_SOUTH = 3;
    private static int AMOUNT_OF_COLS = 3;
    private static int AMOUNT_OF_ROWS = 12;
    private JFrame mainFrame;
    // private ConnectionManager caeManager;
    private JPanel northPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private JPanel errorPanel = new JPanel();
    private JButton submitButton;


    private ArgumentParser argumentParser = new ArgumentParser();


    private JLabel fieldText;
    private JLabel valueText;
    private JLabel requirementText;

    private JLabel nameText;
    private JLabel xText;
    private JLabel yText;
    private JLabel healthText;
    private JLabel heartCountText;
    private JLabel loyalText;
    private JLabel categoryText;
    private JLabel chapterText;
    private JLabel parentLegionText;
    private JLabel marinesCountText;
    private JLabel worldText;

    private JLabel nameReq;
    private JLabel xReq;
    private JLabel yReq;
    private JLabel healthReq;
    private JLabel heartCountReq;
    private JLabel chapterReq;
    private JLabel parentLegionReq;
    private JLabel marinesCountReq;
    private JLabel worldReq;

    private String[] loyal = Constants.LOYAL;
    private String[] category = Constants.CATEGORY;

    private JTextField nameField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField xField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField yField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField healthField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField heartCountField = BasicGUIElementsFabric.createBasicJTextField();
    private JComboBox<String> loyalBox = BasicGUIElementsFabric.createBasicComboBox(loyal);
    private JComboBox<String> categoryBox = BasicGUIElementsFabric.createBasicComboBox(category);
    private JTextField chapterField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField parentLegionField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField marinesCountField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField worldField = BasicGUIElementsFabric.createBasicJTextField();

    private ResourceBundle currentBundle;
    private ConnectionAndExecutorManager caeManager;
    // private AddPanelController addPanelController = new AddPanelController();

    public AddJPanel(ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.currentBundle = resourceBundle;
        this.caeManager = caeManager;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        drawPanel();
    }

    private void initElements() {
        // northPanel = new JPanel();
        centralPanel = new JPanel();
        // southPanel = new JPanel();
        // errorPanel = new JPanel();

        fieldText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("FIELD"));
        valueText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("value"));
        nameText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("name"));
        xText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("x"));
        yText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("y"));
        healthText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("health"));
        heartCountText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("heartCount"));
        loyalText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("loyal"));
        categoryText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("category"));
        chapterText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("chapter"));
        parentLegionText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("parent Legion"));
        marinesCountText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("marines Count"));
        worldText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("worldText"));
        requirementText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("REQUIREMENT"));
        nameReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("NOT Empty"));
        xReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Any double"));
        yReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Not null"));
        healthReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Integer, >0"));
        heartCountReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Integer, 0<x<4"));
        chapterReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("may be null"));
        parentLegionReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Any string"));
        marinesCountReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Integer, 0<x<1001"));
        worldReq = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("Not empty"));
        submitButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("SUBMIT"));
    }

    private void setBorders() {

        fieldText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        valueText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        requirementText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        marinesCountText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        requirementText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        nameText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        nameField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        nameReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        xText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        xField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        xReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        yText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        yField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        yReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        healthText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        healthField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        healthReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        heartCountText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        heartCountField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        heartCountReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        loyalText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        loyalBox.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        categoryText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        categoryBox.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        chapterText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        chapterField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        chapterReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        parentLegionText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        parentLegionField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        parentLegionReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        marinesCountText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        marinesCountReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        marinesCountField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        worldText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        worldReq.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        worldField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));

    }

    private void setSettingsForElements() {

        northPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / AMOUNT_OF_PARTS));
        northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        centralPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 7 * 6, Constants.SCREEN_HEIGHT * PARTS_TO_CENTER / AMOUNT_OF_PARTS));
        centralPanel.setLayout(new GridLayout(AMOUNT_OF_ROWS, AMOUNT_OF_COLS));

        // southPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT * PARTS_OF_SOUTH / AMOUNT_OF_PARTS * 2));
        // southPanel.setLayout(new GridBagLayout());

        errorPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT * PARTS_OF_SOUTH / AMOUNT_OF_PARTS * 2));
        errorPanel.setLayout(new GridBagLayout());

        setBorders();

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        setListenerForSubmitButton();

    }

    protected SpaceMarine parseSpacemarineFromInput() throws IllegalArgumentException {
        StringConverter<Boolean> stringConverterForLoyal = new StringConverter<Boolean>() {

            @Override
            public Boolean convert(String argument) {
                if ("".equals(argument.trim())) {
                    return null;
                }
                return Boolean.parseBoolean(argument);
            }
        };
        try {
            SpaceMarineValidarot spaceMarineValidator = new SpaceMarineValidarot();
            String name = argumentParser.parseArgFromString(nameField.getText(), spaceMarineValidator::nameValidator, s -> s);
            double x = argumentParser.parseArgFromString(xField.getText(), (s) -> true, Double::parseDouble);
            Long y = argumentParser.parseArgFromString(yField.getText(), spaceMarineValidator::yValidator, Long::parseLong);
            Coordinates coordinates = new Coordinates(x, y);
            Integer health = argumentParser.parseArgFromString(healthField.getText(), spaceMarineValidator::healthValidator, Integer::parseInt);
            Integer heartCount = argumentParser.parseArgFromString(heartCountField.getText(), spaceMarineValidator::heartCountValidator, Integer::parseInt);
            Boolean loyal = argumentParser.parseArgFromString(loyalBox.getSelectedItem().toString(), (s) -> true, stringConverterForLoyal);
            AstartesCategory category = argumentParser.parseArgFromString(categoryBox.getSelectedItem().toString().toUpperCase(), (s) -> true, AstartesCategory::valueOf);
            String chapter = argumentParser.parseArgFromString(chapterField.getText(), spaceMarineValidator::chapterValidator, s -> s);
            String parentLegion = argumentParser.parseArgFromString(parentLegionField.getText(), spaceMarineValidator::parentLegiolValidator, s -> s);
            Integer marinesCount = argumentParser.parseArgFromString(marinesCountField.getText(), spaceMarineValidator::marinesCountValidator, Integer::parseInt);
            String world = argumentParser.parseArgFromString(worldField.getText().toString(), spaceMarineValidator::worldValidator, s -> s);
            Chapter chapterSpMar = null;
            if (!("".equals(chapter) || chapter == null)) {
                chapterSpMar = new Chapter(chapter, parentLegion, marinesCount, world);
            }
            return new SpaceMarine(name, coordinates, health, heartCount, loyal, category, chapterSpMar);
        } catch (Exception e) {
            return null;
        }

    }

    protected void errorHandler() { //protected, чтобы можно было переопределить и сделать новые окна(updatePanel)
        errorPanel.removeAll();
        JLabel errorLabel = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("CHECK THE CURRENCY OF THE DATA"));
        errorPanel.add(errorLabel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public SpaceMarine getSpaceMarine() {
        return parseSpacemarineFromInput();
    }

    protected void setListenerForSubmitButton() { //protected, чтобы можно было переопределить и сделать новые окна(updatePanel)
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    SpaceMarine spMar = parseSpacemarineFromInput();
                    caeManager.executeCommand("add", null, spMar);
        }});
    }

    private void setElements() {
        //
        centralPanel.add(fieldText);
        centralPanel.add(valueText);
        centralPanel.add(requirementText);
        centralPanel.add(nameText);
        centralPanel.add(nameField);
        centralPanel.add(nameReq);
        centralPanel.add(xText);
        centralPanel.add(xField);
        centralPanel.add(xReq);
        centralPanel.add(yText);
        centralPanel.add(yField);
        centralPanel.add(yReq);
        centralPanel.add(healthText);
        centralPanel.add(healthField);
        centralPanel.add(healthReq);
        centralPanel.add(heartCountText);
        centralPanel.add(heartCountField);
        centralPanel.add(heartCountReq);
        centralPanel.add(loyalText);
        centralPanel.add(loyalBox);
        centralPanel.add(new JLabel());
        centralPanel.add(categoryText);
        centralPanel.add(categoryBox);
        centralPanel.add(new JLabel());
        centralPanel.add(chapterText);
        centralPanel.add(chapterField);
        centralPanel.add(chapterReq);
        centralPanel.add(parentLegionText);
        centralPanel.add(parentLegionField);
        centralPanel.add(parentLegionReq);
        centralPanel.add(marinesCountText);
        centralPanel.add(marinesCountField);
        centralPanel.add(marinesCountReq);
        centralPanel.add(worldText);
        centralPanel.add(worldField);
        centralPanel.add(worldReq);
        // add(northPanel);
        add(centralPanel);
        // add(southPanel);
        // add(errorPanel);
    }

    public void drawPanel() {
        initElements();
        setSettingsForElements();
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 7 * 6, Constants.SCREEN_HEIGHT));
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setElements();
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    // public ConnectionManager getConnectionManager() {
    //     return caeManager;
    // }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public ResourceBundle getCurrentBundle() {
        return currentBundle;
    }

    public void initTextFields(SpaceMarine spaceMarine) {
        nameField.setText(spaceMarine.getName());
        xField.setText(Double.toString(spaceMarine.getCoordinates().getX()));
        yField.setText(Long.toString(spaceMarine.getCoordinates().getY()));
        healthField.setText(Integer.toString(spaceMarine.getHealth()));
        heartCountField.setText(Integer.toString(spaceMarine.getHeartCount()));
        if (spaceMarine.getLoyal() == null) {
            loyalBox.setSelectedItem("NULL");
        } else {
            loyalBox.setSelectedItem(spaceMarine.getLoyal().toString().toUpperCase());
        }
        categoryBox.setSelectedItem(spaceMarine.getCategory().toString().toUpperCase());
        if (spaceMarine.getChapter() == null) {
            chapterField.setText("");
            parentLegionField.setText("");
            marinesCountText.setText("");
            worldField.setText("");
        } else {
            chapterField.setText(spaceMarine.getChapter().getName());
            parentLegionField.setText(spaceMarine.getChapter().getParentLegion());
            marinesCountField.setText(Long.toString(spaceMarine.getChapter().getMarinesCount()));
            worldField.setText(spaceMarine.getChapter().getWorld());
        }
    }

    // public void addDeleteButton(int id) {
    //     JButton deleteButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Delete"));
    //     southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    //     southPanel.add(deleteButton);

    //     deleteButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             addPanelController.deleteAndClose(caeManager, mainFrame, currentBundle, id);
    //         }
    //     });
    // }
}
