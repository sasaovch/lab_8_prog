package gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;

import lombok.extern.slf4j.Slf4j;
import util.Constants;
import util.ParsList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.awt.Frame;

@Slf4j
public class MainJFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel commandPanel;
    private JPanel connectPanel;

    private TableViewJPanel tablePanel;
    private VisualJPanel vusialJPanel;
    private String[][] listElements;

    private JComboBox<String> languages = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
    private JButton submitButton;
    private String[] tableHeader = new String[] {"id", "name", "creation Time", "coordinate X", "coordinate Y", "health", "heart count", "loyal", "category", "chapter", "parent Legion", "marines count", "world", "owner name"};
    private ConnectionAndExecutorManager caeManager = new ConnectionAndExecutorManager();
    private GUIManager guiManager = new GUIManager(this);
    private ResourceBundle resourceBundle;

    private MainJFrame() {
        resourceBundle = Constants.getBundleFromLanguageName(languages.getSelectedItem().toString());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        ConnectJPanel connectJPanel = new ConnectJPanel(guiManager, caeManager, resourceBundle);
        add(connectJPanel);
        setVisible(true);
    }

    public void initElements() {
        tableHeader = new String[]{resourceBundle.getString("id"), resourceBundle.getString("name"), resourceBundle.getString("x"), resourceBundle.getString("y"), resourceBundle.getString("creationDate"), resourceBundle.getString("age"), resourceBundle.getString("wingspan"), resourceBundle.getString("color"), resourceBundle.getString("type"), resourceBundle.getString("depth"), resourceBundle.getString("Number ot Treasures"), resourceBundle.getString("owner_id")};
        
        commandPanel = new JPanel();

        submitButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("SUBMIT"));
        languages = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
        resourceBundle = Constants.getBundleFromLanguageName(languages.getSelectedItem().toString());
    }

    public JTextField createCenteredJField() {
        JTextField jTextField = new JTextField(SwingConstants.CENTER);
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH * 8 / 10, Constants.SCREEN_HEIGHT / 20));
        jTextField.setMinimumSize(new Dimension(Constants.SCREEN_WIDTH * 8 / 10, Constants.SCREEN_HEIGHT / 20));
        jTextField.setMaximumSize(new Dimension(Constants.SCREEN_WIDTH * 8 / 10, Constants.SCREEN_HEIGHT / 20));
        jTextField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, 3));
        return jTextField;
    }


    public void createJPanelWithLayout(JPanel mainPanel, int amountOfPanels) {

        languageListPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        addressTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        addressTextFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        portTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));

        portTextFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        submitButtonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        errorFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        secondButtonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));

        mainPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        languageListPanel.setBackground(Color.PINK);
        addressTextFieldPanel.setBackground(Color.GREEN);
        addressTextPanel.setBackground(Color.BLUE);
        portTextFieldPanel.setBackground(Color.BLACK);
        portTextPanel.setBackground(Color.CYAN);
        submitButtonPanel.setBackground(Color.MAGENTA);
        errorFieldPanel.setBackground(Color.ORANGE);
        mainPanel.add(languageListPanel);
        mainPanel.add(addressTextPanel);
        mainPanel.add(addressTextFieldPanel);
        mainPanel.add(portTextPanel);
        mainPanel.add(portTextFieldPanel);
        mainPanel.add(submitButtonPanel);
        mainPanel.add(errorFieldPanel);

        JLabel addressText = new JLabel(resourceBundle.getString("ADDRESS"), SwingConstants.CENTER);
        addressText.setFont(Constants.MAIN_FONT);
        addressTextPanel.add(addressText, BorderLayout.CENTER);
        //TextField for getting address
        JTextField fieldForAddress = createCenteredJField();
        addressTextFieldPanel.add(fieldForAddress);
        //host port text
        JLabel portText = new JLabel(resourceBundle.getString("PORT"), SwingConstants.CENTER);
        portText.setFont(Constants.MAIN_FONT);
        portTextPanel.add(portText, BorderLayout.CENTER);
        //Textfield for getting port
        JTextField fieldForPort = createCenteredJField();
        portTextFieldPanel.add(fieldForPort);
        //Submit button
        submitButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("SUBMIT"));
        submitButton.setFont(Constants.MAIN_FONT);
        submitButtonPanel.add(submitButton);
        add(mainPanel);
    }

    public static void main(String[] args) throws Exception {
        MainJFrame mainFrame = new MainJFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainFrame.setSize(new Dimension(Constants.SCREEN_WIDTH / 8 * 7, Constants.SCREEN_HEIGHT));
        mainFrame.setVisible(true);
    }

    public void showLoginPanel(ResourceBundle resourceBundle) {
        log.info("Show log.info()");
        this.resourceBundle = resourceBundle;
        LoginJPanel loginPanel = new LoginJPanel(guiManager, caeManager, this.resourceBundle);
        getContentPane().removeAll();
        setContentPane(loginPanel);
        revalidate();
        repaint();
    }

    public void showTablePanel(ResourceBundle resourceBundle) {
        log.info("Show main window");
        this.resourceBundle = resourceBundle;
        CommandResult result = caeManager.executeCommand("show", null, null);
        log.info(result.getResultStatus().toString());
        if (result.getResultStatus()) {
            Locale locale = Locale.ENGLISH;
            tablePanel = new TableViewJPanel(guiManager, this.resourceBundle, caeManager, tableHeader, (List<SpaceMarine>) result.getData(), locale);
            getContentPane().removeAll();
            setContentPane(tablePanel);
            revalidate();
            repaint();
        }
    }

    public void showVisualPanel(ResourceBundle resourceBundle) {
        log.info("Show visual window");
        vusialJPanel = new VisualJPanel(guiManager, caeManager, this.resourceBundle);
        getContentPane().removeAll();
        setContentPane(vusialJPanel);
        revalidate();
        repaint();
    }

    public void showCommandPanel(ResourceBundle resourceBundle) {
        log.info("Show command panel");
        commandPanel = new CommandModeJPanel(guiManager, caeManager, this.resourceBundle);
        getContentPane().removeAll();
        setContentPane(commandPanel);
        revalidate();
        repaint();
    }

    public void showConnectPanel(ResourceBundle resourceBundle) {
        log.info("Show connect panel");
        this.resourceBundle = resourceBundle;
        connectPanel = new ConnectJPanel(guiManager, caeManager, resourceBundle);        
        getContentPane().removeAll();
        setContentPane(connectPanel);
        revalidate();
        repaint();
    }

    private final JPanel languageListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Constants.SCREEN_WIDTH * 8 / 10, Constants.SCREEN_HEIGHT / 20));
    private final JPanel addressTextPanel = new JPanel(new BorderLayout());
    private final JPanel addressTextFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel portTextFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel portTextPanel = new JPanel(new BorderLayout());
    private final JPanel submitButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel errorFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel secondButtonPanel = new JPanel(new GridBagLayout()); //can be not in use, if it not in use than we don't count it
}
