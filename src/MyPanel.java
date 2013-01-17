
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class MyPanel extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g1 =(Graphics2D) g;
        //Your code
        
        this.repaint();
    }
}
