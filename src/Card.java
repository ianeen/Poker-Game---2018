public class Card
{
    char suit;
    int number;
    public Card(char s, int n)
    {
        suit = s;
        number = n;
    }
    public int getSuit()
    {
        return suit;
    }
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