package com.ut.util;

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
import com.ut.exeptions.ConnectionLostExeption;
import com.ut.gui.BasicGUIElementsFabric;
import com.ut.gui.ChangeFieldsSpaceMarine;
import com.ut.gui.GUIManager;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
    private static final int COUNTER_MAX = 150;
    private static final int AMOUNT_OF_PARTS = 10;
    private static final int MAX_COLOR_VALUE = 0xFFFFFF;
    private static final int BASIC_STROKE = 4;
    private static final int AMOUNT_OF_PART_TO_CENTER_HALF = 4;
    private static final int Y_FIRST_POINT_NUMERATOR = 3;
    private static final double HITBOX_LOW_POINT = 6 / 5.0;
    private static final int LINE_HIGHT = Constants.SCREEN_HEIGHT / 3;
    private static final int LINE_WIDHT = Constants.SCREEN_WIDTH / 7 * 3;
    private static final int TIME_DELAY = 15;
    private static final int TRANSLATE_GRAPH_WIDHT = Constants.SCREEN_WIDTH / 2;
    private static final int TRANSLATE_GRAPH_HEIGHT = Constants.CENTER_PANEL_HEIGHT / 3 * 2;

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
    private BasicGUIElementsFabric basicGUIElementsFabric;
    private GUIManager guiManager;

    public CoordinatesGraphics(GUIManager guiManager, ConnectionAndExecutorManager caeManager, ResourceBundle resourceBundle) {
        this.caeManager = caeManager;
        this.resourceBundle = resourceBundle;
        this.guiManager = guiManager;
        basicGUIElementsFabric = new BasicGUIElementsFabric(resourceBundle);
        currentList = new ArrayList<>();
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.CENTER_PANEL_HEIGHT));
        addMouseListener(this);
        startTimer();
        checkUpdates();
    }

    public void printerror(String error) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(Constants.POPUP_FRAME_WIDTH, Constants.POPUP_FRAME_HIGHT));
        JButton okButton = basicGUIElementsFabric.createBasicButton(("OK"));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
            }
        });
        JLabel errorLabel = basicGUIElementsFabric.createBasicLabel((error));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.add(errorLabel, BorderLayout.CENTER);
        frame.add(okButton, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void checkUpdates() {
        try {
            newList = caeManager.getListFromServer();
        } catch (ConnectionLostExeption e) {
            printerror(ConstantsLanguage.ERROR_TO_CONNECT_TO_SERVER);
            return;
        }
        for (SpaceMarine oldSpaceMarine : currentList) {
            boolean needsToRemove = true;
            for (SpaceMarine spaceMarine : newList) {
                if (oldSpaceMarine.getID().equals(spaceMarine.getID())) {
                    needsToRemove = false;
                    if (oldSpaceMarine.getCoordinates().equals(spaceMarine.getCoordinates())
                    && oldSpaceMarine.getCoordinates().getX() == spaceMarine.getCoordinates().getX()
                    && oldSpaceMarine.getCoordinates().getY().equals(spaceMarine.getCoordinates().getY())
                    && oldSpaceMarine.getHealth().equals(spaceMarine.getHealth())) {
                        oldSpaceMarine = spaceMarine;
                    } else {
                        showedList.remove(oldSpaceMarine);
                        moveSpaceMarineList.add(new MovingSpaceMarine(oldSpaceMarine.getCoordinates().getX(), oldSpaceMarine.getCoordinates().getY(), oldSpaceMarine.getHealth(), spaceMarine));
                    }
                }
            }
            if (needsToRemove) {
                    showedList.remove(oldSpaceMarine);
                    removeSpaceMarinesLIst.add(new RemovingSpaceMarine(oldSpaceMarine.getCoordinates().getX(), oldSpaceMarine.getCoordinates().getY(), oldSpaceMarine));
                }
            }
            for (SpaceMarine newSpaceMarine : newList) {
                if (!idSet.contains(newSpaceMarine.getID())) {
                    idSet.add(newSpaceMarine.getID());
                    showSpaceMarineList.add(new ShowSpaceMarine(newSpaceMarine.getCoordinates().getX(), newSpaceMarine.getCoordinates().getY(), newSpaceMarine.getHealth(), newSpaceMarine));
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
        g2.translate(TRANSLATE_GRAPH_WIDHT, TRANSLATE_GRAPH_HEIGHT);
        g2.setStroke(new BasicStroke(BASIC_STROKE));
        g2.drawLine(-LINE_WIDHT, 0, LINE_WIDHT, 0);
        g2.drawLine(0, -LINE_HIGHT, 0, LINE_HIGHT);
        updateDelay--;
        if (updateDelay == 0) {
            checkUpdates();
            updateDelay = updateDelayConst;
        }
        removeSpaceMarines(g2);
        moveSpaceMarine(g2);
        showSpaceMarines(g2);
        showShowed(g2);
    }


    private synchronized void showShowed(Graphics2D g2) {
        for (SpaceMarine showedSpaceMarine : showedList) {
            drawSpaceMarine(g2, showedSpaceMarine.getCoordinates().getX(), showedSpaceMarine.getCoordinates().getY(), showedSpaceMarine.getHealth(), usersAndColors.get(showedSpaceMarine.getOwnerName()), MAX_ALPHA);
        }
    }

    private void showSpaceMarines(Graphics2D g2) {
        List<ShowSpaceMarine> removeList = new ArrayList<>();
        for (ShowSpaceMarine showingSpaceMarine : showSpaceMarineList) {
            Color color;
            SpaceMarine spaceMarine = showingSpaceMarine.spaceMarine;
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
            showingSpaceMarine.tic++;
            color = usersAndColors.get(showingSpaceMarine.spaceMarine.getOwnerName());
            double gap = ((LINE_WIDHT + showingSpaceMarine.x) / COUNTER_MAX * showingSpaceMarine.tic) - LINE_WIDHT;
            drawSpaceMarine(g2, gap, showingSpaceMarine.y, showingSpaceMarine.health, color, MAX_ALPHA);
            if (showingSpaceMarine.tic == COUNTER_MAX) {
                removeList.add(showingSpaceMarine);
                showedList.add(showingSpaceMarine.spaceMarine);
            }
        }
        showSpaceMarineList.removeAll(removeList);
    }

    private void removeSpaceMarines(Graphics2D g2) {
        List<RemovingSpaceMarine> removeList = new ArrayList<>();
        for (RemovingSpaceMarine removingSpaceMarine : removeSpaceMarinesLIst) {
            Color color;
            removingSpaceMarine.tic++;
            color = usersAndColors.get(removingSpaceMarine.spaceMarine.getOwnerName());
            double gap = (-(LINE_WIDHT + removingSpaceMarine.x) / COUNTER_MAX * removingSpaceMarine.tic) + removingSpaceMarine.x;
            drawSpaceMarine(g2, gap, removingSpaceMarine.y, (int) removingSpaceMarine.spaceMarine.getHealth(), color, MAX_ALPHA);
            if (removingSpaceMarine.tic == COUNTER_MAX) {
                removeList.add(removingSpaceMarine);
                showedList.remove(removingSpaceMarine.spaceMarine);
            }
        }
        removeSpaceMarinesLIst.removeAll(removeList);
    }

    private void moveSpaceMarine(Graphics2D g2) {
        List<MovingSpaceMarine> removeList = new ArrayList<>();
        for (MovingSpaceMarine movingSpaceMarine : moveSpaceMarineList) {
            movingSpaceMarine.tic++;
            movingSpaceMarine.x += movingSpaceMarine.deltaX;
            movingSpaceMarine.y += movingSpaceMarine.deltaY;
            movingSpaceMarine.health += movingSpaceMarine.deltaHealth;
            drawSpaceMarine(g2, movingSpaceMarine.x, (long) movingSpaceMarine.y, movingSpaceMarine.health, usersAndColors.get(movingSpaceMarine.spaceMarine.getOwnerName()), MAX_ALPHA);
            if (movingSpaceMarine.tic == COUNTER_MAX) {
                removeList.add(movingSpaceMarine);
                showedList.add(movingSpaceMarine.spaceMarine);
            }
        }
        moveSpaceMarineList.removeAll(removeList);
    }

    private void drawSpaceMarine(Graphics2D g2, double x, long y, int health, Color ownerColor, int alph) {
        final int factorHealthForRightEar = 4;
        final int factorHealthForLertEar = 3;
        Color color = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), alph);
        g2.setPaint(color);
        Ellipse2D body = new Ellipse2D.Double(x, -y, health, health);
        g2.draw(body);
        Ellipse2D rithEar = new Ellipse2D.Double(x - health / factorHealthForRightEar, -y - health / factorHealthForRightEar, health / 2, health / 2);
        g2.draw(rithEar);
        Ellipse2D leftEar = new Ellipse2D.Double(x + (health * factorHealthForLertEar / factorHealthForRightEar), -y - health / factorHealthForRightEar, health / 2, health / 2);
        g2.draw(leftEar);
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
                    ChangeFieldsSpaceMarine changeFieldsOfSpaceMarinePanel = new ChangeFieldsSpaceMarine(guiManager, caeManager, resourceBundle, false);
                    changeFieldsOfSpaceMarinePanel.showJFrame(spaceMarine);
                } else {
                    JFrame subFrame = new JFrame();
                    JPanel mainPanel = new JPanel();
                    JLabel jLabel = basicGUIElementsFabric.createBasicLabel(ConstantsLanguage.NOT_YOUR_SPMAR_MESSAGE);
                    subFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                    mainPanel.add(jLabel);
                    JButton exitButton = basicGUIElementsFabric.createBasicButton(ConstantsLanguage.OK);
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
        private double x;
        private long y;
        private int tic = 0;

        RemovingSpaceMarine(double x, long y, SpaceMarine spaceMarine) {
            this.x = x;
            this.y = y;
            this.spaceMarine = spaceMarine;
        }
    }

    private static class MovingSpaceMarine {
        private SpaceMarine spaceMarine;
        private double x;
        private long y;
        private int health;
        private int tic = 0;
        private final double deltaX;
        private final long deltaY;
        private final double deltaHealth;
        MovingSpaceMarine(double oldX, long oldY, int oldHealth, SpaceMarine spaceMarine) {
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
        private int health;
        private int tic = -COUNTER_MAX;
        ShowSpaceMarine(double x, long y, int health, SpaceMarine spaceMarine) {
            this.x = x;
            this.y = y;
            this.health = health;
            this.spaceMarine = spaceMarine;
        }
}
}
