package util;



import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;

import gui.BasicGUIElementsFabric;
import gui.GUIManager;
import gui.VisualJPanel;
import lombok.extern.slf4j.Slf4j;

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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/*
отрисовщик координатной плоскости
 */
@Slf4j
public class CoordinatesDemo extends JComponent implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int MAX_ALPHA = 255;
    private static final int DELTA_ALPHA_PER_TIC = 5;
    private static final int COUNTER_MAX = 30;
    private static final int AMOUNT_OF_PARTS = 10; //делим panel на 10 частей
    private static final int HEIGHT_OF_LEG_TO_WINGSPAN = 10; //размер лапы 1/10 health
    private static final int WIDTH_OF_LEG_TO_WINGSPAN = 5;
    private static final int AMOUNT_OD_POINTS = 5;
    private static final int DELTA_X_TO_WINGSPAN = 4;
    private static final double HEAD_HEIGHT_TO_WINGSPAN = 5 / 4.0;
    private static final int MAX_COLOR_VALUE = 0xFFFFFF;
    private static final int BASIC_STROKE = 4;
    private static final int AMOUNT_OF_PART_TO_CENTER_HALF = 4;
    private static final int Y_FIRST_POINT_NUMERATOR = 3;
    private static final double HITBOX_LOW_POINT = 6 / 5.0; //Я попытался структурировать константы, но большинство из них это просто значения, которые я получил из расчетов
    private HashMap<String, Color> usersAndColors = new HashMap<>();
    private Set<Color> colors = new HashSet<>();
    private List<SpaceMarine> spaceMarinesNeedsToBeShowed; //Для анимации появления
    private List<RemovingSpaceMarine> spaceMarinesNeedsToBeRemoved = new ArrayList<>(); //Для анимации удаления
    private List<MovingSpaceMarine> spaceMarinesNeedsToBeMoved = new ArrayList<>(); //Для анимации перемещения
    private List<SpaceMarine> showedSpaceMarines = new ArrayList<>(); //Просто чтобы отрисовывать уже появившихся драконов
    private List<SpaceMarine> currentList;
    private VisualJPanel visualStyleMain;
    private int alpha = 0;
    private int frequencyOfUpdateConst = 300;
    private int frequencyOfUpdate = frequencyOfUpdateConst; //Можно создать новый поток, но тогда придется создавать новый ConnectionManager, так как нельзя работать с PipedStream'ами с двух потоков
    private Timer timer = new Timer(15, this);
    private Set<Long> ids = new HashSet<>();
    private GUIManager guiManager;
    private ResourceBundle resourceBundle;
    private ConnectionAndExecutorManager caeManager;
    private Locale locale;
    private int maxHealth = 0;
    private double maxX = 0;
    private long maxY = 0;
    private int minHealth = 0;
    private double minX = 0;
    private long minY = 0;
    private double qu = 0;

    public CoordinatesDemo(VisualJPanel visualStyleMain, GUIManager guiManager, ConnectionAndExecutorManager caeManager) {
        this.visualStyleMain = visualStyleMain;
        this.guiManager = guiManager;
        this.caeManager = caeManager;
        setBackground(Color.PINK);
        // setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 8 * 7, Constants.CENTER_PANEL_HEIGHT/7*8));
        // addMouseListener(this);
        CommandResult result = caeManager.executeCommand("show", null, null);
        spaceMarinesNeedsToBeShowed = (List<SpaceMarine>)result.getData();
        currentList = new ArrayList<>(spaceMarinesNeedsToBeShowed);
        maxHealth = 0;
        maxX = 0;
        maxY = 0;
        for (SpaceMarine spaceMarine : currentList) {
            ids.add(spaceMarine.getID());
            compareMaxVariables(spaceMarine.getHealth(), spaceMarine.getCoordinates().getX(), spaceMarine.getCoordinates().getY());
            qu = 200 / (maxHealth - minHealth);
        }
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

    public void checkUpdates() {
        CommandResult result = caeManager.executeCommand("show", null, null);
        List<SpaceMarine> spaceMarines = (List<SpaceMarine>) result.getData();
        for (SpaceMarine oldSpaceMarine : currentList) {
            boolean needsToRemove = true;
            for (SpaceMarine spaceMarine : spaceMarines) {
                compareMaxVariables(spaceMarine.getHealth(), spaceMarine.getCoordinates().getX(), spaceMarine.getCoordinates().getY());
                qu = 200 / (maxHealth - minHealth);
                if (oldSpaceMarine.getID().equals(spaceMarine.getID())) {
                    needsToRemove = false;
                    if (oldSpaceMarine.getCoordinates().equals(spaceMarine.getCoordinates())
                            && oldSpaceMarine.getCoordinates().getX() == spaceMarine.getCoordinates().getX()
                            && oldSpaceMarine.getCoordinates().getY().equals(spaceMarine.getCoordinates().getY())) {
                        oldSpaceMarine = spaceMarine;
                    } else {
                        spaceMarinesNeedsToBeMoved.add(new MovingSpaceMarine((int) oldSpaceMarine.getCoordinates().getX(), oldSpaceMarine.getCoordinates().getY(), (int) oldSpaceMarine.getHealth(), spaceMarine));
                        showedSpaceMarines.remove(oldSpaceMarine);
                    }
                }
            }
            if (needsToRemove) {
                spaceMarinesNeedsToBeRemoved.add(new RemovingSpaceMarine(oldSpaceMarine));
                showedSpaceMarines.remove(oldSpaceMarine);
            }
        }
        for (SpaceMarine newSpaceMarine : spaceMarines) {
            if (!ids.contains(newSpaceMarine.getID())) {
                ids.add(newSpaceMarine.getID());
                spaceMarinesNeedsToBeShowed.add(newSpaceMarine);
            }
        }
        currentList = new ArrayList<>(spaceMarines);
    }


    public void startTimer() {
        timer.start();
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(Constants.SCREEN_WIDTH / 2 - 100, Constants.CENTER_PANEL_HEIGHT/ 2);
        g2.setStroke(new BasicStroke(BASIC_STROKE));
        g2.drawLine(-Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH, 0);
        g2.drawLine(0, -Constants.SCREEN_HEIGHT / 5 * 2, 0, Constants.SCREEN_HEIGHT / 5 * 2);
        frequencyOfUpdate--;
        if (frequencyOfUpdate == 0) {
            checkUpdates();
            frequencyOfUpdate = frequencyOfUpdateConst;
        }
        moveSpaceMarine(g2);
        removeSpaceMarines(g2);
        showSpaceMarines(g2);
        showShowed(g2);

    }


    private synchronized void showShowed(Graphics2D g2) {
        for (SpaceMarine showedSpaceMarine : showedSpaceMarines) {
            drawSpaceMarine(g2, (int) showedSpaceMarine.getCoordinates().getX(), showedSpaceMarine.getCoordinates().getY(), (int) showedSpaceMarine.getHealth(), usersAndColors.get(showedSpaceMarine.getOwnerName()), MAX_ALPHA);
        }
    }

    private void showSpaceMarines(Graphics2D g2) {
        if (spaceMarinesNeedsToBeShowed.size() > 0) {
            Color color;
            SpaceMarine spaceMarine = spaceMarinesNeedsToBeShowed.get(0);
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
            alpha += DELTA_ALPHA_PER_TIC;
            if (alpha > MAX_ALPHA) {
                SpaceMarine showed = spaceMarinesNeedsToBeShowed.remove(0);
                showedSpaceMarines.add(showed);
                alpha = 0;
            }
            if (spaceMarinesNeedsToBeShowed.size() == 0) {
                drawSpaceMarine(g2, (int) spaceMarine.getCoordinates().getX(), spaceMarine.getCoordinates().getY(), (int) spaceMarine.getHealth(), color, MAX_ALPHA);

            } else {
                spaceMarine = spaceMarinesNeedsToBeShowed.get(0);
                drawSpaceMarine(g2, (int) spaceMarine.getCoordinates().getX(), spaceMarine.getCoordinates().getY(), (int) spaceMarine.getHealth(), color, alpha);
            }
        }
    }

    private void removeSpaceMarines(Graphics2D g2) {
        for (RemovingSpaceMarine removingSpaceMarine : spaceMarinesNeedsToBeRemoved) {
            Color color;
            removingSpaceMarine.tic--;
            if (removingSpaceMarine.tic == 0) {
                spaceMarinesNeedsToBeRemoved.remove(removingSpaceMarine);
                return;
            }

            color = usersAndColors.get(removingSpaceMarine.spaceMarine.getOwnerName());
            int teta = MAX_ALPHA - MAX_ALPHA * (COUNTER_MAX - removingSpaceMarine.tic) / COUNTER_MAX;

            drawSpaceMarine(g2, (int) removingSpaceMarine.spaceMarine.getCoordinates().getX(), removingSpaceMarine.spaceMarine.getCoordinates().getY(), (int) removingSpaceMarine.spaceMarine.getHealth(), color, teta);

        }
    }

    private void moveSpaceMarine(Graphics2D g2) {
        for (MovingSpaceMarine movingSpaceMarine : spaceMarinesNeedsToBeMoved) {
            if (movingSpaceMarine.tic < 0) {
                spaceMarinesNeedsToBeMoved.remove(movingSpaceMarine);
                showedSpaceMarines.add(movingSpaceMarine.spaceMarine);
                return;
            }
            movingSpaceMarine.tic--;
            movingSpaceMarine.x += movingSpaceMarine.deltaX;
            movingSpaceMarine.y += movingSpaceMarine.deltaY;
            movingSpaceMarine.health += movingSpaceMarine.deltaWingspan;
            drawSpaceMarine(g2, (int) movingSpaceMarine.x, (long) movingSpaceMarine.y, (int) movingSpaceMarine.health, usersAndColors.get(movingSpaceMarine.spaceMarine.getOwnerName()), MAX_ALPHA);
        }
    }

    private void drawSpaceMarine(Graphics2D g2, int x, long y, int health, Color ownerColor, int alph) {

        Color color = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), alph);
        g2.setPaint(color);
        Ellipse2D body = new Ellipse2D.Double(xCoordinatesFunc(x, convert(health)), yCoordinatesFunc(y, convert(health)), convert(health), convert(health));
        g2.draw(body);
        Ellipse2D rithEar = new Ellipse2D.Double(xCoordinatesFunc(x, convert(health)) - convert(health) / 4, yCoordinatesFunc(y, convert(health)) - convert(health) / 4, convert(health) / 2, convert(health) / 2);
        g2.draw(rithEar);
        Ellipse2D leftEar = new Ellipse2D.Double(xCoordinatesFunc(x, convert(health)) + convert(health) / 4 * 3, yCoordinatesFunc(y, convert(health)) - convert( health) / 4, convert(health) / 2, convert(health) / 2);
        g2.draw(leftEar);
        // g2.drawPolyline(new int[]{x + health / DELTA_X_TO_WINGSPAN, x + health / 2, x + health, x + health, x + health / DELTA_X_TO_WINGSPAN}, new int[]{(int) -(Math.sqrt(Y_FIRST_POINT_NUMERATOR) * health / 2) - Long.valueOf(y).intValue(), -health - Long.valueOf(y).intValue(), -health / 2 - Long.valueOf(y).intValue(), -Long.valueOf(y).intValue(), (int) (Math.sqrt(Y_FIRST_POINT_NUMERATOR) * health / 2 - y)}, AMOUNT_OD_POINTS);
        // g2.drawPolyline(new int[]{x - health / DELTA_X_TO_WINGSPAN, x - health / 2, x - health, x - health, x - health / DELTA_X_TO_WINGSPAN}, new int[]{(int) -(Math.sqrt(Y_FIRST_POINT_NUMERATOR) * health / 2) - Long.valueOf(y).intValue(), -health - Long.valueOf(y).intValue(), -health / 2 - Long.valueOf(y).intValue(), -Long.valueOf(y).intValue(), (int) (Math.sqrt(Y_FIRST_POINT_NUMERATOR) * health / 2 - y)}, AMOUNT_OD_POINTS);
        // g2.drawOval(xCoordinatesFunc(x, health), yCoordinatesFunc(y, health), convert(health), convert(health));
        // g2.drawOval(xCoordinatesFunc(x + health / DELTA_X_TO_WINGSPAN, health / 2), yCoordinatesFunc(y - health - health / HEIGHT_OF_LEG_TO_WINGSPAN, health / WIDTH_OF_LEG_TO_WINGSPAN), health / 2, health / WIDTH_OF_LEG_TO_WINGSPAN);
        // g2.drawOval(xCoordinatesFunc(x - health / DELTA_X_TO_WINGSPAN, health / 2), yCoordinatesFunc(y - health - health / HEIGHT_OF_LEG_TO_WINGSPAN, health / WIDTH_OF_LEG_TO_WINGSPAN), health / 2, health / WIDTH_OF_LEG_TO_WINGSPAN);


    }

    private int convert(int health) {
        return (int) qu * health + 10;
    }

    private int xCoordinatesFunc(int x, int width) {
        return x - width / 2;
    }

    private int yCoordinatesFunc(long y, int height) {
        return Long.valueOf(y - height / 2).intValue();
    }

    // @Override
    // public void mouseClicked(MouseEvent e) {
    //     for (int i = currentList.size() - 1; i >= 0; i--) {
    //         int x = e.getX() - Constants.SCREEN_WIDTH / 2;
    //         int y = -e.getY() + Constants.SCREEN_HEIGHT * AMOUNT_OF_PART_TO_CENTER_HALF / AMOUNT_OF_PARTS;
    //         SpaceMarine spaceMarine = currentList.get(i);
    //         if (x <= spaceMarine.getCoordinates().getX() + spaceMarine.getHealth() && x >= spaceMarine.getCoordinates().getX() - spaceMarine.getHealth()
    //                 && y >= spaceMarine.getCoordinates().getY() - spaceMarine.getHealth() * HITBOX_LOW_POINT && y <= spaceMarine.getCoordinates().getY() + spaceMarine.getHealth()() * Y_FIRST_POINT_NUMERATOR / 2) {

    //             if (commandExecutor.getConnectionManager().getUserId() == spaceMarine.getOwnerId()) {
    //                 // ChangeFieldsOfSpaceMarinePanel changeFieldsOfSpaceMarinePanel = new ChangeFieldsOfSpaceMarinePanel(visualStyleMain.getConnectionManager(), visualStyleMain.getCurrentBundle(), spaceMarine);
    //                 changeFieldsOfSpaceMarinePanel.drawPanel();
    //             } else {
    //                 JFrame subFrame = new JFrame();
    //                 JPanel mainPanel = new JPanel();
    //                 JLabel jLabel = BasicGUIElementsFabric.createBasicLabel(visualStyleMain.getCurrentBundle().getString("It is not your spaceMarine"));
    //                 subFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //                 mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    //                 mainPanel.add(jLabel);
    //                 JButton exitButton = BasicGUIElementsFabric.createBasicButton(visualStyleMain.getCurrentBundle().getString("OK"));
    //                 exitButton.setAlignmentX(CENTER_ALIGNMENT);
    //                 JPanel subPanel = new JPanel();
    //                 subPanel.setLayout(new GridBagLayout());
    //                 subPanel.add(exitButton);
    //                 mainPanel.add(subPanel);
    //                 exitButton.addActionListener(e1 -> subFrame.dispose());
    //                 subFrame.setContentPane(mainPanel);
    //                 subFrame.revalidate();
    //                 subFrame.pack();
    //                 subFrame.setLocationRelativeTo(null);
    //                 subFrame.setVisible(true);
    //             }
    //             break;
    //         }
    //     }
    // // }

    // @Override
    // public void mousePressed(MouseEvent e) {
    //     //do nothing
    // }

    // @Override
    // public void mouseReleased(MouseEvent e) {
    //     //do nothing
    // }

    // @Override
    // public void mouseEntered(MouseEvent e) {
    //     //do nothing
    // }

    // @Override
    // public void mouseExited(MouseEvent e) {
    //     //do nothing
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
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
        private double y;
        private final SpaceMarine spaceMarine;
        private double health;
        private int tic = COUNTER_MAX;
        private final double deltaX;
        private final double deltaY;
        private final double deltaWingspan;

        MovingSpaceMarine(int x, Long y, int oldWingspan, SpaceMarine spaceMarine) {
            this.x = x;
            this.y = y;
            this.spaceMarine = spaceMarine;
            this.health = oldWingspan;
            this.deltaX = ((spaceMarine.getCoordinates().getX() - x) / COUNTER_MAX);
            this.deltaY = (spaceMarine.getCoordinates().getY() - y) * 1.0 / COUNTER_MAX;
            this.deltaWingspan = ((spaceMarine.getHealth() - health) / COUNTER_MAX);
        }
    }

}
