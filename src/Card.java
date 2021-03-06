//card class
public class Card
{
    //contains the basics of a card
    char suit;
    int number;
    public Card(char s, int n)//constructor
    {
        suit = s;
        number = n;
    }
    public char getSuit()
    {
        return suit;
    }
    //accessor and mutator methods
    public int getNumber()
    {
        return number;
    }
    public void setSuit(char s)
    {
        suit = s;
    }
    public void setNumber(int n)
    {
        number = n;
    }
    public String toString()
    {
        return suit+""+number;
    }
}