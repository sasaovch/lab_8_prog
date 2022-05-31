package gui;

import java.util.ResourceBundle;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;


import com.ut.client.ConnectionAndExecutorManager;

import lombok.extern.slf4j.Slf4j;
import util.Constants;
import util.CoordinatesDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

@Slf4j
public class VisualJPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> typeOfView;
    private JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);

    private JButton userButton;
    private JButton reloadButton;
    
    private JPanel northPanel;
    private JPanel centerPanel;

    private GUIManager guiManager;
    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;

    private CoordinatesDemo coordinatesDemo;

    public VisualJPanel(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setLayout(new BorderLayout());
        initElements();
    }
    
    public void initElements() {

        typeOfView = BasicGUIElementsFabric.createBasicComboBox(new String[]{"Visual View", "Table View",  "Command Panel"});

        userButton = BasicGUIElementsFabric.createBasicButton("username");
        reloadButton = BasicGUIElementsFabric.createBasicButton("reload");
    
        centerPanel = new JPanel();
        northPanel = new JPanel();
        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setBackground(Constants.SUB_COLOR);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(resourceBundle));
    
        setListenerForReloadButton();
        setListenerForTypeOfViewBox();
        setListenerForUserButton();
        setSettingsForElements();
        setSettingForLanguagesList();
        addElementsToNorthPanel();
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        printGraphics();
    }

    public void setListenerForUserButton() {
        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                log.info("In userButton action");
                JFrame exitFrame = new JFrame();
                JPanel panel = new JPanel();
                JButton yesButton = new JButton("yes");
                JButton noButton = new JButton("no");
                JLabel label = new JLabel("Do you want exit?");
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
                exitFrame.setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
                exitFrame.setVisible(true);
            }
        });
    }

    public void setListenerForTypeOfViewBox() {
        typeOfView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Table View".equals(typeOfView.getSelectedItem().toString())) {
                    log.info("Choosed Table View");
                    guiManager.showTablePanel(resourceBundle);
                } else if ("Command Panel".equals(typeOfView.getSelectedItem().toString())) {
                    log.info("Choosed Command Panel");
                    guiManager.showCommandPanel(resourceBundle);
                }
            }
        });
    }

    public void addElementsToNorthPanel() {
        northPanel.add(typeOfView);
        northPanel.add(reloadButton);
        northPanel.add(listToChooseLanguage);
        northPanel.add(userButton);
    }

    private void setSettingsForElements() {
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Constants.HGAP, Constants.VGAP));
        northPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        northPanel.setBackground(Color.PINK);

        centerPanel.setBackground(Color.BLUE);
        // centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        centerPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));

        typeOfView.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        listToChooseLanguage.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));

        userButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        reloadButton.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        }

    private void setListenerForReloadButton() {
        reloadButton.addActionListener(new ActionListener() {
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
                log.info("Change language");
                resourceBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                guiManager.showVisualPanel(resourceBundle);
            }
        });
    }


    public void printGraphics() {
        coordinatesDemo = new CoordinatesDemo(this, guiManager, caeManager);
        centerPanel.removeAll();
        setSettingForLanguagesList();
        centerPanel.add(coordinatesDemo);
        coordinatesDemo.startTimer();
        guiManager.reloadMainScreen();
    }
}
