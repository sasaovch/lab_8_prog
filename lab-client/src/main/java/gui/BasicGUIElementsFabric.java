package gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import util.Constants;

public final class BasicGUIElementsFabric {

    private BasicGUIElementsFabric() {
        throw new Error();
    }
    public static JLabel createBasicLabel(String text) {
        JLabel basicLabel = new JLabel(text);
        basicLabel.setFont(Constants.MAIN_FONT);
        basicLabel.setBackground(Constants.SUB_COLOR);
        basicLabel.setPreferredSize(new Dimension(Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        basicLabel.setOpaque(false);
        return basicLabel;
    }

    public static JTextField createBasicJTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jTextField.setPreferredSize(new Dimension(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HIGHT));
        return jTextField;
    }

    public static JComboBox<String> createBasicComboBox(String[] elements) {
        JComboBox<String> jComboBox = new JComboBox<>(elements);
        jComboBox.setFont(Constants.MAIN_FONT);
        jComboBox.setBackground(Constants.SUB_COLOR);
        jComboBox.setPreferredSize(new Dimension(Constants.COMBOX_WIDTH, Constants.COMBOX_HIGHT));
        return jComboBox;
    }
    public static JButton createBasicButton(String text) {
        JButton jButton = new JButton();
        jButton.setFont(Constants.MAIN_FONT);
        jButton.setText(text);
        jButton.setBackground(Constants.SUB_COLOR);
        jButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        return jButton;
    }

    public static JPasswordField createBasicJPasswordFiled() {
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setFont(Constants.MAIN_FONT);
        jPasswordField.setBackground(Constants.SUB_COLOR);
        jPasswordField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jPasswordField.setPreferredSize(new Dimension(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HIGHT));
        return jPasswordField;
    }

    public static JLabel createBasicLabel(String text, int width, int height) {
        JLabel basicLabel = new JLabel(text);
        basicLabel.setFont(Constants.MAIN_FONT);
        basicLabel.setBackground(Constants.SUB_COLOR);
        basicLabel.setPreferredSize(new Dimension(width, height));
        basicLabel.setOpaque(false);
        return basicLabel;
    }

    public static JTextField createBasicJTextField(int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jTextField.setPreferredSize(new Dimension(width, height));
        return jTextField;
    }

    public static JComboBox<String> createBasicComboBox(String[] elements, int width, int height) {
        JComboBox<String> jComboBox = new JComboBox<>(elements);
        jComboBox.setFont(Constants.MAIN_FONT);
        jComboBox.setBackground(Constants.SUB_COLOR);
        jComboBox.setPreferredSize(new Dimension(width, height));
        return jComboBox;
    }
    public static JButton createBasicButton(String text, int width, int height) {
        JButton jButton = new JButton();
        jButton.setFont(Constants.MAIN_FONT);
        jButton.setText(text);
        jButton.setBackground(Constants.SUB_COLOR);
        jButton.setPreferredSize(new Dimension(width, height));
        return jButton;
    }

    public static JPasswordField createBasicJPasswordFiled(int width, int height) {
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setFont(Constants.MAIN_FONT);
        jPasswordField.setBackground(Constants.SUB_COLOR);
        jPasswordField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        jPasswordField.setPreferredSize(new Dimension(width, height));
        return jPasswordField;
    }
}
