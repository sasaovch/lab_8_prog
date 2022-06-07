package com.ut.gui;

import java.util.Objects;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;
import com.ut.util.Constants;
import com.ut.util.ConstantsLanguage;

public class ChangeFieldsSpaceMarine extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int PART_OF_SCREEN = 4;
    private AddJPanel addJPanel;
    private JPanel centerJPanel = new JPanel();
    private JPanel southJPanel = new JPanel();
    private JLabel errorJLabe;
    private JButton submitButton;
    private final ConnectionAndExecutorManager caeManager;
    private final ResourceBundle resourceBundle;
    private SpaceMarine oldSpaceMarine;
    private BasicGUIElementsFabric basicGUIElementsFabric;
    private GUIManager guiManager;
    private boolean needToUpdateTable;
    public ChangeFieldsSpaceMarine(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle, boolean needToUpdateTable) {
        this.resourceBundle = resourceBundle;
        this.caeManager = caeManager;
        this.guiManager = guiManager;
        this.needToUpdateTable = needToUpdateTable;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        errorJLabe = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.EMPTY_STRING);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setSize(new Dimension(Constants.SCREEN_WIDTH / PART_OF_SCREEN, Constants.SCREEN_HEIGHT / PART_OF_SCREEN));
        initElements();
    }

    protected void setListenerForSubmitButton() {
        submitButton.addActionListener(e -> {
                SpaceMarine spaceMarine = addJPanel.getSpaceMarine();
                if (Objects.isNull(spaceMarine)) {
                    printerror(ConstantsLanguage.INVALID_ARGUMENTS);
                } else {
                    spaceMarine.setID(oldSpaceMarine.getID());
                    handleResult(caeManager.executeCommand(ConstantsLanguage.UPDATE_COMMAND, spaceMarine, spaceMarine.getID()));
                }
        });
    }

    private void handleResult(CommandResult result) {
        if (result.getResultStatus()) {
            if (needToUpdateTable) {
                guiManager.addRowToTable((SpaceMarine) result.getData());
            }
            dispose();
        } else {
            printerror(result.getMessageResult());
            revalidate();
            repaint();
        }
    }

    private void initElements() {
        southJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        southJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, Constants.VGAP, Constants.HGAP));
        submitButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.SUBMIT);

        centerJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        addJPanel = new AddJPanel(resourceBundle);
        centerJPanel.add(addJPanel);
        southJPanel.add(submitButton);
        add(centerJPanel, BorderLayout.CENTER);
        add(southJPanel, BorderLayout.SOUTH);
    }

    private void printerror(String error) {
        errorJLabe.setText(resourceBundle.getString(error));
        southJPanel.add(errorJLabe);
        revalidate();
        repaint();
    }

    public void showJFrame(SpaceMarine editSpaceMarine) {
        this.oldSpaceMarine = editSpaceMarine;
        addJPanel.initTextFields(oldSpaceMarine);
        setListenerForSubmitButton();
        setVisible(true);
    }
}
