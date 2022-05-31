package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
        basicLabel.setOpaque(false);
        return basicLabel;
    }

    public static JTextField createBasicJTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        return jTextField;
    }
    public static JTextField createBasicJTextField(int columns) {
        JTextField jTextField = new JTextField(columns);
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setBackground(Constants.SUB_COLOR);
        jTextField.setBorder(new LineBorder(Constants.MAIN_COLOR, 1));
        return jTextField;
    }
    public static JComboBox<String> createBasicComboBox(String[] elements) {
        JComboBox<String> jComboBox = new JComboBox<>(elements);
        jComboBox.setFont(Constants.MAIN_FONT);
        jComboBox.setBackground(Constants.SUB_COLOR);
        return jComboBox;
    }
    public static JButton createBasicButton(String text) {
        JButton jButton = new JButton();
        jButton.setFont(Constants.MAIN_FONT);
        jButton.setText(text);
        jButton.setBackground(Constants.SUB_COLOR);
        return jButton;
    }
}