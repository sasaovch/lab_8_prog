package gui;

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

import util.Constants;

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
    private final SpaceMarine oldSpaceMarine;
    private BasicGUIElementsFabric basicGUIElementsFabric;
    public ChangeFieldsSpaceMarine(ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle, SpaceMarine spaceMarine) {
        this.resourceBundle = resourceBundle;
        this.caeManager = caeManager;
        this.oldSpaceMarine = spaceMarine;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        errorJLabe = basicGUIElementsFabric.createBasicLabel("");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setSize(new Dimension(Constants.SCREEN_WIDTH / PART_OF_SCREEN, Constants.SCREEN_HEIGHT / PART_OF_SCREEN));
        initElements();
    }

    protected void setListenerForSubmitButton() {
        submitButton.addActionListener(e -> {
                SpaceMarine spaceMarine = addJPanel.getSpaceMarine();
                if (Objects.isNull(spaceMarine)) {
                    printerror("Invalid arguments");
                } else {
                    spaceMarine.setID(oldSpaceMarine.getID());
                    CommandResult result = caeManager.executeCommand("update", spaceMarine, spaceMarine.getID());
                    southJPanel.add(basicGUIElementsFabric.createBasicLabel(result.getMessageResult()));
                    revalidate();
                    repaint();
                    dispose();
                }
        });
    }

    private void initElements() {
        southJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.NORTH_PANEL_HEIGHT));
        southJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, Constants.VGAP, Constants.HGAP));
        submitButton = basicGUIElementsFabric.createBasicButton("submit");

        centerJPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        addJPanel = new AddJPanel(resourceBundle);
        addJPanel.initTextFields(oldSpaceMarine);
        centerJPanel.add(addJPanel);
        southJPanel.add(submitButton);
        setListenerForSubmitButton();
        add(centerJPanel, BorderLayout.CENTER);
        add(southJPanel, BorderLayout.SOUTH);
    }

    private void printerror(String error) {
        errorJLabe.setText(resourceBundle.getString(error));
        southJPanel.add(errorJLabe);
        revalidate();
        repaint();
    }

    public void showJFrame() {
        setVisible(true);
    }
}
