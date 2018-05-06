import java.util.ArrayList;
import java.util.Collections;

public class Hand{
    ArrayList<Card> hand = new ArrayList<Card>();
    ArrayList<Card> flop = new ArrayList<Card>(); // for now
    ArrayList<Integer> num = new ArrayList<Integer>();
    ArrayList<Character> type = new ArrayList<Character>();
    public Hand()
    {
    }

    public void genHand(ArrayList<Card> deck)
    {
        for(int i = 0; i < 2; i++)
        {
            System.out.println(deck.get(0));
            hand.add(deck.get(0));
            deck.remove(0);
        }
    }
    public int compareTo(Hand hand) {
        System.out.println(handType(flop));
        System.out.println(hand.handType(flop));
        return handType(flop) - hand.handType(flop);
    }
    public int handType(ArrayList<Card> flop)
    {
        if(isStraight() && isFlush())
            return 0;
        else if(isKindOf4())
            return 1;
        else if(isKindOf2() && isKindOf3())
            return 2;
        else if(isFlush())
            return 3;
        else if(isStraight())
            return 4;
        else if(isKindOf3())
            return 5;
        else if(isKindOf2())
            return 6;
        else
            return 7;
    }
    public boolean isFlush()
    {
        setUp();
        int totals = 0;
        int totalh = 0;
        int totalc = 0;
        int totald = 0;
        for(int i = 0; i < 7; i++)
        {
            if(type.get(i) == 'c')
                totalc++;
            else if(type.get(i) == 's')
                totals++;
            else if(type.get(i) == 'h')
                totalh++;
            else if(type.get(i) == 'd')
                totald++;
            if(totals == 5 || totalc == 5 || totalh == 5 || totald == 5)
                return true;
        }
        return false;
    }
    public boolean isStraight()
    {
        setUp();
        boolean straight = false;
        for(int i = 0; i < num.size()-2; i++)
        {

            for(int j = i+1; j < num.size()-1; j++)
            {
                if(num.get(i) == num.get(j))
                {
                    num.remove(j);
                }
            }
        }
        if(num.size()>=5) {
            for (int i = 0; i < num.size()-5; i++) {
                straight = true;
                for (int j = i; j < i + 5; j++) {
                    if (num.get(j + 1) - num.get(j) != 1) {
                        straight = false;
                        break;
                    }
                }
                if (straight)
                    break;
            }
        }
        return straight;
    }
    public boolean isKindOf2()
    {
        setUp();
        int total = 1;
        for(int i = 0; i < num.size()-2; i++)
        {
            total = 1;
            for(int j = i+1; j < num.size()-1; j++)
            {
                if(num.get(i) == num.get(j))
                {
                    total++;
                }
            }
            if(total == 2)
                return true;
        }
        return false;
    }
    public boolean isKindOf3()
    {
        setUp();
        int total = 1;
        for(int i = 0; i < num.size()-2; i++)
        {
            total = 1;
            for(int j = i+1; j < num.size()-1; j++)
            {
                if(num.get(i) == num.get(j))
                {
                    total++;
                }
            }
            if(total == 3)
                return true;
        }
        return false;
    }
    public boolean isKindOf4()
    {
        setUp();
        int total = 1;
        for(int i = 0; i < num.size()-2; i++)
        {
            total = 1;
            for(int j = i+1; j < num.size()-1; j++)
            {
                if(num.get(i) == num.get(j))
                {
                    total++;
                }
            }
            if(total == 4)
                return true;
        }
        return false;
    }
    public Card getCard1()
    {
        return hand.get(0);
    }
    public Card getCard2()
    {
        return hand.get(1);
    }
    public void addFlop(Card card)
    {
        flop.add(card);
    }
    public void setUp()
    {
        flop.add(hand.get(0));
        flop.add(hand.get(1));
        num.add(flop.get(0).number);
        num.add(flop.get(1).number);
        num.add(flop.get(2).number);
        num.add(flop.get(3).number);
        num.add(flop.get(4).number);
        num.add(flop.get(5).number);
        num.add(flop.get(6).number);
        type.add(flop.get(0).suit);
        type.add(flop.get(1).suit);
        type.add(flop.get(2).suit);
        type.add(flop.get(3).suit);
        type.add(flop.get(4).suit);
        type.add(flop.get(5).suit);
        type.add(flop.get(6).suit);
        Collections.sort(num);
    }
    public String toString()
    {
        return ""+hand;
    }
}
