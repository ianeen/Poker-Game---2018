import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//Deck class
public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();//ArrayList of cards to make a deck
    public Deck()
    {
        genDeck();
    }
    //Shuffles the cards
    public void shuffle()
    {
        Collections.shuffle(deck);
    }
    //Generates the deck with all the different cards
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
