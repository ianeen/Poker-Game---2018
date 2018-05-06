import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    public Deck()
    {
        genDeck();
    }
    public void shuffle()
    {
        Collections.shuffle(deck);
    }
    public void genDeck()
    {
        for(int i = 1; i <= 13; i++)
        {
            Card c = new Card('S', i);
            deck.add(c);
        }
        for(int i = 1; i <= 13; i++)
        {
            Card c = new Card('D', i);
            deck.add(c);
        }
        for(int i = 1; i <= 13; i++)
        {
            Card c = new Card('H', i);
            deck.add(c);
        }
        for(int i = 1; i <= 13; i++)
        {
            Card c = new Card('C', i);
            deck.add(c);
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public String toString()
    {
        return ""+deck;
    }
}
