import java.applet.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends Applet
{

    public void update()
    {
        Card[] cards = new Card[52];
        for(int i = 0; i < cards.length; i++)
        {
            cards[i] = new Card('s', i);
        }
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.green);
        g.fillRect(0,0, 800,400);
        update();
    }
}