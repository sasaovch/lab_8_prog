package gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.data.AstartesCategory;
import com.ut.common.data.Chapter;
import com.ut.common.data.Coordinates;
import com.ut.common.data.SpaceMarine;

import exeptions.IllegalValueException;
import util.ArgumentParser;
import util.Constants;
import util.SpaceMarineValidarot;
import util.StringConverter;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class AddJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int AMOUNT_OF_COLS = 3;
    private static final int AMOUNT_OF_ROWS = 12;
    private JPanel centralPanel = new JPanel();
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

    private JTextField nameField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField xField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField yField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField healthField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField heartCountField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField chapterField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField parentLegionField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField marinesCountField = BasicGUIElementsFabric.createBasicJTextField();
    private JTextField worldField = BasicGUIElementsFabric.createBasicJTextField();
    private JComboBox<String> loyaJComboBox;
    private JComboBox<String> categoryJComboBox;

    private int sizeHight = Constants.CENTER_PANEL_HEIGHT / AMOUNT_OF_COLS;
    private int sizeWight = Constants.SCREEN_WIDTH / AMOUNT_OF_ROWS;

    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;

    public AddJPanel(ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.caeManager = caeManager;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        drawPanel();
    }

    private void initElements() {
        centralPanel = new JPanel();

        loyaJComboBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{resourceBundle.getString("null"), resourceBundle.getString("true"), resourceBundle.getString("false")}, sizeWight, sizeHight);
        categoryJComboBox = BasicGUIElementsFabric.createBasicComboBox(new String[]{resourceBundle.getString("aggressor"), resourceBundle.getString("tactical"), resourceBundle.getString("inceptor"), resourceBundle.getString("helix")}, sizeWight, sizeHight);
        fieldText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Field"), sizeWight, sizeHight);
        valueText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Value"), sizeWight, sizeHight);
        nameText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("name"), sizeWight, sizeHight);
        xText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("x"), sizeWight, sizeHight);
        yText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("y"), sizeWight, sizeHight);
        healthText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("health"), sizeWight, sizeHight);
        heartCountText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("heartCount"), sizeWight, sizeHight);
        loyalText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("loyal"), sizeWight, sizeHight);
        categoryText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("category"), sizeWight, sizeHight);
        chapterText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("chapter"), sizeWight, sizeHight);
        parentLegionText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("parentLegion"), sizeWight, sizeHight);
        marinesCountText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("marinesCount"), sizeWight, sizeHight);
        worldText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("world"), sizeWight, sizeHight);
        requirementText = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Requirement"), sizeWight, sizeHight);
        nameReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Not empty"), sizeWight, sizeHight);
        xReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Any double"), sizeWight, sizeHight);
        yReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Not null"), sizeWight, sizeHight);
        healthReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Integer, >0"), sizeWight, sizeHight);
        heartCountReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Integer, 0<x<4"), sizeWight, sizeHight);
        chapterReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("May be null"), sizeWight, sizeHight);
        parentLegionReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Any string"), sizeWight, sizeHight);
        marinesCountReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Integer, 0<x<1001"), sizeWight, sizeHight);
        worldReq = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("Not empty"), sizeWight, sizeHight);
        submitButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("submit"), sizeWight, sizeHight);
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
        loyaJComboBox.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        categoryText.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
        categoryJComboBox.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 1));
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
        centralPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        centralPanel.setLayout(new GridLayout(AMOUNT_OF_ROWS, AMOUNT_OF_COLS));
        setBorders();

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        setListenerForSubmitButton();
    }

    private StringConverter<Boolean> createStringConverterBoolean() {
        return new StringConverter<Boolean>() {
            @Override
            public Boolean convert(String argument) {
                if (resourceBundle.getString("null").equals(argument)) {
                    return null;
                }
                if (resourceBundle.getString("true").equals(argument)) {
                    return true;
                }
                return false;
            }
        };
    }

    private SpaceMarine parseSpacemarineFromInput() throws IllegalArgumentException {
        try {
            final String name = argumentParser.parseArgFromString(nameField.getText(), SpaceMarineValidarot::nameValidator, s -> s);
            final double x = argumentParser.parseArgFromString(xField.getText(), (s) -> true, Double::parseDouble);
            final Long y = argumentParser.parseArgFromString(yField.getText(), SpaceMarineValidarot::yValidator, Long::parseLong);
            final Coordinates coordinates = new Coordinates(x, y);
            final Integer health = argumentParser.parseArgFromString(healthField.getText(), SpaceMarineValidarot::healthValidator, Integer::parseInt);
            final Integer heartCount = argumentParser.parseArgFromString(heartCountField.getText(), SpaceMarineValidarot::heartCountValidator, Integer::parseInt);
            final Boolean loyal = argumentParser.parseArgFromString(loyaJComboBox.getSelectedItem().toString(), (s) -> true, createStringConverterBoolean());
            String categoryEnglish = "";
            String bundleCategory = categoryJComboBox.getSelectedItem().toString();
            for (String key : resourceBundle.keySet()) {
                if (resourceBundle.getString(key).equals(bundleCategory)) {
                    categoryEnglish = key.toUpperCase();
                    break;
                }
            }
            AstartesCategory category = argumentParser.parseArgFromString(categoryEnglish, (s) -> true, AstartesCategory::valueOf);
            String chapter = argumentParser.parseArgFromString(chapterField.getText(), SpaceMarineValidarot::chapterValidator, s -> s);
            Chapter chapterSpMar;
            if ("".equals(chapter)) {
                chapterSpMar = null;
            } else {
                String parentLegion = argumentParser.parseArgFromString(parentLegionField.getText(), SpaceMarineValidarot::parentLegiolValidator, s -> s);
                Integer marinesCount = argumentParser.parseArgFromString(marinesCountField.getText(), SpaceMarineValidarot::marinesCountValidator, Integer::parseInt);
                String world = argumentParser.parseArgFromString(worldField.getText().toString(), SpaceMarineValidarot::worldValidator, s -> s);
                chapterSpMar = new Chapter(chapter, parentLegion, marinesCount, world);
            }
            return new SpaceMarine(name, coordinates, health, heartCount, loyal, category, chapterSpMar);
        } catch (IllegalValueException e) {
            return null;
        }
    }

    public SpaceMarine getSpaceMarine() {
        return parseSpacemarineFromInput();
    }

    private void setListenerForSubmitButton() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    SpaceMarine spMar = parseSpacemarineFromInput();
                    caeManager.executeCommand("add", null, spMar);
            }
        });
    }

    private void setElements() {
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
        centralPanel.add(loyaJComboBox);
        centralPanel.add(new JLabel());
        centralPanel.add(categoryText);
        centralPanel.add(categoryJComboBox);
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
        add(centralPanel);
    }

    private void drawPanel() {
        initElements();
        setSettingsForElements();
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setElements();
    }

    public void initTextFields(SpaceMarine spaceMarine) {
        nameField.setText(spaceMarine.getName());
        xField.setText(Double.toString(spaceMarine.getCoordinates().getX()));
        yField.setText(Long.toString(spaceMarine.getCoordinates().getY()));
        healthField.setText(Integer.toString(spaceMarine.getHealth()));
        heartCountField.setText(Integer.toString(spaceMarine.getHeartCount()));
        if (spaceMarine.getLoyal() == null) {
            loyaJComboBox.setSelectedItem(resourceBundle.getString("null"));
        } else {
            loyaJComboBox.setSelectedItem(spaceMarine.getLoyal().toString().toUpperCase());
        }
        categoryJComboBox.setSelectedItem(spaceMarine.getCategory().toString().toUpperCase());
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
}
