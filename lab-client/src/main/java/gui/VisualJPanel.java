package gui;

import java.util.ResourceBundle;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


import javax.swing.JFrame;
import javax.swing.JLabel;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;


import com.ut.client.ConnectionAndExecutorManager;

import util.Constants;
import util.CoordinatesGraphics;

import java.awt.Color;

public class VisualJPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> typeOfView;
    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);

    private JButton userJButton;
    private JButton reloadJButton;

    private JPanel northPanel;
    private JPanel centerPanel;

    private GUIManager guiManager;
    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;

    private CoordinatesGraphics coordinatesGraphics;

    public VisualJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setLayout(new BorderLayout());
        initElements();
    }

    public void initElements() {

        typeOfView = BasicGUIElementsFabric.createBasicComboBox(new String[]{resourceBundle.getString("Visual View"), resourceBundle.getString("Command Panel"), resourceBundle.getString("Table View")});

        userJButton = BasicGUIElementsFabric.createBasicButton(caeManager.getUsername());
        reloadJButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("reload"));

        centerPanel = new JPanel();
        northPanel = new JPanel();

        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setBackground(Constants.SUB_COLOR);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));

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

    public void setListenerForUserButton() {
        userJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame exitFrame = new JFrame();
                JPanel panel = new JPanel();
                JButton yesButton = new JButton(resourceBundle.getString("YES"));
                JButton noButton = new JButton(resourceBundle.getString("NO"));
                JLabel label = new JLabel(resourceBundle.getString("Do you want exit?"));
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
                panel.add(yesButton);
                panel.add(noButton);
                panel.add(label);
                exitFrame.add(panel);
                exitFrame.setSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
                exitFrame.setVisible(true);
            }
        });
    }

    public void setListenerForTypeOfViewBox() {
        typeOfView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resourceBundle.getString("Table View").equals(typeOfView.getSelectedItem().toString())) {
                    guiManager.showTablePanel(resourceBundle);
                } else if (resourceBundle.getString("Command Panel").equals(typeOfView.getSelectedItem().toString())) {
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
        northPanel.setBackground(Color.PINK);

        centerPanel.setBackground(Color.BLUE);
        centerPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));

        typeOfView.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));

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
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showVisualPanel(resourceBundle);
            }
        });
    }


    public void printGraphics() {
        coordinatesGraphics = new CoordinatesGraphics(caeManager, resourceBundle);
        centerPanel.removeAll();
        centerPanel.add(coordinatesGraphics);
        guiManager.reloadMainScreen();
    }
}
