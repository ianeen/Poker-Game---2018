//Ian Rokas 11A - Final Project Code

//import statements
import java.util.Random;
import java.util.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//main class
public class Main extends Applet implements MouseListener
{
    int x = 0;//mouseX
    int y = 0;//mouseY
    Image dbImage; //image variable for double buffer
    Graphics dbg;//double buffer

    //initiation method
    public void init()
    {
        addMouseListener(this);//starting mouse listener
    }

    //paint method
    public void paint(Graphics g)
    {
        Color color = new Color();
        g.setColor(Co);

    }

    public void update(Graphics g)//double buffer
    {
        dbImage = createImage(600, 600);
        dbg = dbImage.getGraphics();
        paint(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    // mouseClicked method
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }

}