import java.util.ArrayList;
import java.util.Collections;
//Flop class
public class Flop {
    private ArrayList<Card> flop = new ArrayList<>();
    public Flop()//constructor
    {

    }
    //adds the the first flop
    public void genFlop3(ArrayList<Card> deck)
    {
        for(int i = 0 ; i < 4; i++) {
            flop.add(deck.get(0));
            deck.remove(0);
        }

    }
    //adds the other 2 flops
    public void genFlopAfter(ArrayList<Card> deck)
    {
        flop.add(deck.get(0));
        deck.remove(0);
    }
    //accessor and mutator methods
    public Card getCard1()
    {
        return flop.get(0);
    }
    public Card getCard2()
    {
        return flop.get(1);
    }
    public Card getCard3()
    {
        return flop.get(2);
    }
    public Card getCard4()
    {
        return flop.get(3);
    }
    public Card getCard5()
    {
        return flop.get(4);
    }

    public ArrayList<Card> getFlop() {
        return flop;
    }

    public String toString()
    {
        return ""+flop;
    }
}