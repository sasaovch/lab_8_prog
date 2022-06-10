package com.ut.gui;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ut.common.data.AstartesCategory;
import com.ut.common.data.Chapter;
import com.ut.common.data.Coordinates;
import com.ut.common.data.SpaceMarine;
import com.ut.exeptions.IllegalValueException;
import com.ut.util.ArgumentParser;
import com.ut.util.Constants;
import com.ut.util.ConstantsLanguage;
import com.ut.util.SpaceMarineValidarot;
import com.ut.util.StringConverter;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ResourceBundle;

public class AddJPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int AMOUNT_OF_COLS = 3;
    private static final int AMOUNT_OF_ROWS = 12;
    private static final int FACTOR_FOR_ROWS = 10;
    private JPanel centralPanel = new JPanel();

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

    private JTextField nameField;
    private JTextField xField;
    private JTextField yField;
    private JTextField healthField;
    private JTextField heartCountField;
    private JTextField chapterField;
    private JTextField parentLegionField;
    private JTextField marinesCountField;
    private JTextField worldField;
    private JComboBox<String> loyaJComboBox;
    private JComboBox<String> categoryJComboBox;

    private ResourceBundle resourceBundle;
    private BasicGUIElementsFabric basicGUIElementsFabric;

    public AddJPanel(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        initElements();
        setSettingsForElements();
        setElements();
    }

    private void initElements() {
        centralPanel = new JPanel();
        nameField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        xField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        yField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        healthField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        heartCountField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        chapterField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        parentLegionField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        marinesCountField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        worldField = basicGUIElementsFabric.createBasicJTextField(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);

        loyaJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.TRUE, ConstantsLanguage.FALSE, ConstantsLanguage.NULL});
        categoryJComboBox = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.AGGRESSOR, ConstantsLanguage.HELIX, ConstantsLanguage.TACTICAL, ConstantsLanguage.INCEPTOR});
        fieldText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.FIELD, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        valueText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.VALUE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        nameText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.NAME, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        xText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.X, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        yText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.Y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        healthText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.HEALTH, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        heartCountText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.HEART_COUNT, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        loyalText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.LOYAL, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        categoryText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.CATEGORY, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        chapterText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.CHAPTER, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        parentLegionText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.PARENT_LEGION, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        marinesCountText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.MARINES_COUNT, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        worldText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.WORLD, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        requirementText = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.REQUIEMENT, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        nameReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.NOT_EMPTY, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        xReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.ANY_DOUBLE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        yReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.NOT_NULL, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        healthReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.INTEGER_GREATER_ZERO, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        heartCountReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.INTEGER_LOWER_FOUR, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        chapterReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.MAY_BE_NULL, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        parentLegionReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.ANY_STRING, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        marinesCountReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.INTEGER_LOWER_THOUSAND, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
        worldReq = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.NOT_EMPTY, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT * 2);
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
        centralPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT / AMOUNT_OF_ROWS * FACTOR_FOR_ROWS));
        centralPanel.setLayout(new GridLayout(AMOUNT_OF_ROWS, AMOUNT_OF_COLS));
        setBorders();
    }

    private StringConverter<Boolean> createStringConverterBoolean() {
        return new StringConverter<Boolean>() {
            @Override
            public Boolean convert(String argument) {
                if (resourceBundle.getString(ConstantsLanguage.NULL).equals(argument)) {
                    return null;
                }
                if (resourceBundle.getString(ConstantsLanguage.TRUE).equals(argument)) {
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
            String categoryEnglish = ConstantsLanguage.EMPTY_STRING;
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
            if (ConstantsLanguage.EMPTY_STRING.equals(chapter)) {
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

    public void initTextFields(SpaceMarine spaceMarine) {
        nameField.setText(spaceMarine.getName());
        xField.setText(Double.toString(spaceMarine.getCoordinates().getX()));
        yField.setText(Long.toString(spaceMarine.getCoordinates().getY()));
        healthField.setText(Integer.toString(spaceMarine.getHealth()));
        heartCountField.setText(Integer.toString(spaceMarine.getHeartCount()));
        if (spaceMarine.getLoyal() == null) {
            loyaJComboBox.setSelectedItem(resourceBundle.getString(ConstantsLanguage.NULL));
        } else if (spaceMarine.getLoyal().equals(true)) {
            loyaJComboBox.setSelectedItem(resourceBundle.getString(ConstantsLanguage.TRUE));
        } else {
            loyaJComboBox.setSelectedItem(resourceBundle.getString(ConstantsLanguage.FALSE));
        }
        categoryJComboBox.setSelectedItem(resourceBundle.getString(spaceMarine.getCategory().toString().toLowerCase()));
        if (spaceMarine.getChapter() == null) {
            chapterField.setText(ConstantsLanguage.EMPTY_STRING);
            parentLegionField.setText(ConstantsLanguage.EMPTY_STRING);
            marinesCountField.setText(ConstantsLanguage.EMPTY_STRING);
            worldField.setText(ConstantsLanguage.EMPTY_STRING);
        } else {
            chapterField.setText(spaceMarine.getChapter().getName());
            parentLegionField.setText(spaceMarine.getChapter().getParentLegion());
            marinesCountField.setText(Long.toString(spaceMarine.getChapter().getMarinesCount()));
            worldField.setText(spaceMarine.getChapter().getWorld());
        }
    }
}
