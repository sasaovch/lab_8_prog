package util;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Ex extends Frame {
    private static final int BASIC_STROKE = 5;
       
   public Ex(){
      super("Java AWT Examples");
      prepareGUI();
   }

   public static void main(String[] args){
      Ex  awtGraphicsDemo = new Ex();  
      awtGraphicsDemo.setVisible(true);
   }

   private void prepareGUI(){
      setSize(400,400);
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      }); 
   }    

   @Override
   public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    // g2.translate(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT * AMOUNT_OF_PART_TO_CENTER_HALF / AMOUNT_OF_PARTS);
    // g2.setStroke(new BasicStroke());
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
    Font font = new Font("Serif", Font.PLAIN, 24);
    g2.drawLine(0, 100, 100, 0);
    g2.drawLine(50, 0, 50, 100);
      g2.setFont(font);
      g2.drawString("Welcome to TutorialsPoint", 50, 70); 
   }
}