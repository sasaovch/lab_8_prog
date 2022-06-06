package com.ut.gui;

import java.util.ResourceBundle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.Component;


import com.ut.client.ConnectionAndExecutorManager;
import com.ut.util.Constants;
import com.ut.util.ConstantsLanguage;
import com.ut.util.CoordinatesGraphics;


public class VisualJPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> typeOfView;
    private JComboBox<String> listToChooseLanguage;

    private JButton userJButton;
    private JButton reloadJButton;

    private JPanel northPanel;
    private JPanel centerPanel;

    private GUIManager guiManager;
    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;
    private BasicGUIElementsFabric basicGUIElementsFabric;

    private CoordinatesGraphics coordinatesGraphics;

    public VisualJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        setLayout(new BorderLayout());
        initElements();
    }

    private void initElements() {
        typeOfView = basicGUIElementsFabric.createBasicComboBox(new String[]{ConstantsLanguage.VISUAL_VIEW, ConstantsLanguage.COMMAND_PANEL, ConstantsLanguage.TABLE_VIEW});

        userJButton = new JButton();
        userJButton.setFont(Constants.MAIN_FONT);
        userJButton.setText((caeManager.getUsername()));
        userJButton.setBackground(Constants.SUB_COLOR);
        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        reloadJButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.RELOAD);

        centerPanel = new JPanel();
        northPanel = new JPanel();

        setSettingsForElements();
        setListenerForReloadButton();
        setListenerForTypeOfViewBox();
        setListenerForUserButton();
        setSettingForLanguagesList();
        addElementsToNorthPanel();
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        printGraphics();
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

    public void setListenerForTypeOfViewBox() {
        typeOfView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resourceBundle.getString(ConstantsLanguage.TABLE_VIEW).equals(typeOfView.getSelectedItem().toString())) {
                    guiManager.showTablePanel(resourceBundle);
                } else if (resourceBundle.getString(ConstantsLanguage.COMMAND_PANEL).equals(typeOfView.getSelectedItem().toString())) {
                    guiManager.showCommandPanel(resourceBundle);
                }
            }
        });
    }

    public void addElementsToNorthPanel() {
        northPanel.add(typeOfView);
        northPanel.add(reloadJButton);
        northPanel.add(listToChooseLanguage);
        northPanel.add(userJButton);
    }

    private void setSettingsForElements() {
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));

        centerPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        centerPanel.setLayout(new BorderLayout());

        typeOfView.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));

        userJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        reloadJButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        }

    private void setListenerForReloadButton() {
        reloadJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printGraphics();
            }
        });
    }

    private void setSettingForLanguagesList() {
        listToChooseLanguage = basicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setBackground(Constants.SUB_COLOR);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showVisualPanel(resourceBundle);
            }
        });
    }


    public void printGraphics() {
        coordinatesGraphics = new CoordinatesGraphics(guiManager, caeManager, resourceBundle);
        centerPanel.removeAll();
        centerPanel.add(coordinatesGraphics);
        guiManager.reloadMainScreen();
    }
}
