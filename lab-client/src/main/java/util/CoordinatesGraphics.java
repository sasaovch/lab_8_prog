package util;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.data.SpaceMarine;

import exeptions.ConnectionLostExeption;
import gui.BasicGUIElementsFabric;
import gui.ChangeFieldsSpaceMarine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class CoordinatesGraphics extends JComponent implements MouseListener, ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int MAX_ALPHA = 255;
    private static final int COUNTER_MAX = 30;
    private static final int AMOUNT_OF_PARTS = 10;
    private static final int MAX_COLOR_VALUE = 0xFFFFFF;
    private static final int BASIC_STROKE = 4;
    private static final int AMOUNT_OF_PART_TO_CENTER_HALF = 4;
    private static final int Y_FIRST_POINT_NUMERATOR = 3;
    private static final double HITBOX_LOW_POINT = 6 / 5.0;
    private static final int LINE_HIGHT = Constants.SCREEN_HEIGHT / 3;
    private static final int TIME_DELAY = 15;
    private static final int MIN_SIZE = 10;

    private HashMap<String, Color> usersAndColors = new HashMap<>();
    private Set<Color> colors = new HashSet<>();

    private List<SpaceMarine> currentList;
    private List<SpaceMarine> newList;
    private List<SpaceMarine> showedList = new ArrayList<>();
    private List<RemovingSpaceMarine> removeSpaceMarinesLIst = new ArrayList<>();
    private List<MovingSpaceMarine> moveSpaceMarineList = new ArrayList<>();
    private List<ShowSpaceMarine> showSpaceMarineList = new ArrayList<>();
    private Set<Long> idSet = new HashSet<>();

    private final int updateDelayConst = 300;
    private int updateDelay = updateDelayConst;
    private Timer timer = new Timer(TIME_DELAY, this);

    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;

    private int maxHealth = 0;
    private double maxX = 0;
    private long maxY = 0;
    private int minHealth = 0;
    private double minX = 0;
    private long minY = 0;
    private double factor = 0;
    private final int differenceSize = 200;

    public CoordinatesGraphics(ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.caeManager = caeManager;
        this.resourceBundle = resourceBundle;
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        addMouseListener(this);
        startTimer();
        updateList();
        currentList = new ArrayList<>();
        for (SpaceMarine spaceMarine : newList) {
            compareMaxVariables(spaceMarine.getHealth(), spaceMarine.getCoordinates().getX(), spaceMarine.getCoordinates().getY());
            factor = differenceSize / (maxHealth - minHealth);
        }
    }
    public void printerror(String error) {
        JFrame frame = new JFrame();
        JButton okButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("OK"));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
            }
        });
        JLabel errorLabel = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString(error));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(errorLabel);
        frame.add(okButton);
        frame.setPreferredSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
        frame.setVisible(true);
    }

    public void compareMaxVariables(int health, double x, long y) {
        if (maxHealth < health) {
            maxHealth = health;
        }
        if (minHealth > health) {
            minHealth = health;
        }
        if (maxX < x) {
            maxX = x;
        }
        if (minX > x) {
            minX = x;
        }
        if (maxY < y) {
            maxY = y;
        }
        if (minY > y) {
            minY = y;
        }
    }

    public void updateList() {
        try {
            newList = caeManager.getListFromServer();
        } catch (ConnectionLostExeption e) {
            printerror("Connection lost");
            newList = new ArrayList<>();
        }
    }

    public void checkUpdates() {
        updateList();
        for (SpaceMarine oldSpaceMarine : currentList) {
            boolean needsToRemove = true;
            for (SpaceMarine spaceMarine : newList) {
                compareMaxVariables(spaceMarine.getHealth(), spaceMarine.getCoordinates().getX(), spaceMarine.getCoordinates().getY());
                factor = differenceSize / (maxHealth - minHealth);
                if (oldSpaceMarine.getID().equals(spaceMarine.getID())) {
                    needsToRemove = false;
                    if (oldSpaceMarine.getCoordinates().equals(spaceMarine.getCoordinates())
                            && oldSpaceMarine.getCoordinates().getX() == spaceMarine.getCoordinates().getX()
                            && oldSpaceMarine.getCoordinates().getY().equals(spaceMarine.getCoordinates().getY())) {
                        oldSpaceMarine = spaceMarine;
                    } else {
                        moveSpaceMarineList.add(new MovingSpaceMarine((int) oldSpaceMarine.getCoordinates().getX(), oldSpaceMarine.getCoordinates().getY(), (int) oldSpaceMarine.getHealth(), spaceMarine));
                    }
                }
            }
            if (needsToRemove) {
                removeSpaceMarinesLIst.add(new RemovingSpaceMarine(oldSpaceMarine));
            }
        }
        for (SpaceMarine newSpaceMarine : newList) {
            if (!idSet.contains(newSpaceMarine.getID())) {
                idSet.add(newSpaceMarine.getID());
                showSpaceMarineList.add(new ShowSpaceMarine(newSpaceMarine));
            }
        }
        currentList = new ArrayList<>(newList);
    }


    public void startTimer() {
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(Constants.SCREEN_WIDTH / 2, Constants.CENTER_PANEL_HEIGHT / 2);
        g2.setStroke(new BasicStroke(BASIC_STROKE));
        g2.drawLine(-Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH, 0);
        g2.drawLine(0, -LINE_HIGHT, 0, LINE_HIGHT);
        updateDelay--;
        if (updateDelay == 0) {
            checkUpdates();
            updateDelay = updateDelayConst;
        }
        moveSpaceMarine(g2);
        removeSpaceMarines(g2);
        showSpaceMarines(g2);
        showShowed(g2);

    }


    private synchronized void showShowed(Graphics2D g2) {
        for (SpaceMarine showedSpaceMarine : showedList) {
            drawSpaceMarine(g2, (int) showedSpaceMarine.getCoordinates().getX(), showedSpaceMarine.getCoordinates().getY(), (int) showedSpaceMarine.getHealth(), usersAndColors.get(showedSpaceMarine.getOwnerName()), MAX_ALPHA);
        }
    }

    private void showSpaceMarines(Graphics2D g2) {
        for (ShowSpaceMarine showingSpaceMarine : showSpaceMarineList) {
            Color color;
            SpaceMarine spaceMarine = showSpaceMarineList.get(0).spaceMarine;
            if (usersAndColors.containsKey(spaceMarine.getOwnerName())) {
                color = usersAndColors.get(spaceMarine.getOwnerName());
            } else {
                while (true) {
                    color = new Color((int) (Math.random() * MAX_COLOR_VALUE));
                    if (!colors.contains(color)) {
                        usersAndColors.put(spaceMarine.getOwnerName(), color);
                        break;
                    }
                }
            }
            showingSpaceMarine.tic--;
            if (showingSpaceMarine.tic == 0) {
                showSpaceMarineList.remove(showingSpaceMarine);
                return;
            }
            color = usersAndColors.get(showingSpaceMarine.spaceMarine.getOwnerName());
            int gap = (int) (showingSpaceMarine.y - showingSpaceMarine.y * (COUNTER_MAX - showingSpaceMarine.tic) / COUNTER_MAX);
            drawSpaceMarine(g2, (int) showingSpaceMarine.x, gap, (int) showingSpaceMarine.health, color, MAX_ALPHA);
        }
    }

    private void removeSpaceMarines(Graphics2D g2) {
        for (RemovingSpaceMarine removingSpaceMarine : removeSpaceMarinesLIst) {
            Color color;
            removingSpaceMarine.tic--;
            if (removingSpaceMarine.tic == 0) {
                removeSpaceMarinesLIst.remove(removingSpaceMarine);
                return;
            }
            color = usersAndColors.get(removingSpaceMarine.spaceMarine.getOwnerName());
            int teta = MAX_ALPHA - MAX_ALPHA * (COUNTER_MAX - removingSpaceMarine.tic) / COUNTER_MAX;
            drawSpaceMarine(g2, (int) removingSpaceMarine.spaceMarine.getCoordinates().getX(), removingSpaceMarine.spaceMarine.getCoordinates().getY(), (int) removingSpaceMarine.spaceMarine.getHealth(), color, teta);
        }
    }

    private void moveSpaceMarine(Graphics2D g2) {
        for (MovingSpaceMarine movingSpaceMarine : moveSpaceMarineList) {
            if (movingSpaceMarine.tic < 0) {
                moveSpaceMarineList.remove(movingSpaceMarine);
                showedList.add(movingSpaceMarine.spaceMarine);
                return;
            }
            movingSpaceMarine.tic--;
            movingSpaceMarine.x += movingSpaceMarine.deltaX;
            movingSpaceMarine.y += movingSpaceMarine.deltaY;
            movingSpaceMarine.health += movingSpaceMarine.deltaHealth;
            drawSpaceMarine(g2, (int) movingSpaceMarine.x, (long) movingSpaceMarine.y, (int) movingSpaceMarine.health, usersAndColors.get(movingSpaceMarine.spaceMarine.getOwnerName()), MAX_ALPHA);
        }
    }

    private void drawSpaceMarine(Graphics2D g2, int x, long y, int health, Color ownerColor, int alph) {
        final int factorForEar = 4;
        Color color = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), alph);
        g2.setPaint(color);
        Ellipse2D body = new Ellipse2D.Double(xCoordinatesFunc(x, convert(health)), yCoordinatesFunc(y, convert(health)), convert(health), convert(health));
        g2.draw(body);
        Ellipse2D rithEar = new Ellipse2D.Double(xCoordinatesFunc(x, convert(health)) - convert(health) / factorForEar, yCoordinatesFunc(y, convert(health)) - convert(health) / factorForEar, convert(health) / 2, convert(health) / 2);
        g2.draw(rithEar);
        Ellipse2D leftEar = new Ellipse2D.Double(xCoordinatesFunc(x, convert(health)) + convert(health) / factorForEar, yCoordinatesFunc(y, convert(health)) - convert(health) / factorForEar, convert(health) / 2, convert(health) / 2);
        g2.draw(leftEar);
    }

    private int convert(int health) {
        return (int) factor * health + MIN_SIZE;
    }

    private int xCoordinatesFunc(int x, int width) {
        return x - width / 2;
    }

    private int yCoordinatesFunc(long y, int height) {
        return Long.valueOf(y - height / 2).intValue();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = currentList.size() - 1; i >= 0; i--) {
            int x = e.getX() - Constants.SCREEN_WIDTH / 2;
            int y = -e.getY() + Constants.SCREEN_HEIGHT * AMOUNT_OF_PART_TO_CENTER_HALF / AMOUNT_OF_PARTS;
            SpaceMarine spaceMarine = currentList.get(i);
            if (x <= spaceMarine.getCoordinates().getX() + spaceMarine.getHealth() && x >= spaceMarine.getCoordinates().getX() - spaceMarine.getHealth()
                    && y >= spaceMarine.getCoordinates().getY() - spaceMarine.getHealth() * HITBOX_LOW_POINT && y <= spaceMarine.getCoordinates().getY() + spaceMarine.getHealth() * Y_FIRST_POINT_NUMERATOR / 2) {

                if (caeManager.getUsername().equals(spaceMarine.getOwnerName())) {
                    ChangeFieldsSpaceMarine changeFieldsOfSpaceMarinePanel = new ChangeFieldsSpaceMarine(caeManager, resourceBundle, spaceMarine);
                    changeFieldsOfSpaceMarinePanel.showJFrame();
                } else {
                    JFrame subFrame = new JFrame();
                    JPanel mainPanel = new JPanel();
                    JLabel jLabel = BasicGUIElementsFabric.createBasicLabel(resourceBundle.getString("It is not your spaceMarine"));
                    subFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                    mainPanel.add(jLabel);
                    JButton exitButton = BasicGUIElementsFabric.createBasicButton(resourceBundle.getString("OK"));
                    exitButton.setAlignmentX(CENTER_ALIGNMENT);
                    JPanel subPanel = new JPanel();
                    subPanel.setLayout(new GridBagLayout());
                    subPanel.add(exitButton);
                    mainPanel.add(subPanel);
                    exitButton.addActionListener(e1 -> subFrame.dispose());
                    subFrame.setContentPane(mainPanel);
                    subFrame.revalidate();
                    subFrame.pack();
                    subFrame.setLocationRelativeTo(null);
                    subFrame.setVisible(true);
                }
                break;
        }
    }

}

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }

    private static class RemovingSpaceMarine {
        private SpaceMarine spaceMarine;
        private int tic = COUNTER_MAX;

        RemovingSpaceMarine(SpaceMarine spaceMarine) {
            this.spaceMarine = spaceMarine;
        }
    }

    private static class MovingSpaceMarine {
        private double x;
        private long y;
        private final SpaceMarine spaceMarine;
        private double health;
        private int tic = COUNTER_MAX;
        private final double deltaX;
        private final long deltaY;
        private final double deltaHealth;
        MovingSpaceMarine(int oldX, long oldY, int oldHealth, SpaceMarine spaceMarine) {
            this.x = oldX;
            this.y = oldY;
            this.spaceMarine = spaceMarine;
            this.health = oldHealth;
            this.deltaX = ((spaceMarine.getCoordinates().getX() - x) / COUNTER_MAX);
            this.deltaY = (spaceMarine.getCoordinates().getY() - y) / COUNTER_MAX;
            this.deltaHealth = ((spaceMarine.getHealth() - health) / COUNTER_MAX);
        }
    }

    private static class ShowSpaceMarine {
        private SpaceMarine spaceMarine;
        private double x;
        private long y;
        private double health;
        private int tic = COUNTER_MAX;
        ShowSpaceMarine(SpaceMarine spaceMarine) {
            this.x = spaceMarine.getCoordinates().getX();
            this.y = spaceMarine.getCoordinates().getY();
            this.spaceMarine = spaceMarine;
            this.health = spaceMarine.getHealth();
    }
}
}
