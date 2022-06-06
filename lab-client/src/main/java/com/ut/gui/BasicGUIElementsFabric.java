package com.ut.gui;

import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.ut.util.Constants;

public class BasicGUIElementsFabric {
    private ResourceBundle resourceBundle;

    public BasicGUIElementsFabric(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public JLabel createBasicLabel(String text) {
        JLabel basicLabel = new JLabel(resourceBundle.getString(text));
        basicLabel.setFont(Constants.MAIN_FONT);
        basicLabel.setBackground(Constants.SUB_COLOR);
        basicLabel.setHorizontalAlignment(JLabel.CENTER);
        basicLabel.setPreferredSize(new Dimension(Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        basicLabel.setOpaque(false);
        return basicLabel;
    }

    public JTextField createBasicJTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jTextField.setPreferredSize(new Dimension(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HIGHT));
        return jTextField;
    }

    public JTextField createBasicJTextField(String text) {
        JTextField jTextField = new JTextField();
        jTextField.setText(text);
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jTextField.setPreferredSize(new Dimension(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HIGHT));
        return jTextField;
    }

    public JComboBox<String> createBasicComboBox(String[] elements) {
        String[] elementsLocal = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            elementsLocal[i] = resourceBundle.getString(elements[i]);
        }
        JComboBox<String> jComboBox = new JComboBox<>(elementsLocal);
        jComboBox.setFont(Constants.MAIN_FONT);
        jComboBox.setBackground(Constants.SUB_COLOR);
        jComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        return jComboBox;
    }
    public JButton createBasicButton(String text) {
        JButton jButton = new JButton();
        jButton.setFont(Constants.MAIN_FONT);
        jButton.setText(resourceBundle.getString(text));
        jButton.setBackground(Constants.SUB_COLOR);
        jButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        return jButton;
    }

    public JPasswordField createBasicJPasswordFiled() {
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setFont(Constants.MAIN_FONT);
        jPasswordField.setBackground(Constants.SUB_COLOR);
        jPasswordField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jPasswordField.setPreferredSize(new Dimension(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HIGHT));
        return jPasswordField;
    }

    public JLabel createBasicLabel(String text, int wigth, int height) {
        JLabel basicLabel = new JLabel(resourceBundle.getString(text));
        basicLabel.setFont(Constants.MAIN_FONT);
        basicLabel.setBackground(Constants.SUB_COLOR);
        basicLabel.setPreferredSize(new Dimension(wigth, height));
        basicLabel.setOpaque(false);
        return basicLabel;
    }

    public JTextField createBasicJTextField(int wigth, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jTextField.setPreferredSize(new Dimension(wigth, height));
        return jTextField;
    }

    public JComboBox<String> createBasicComboBox(String[] elements, int wigth, int height) {
        String[] elementsLocal = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            elementsLocal[i] = resourceBundle.getString(elements[i]);
        }
        JComboBox<String> jComboBox = new JComboBox<>(elementsLocal);
        jComboBox.setFont(Constants.MAIN_FONT);
        jComboBox.setBackground(Constants.SUB_COLOR);
        jComboBox.setPreferredSize(new Dimension(wigth, height));
        return jComboBox;
    }
    public JButton createBasicButton(String text, int wigth, int height) {
        JButton jButton = new JButton();
        jButton.setFont(Constants.MAIN_FONT);
        jButton.setText(resourceBundle.getString(text));
        jButton.setBackground(Constants.SUB_COLOR);
        jButton.setPreferredSize(new Dimension(wigth, height));
        return jButton;
    }

    public JPasswordField createBasicJPasswordFiled(int wigth, int height) {
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setFont(Constants.MAIN_FONT);
        jPasswordField.setBackground(Constants.SUB_COLOR);
        jPasswordField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jPasswordField.setPreferredSize(new Dimension(wigth, height));
        return jPasswordField;
    }
}
