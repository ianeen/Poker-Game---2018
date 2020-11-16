import java.util.ArrayList;
import java.util.Collections;
//Hand class
public class Hand
{
    //ArrayLists used to identify the possible hands they have
    ArrayList<Card> hand = new ArrayList<>();
    ArrayList<Card> flop = new ArrayList<>();
    ArrayList<Integer> num = new ArrayList<>();
    ArrayList<Character> type = new ArrayList<>();
    ArrayList<Integer> best = new ArrayList<>();
    int kind3 = 0;
    int bestFlush = 0;
    int bestStraight = 0;
    int bestKindOf2 = 0;
    int bestTwoPair1 = 0;
    int bestTwoPair2 = 0;
    int bestFullHousePair = 0;
    int bestFullHouseTrio = 0;
    int bestKindOf4 = 0;
    public Hand()
    {
    }
    //generates a hand based on 2 cards
    public void genHand(ArrayList<Card> deck)
    {
        for(int i = 0; i < 2; i++)
        {
            hand.add(deck.get(0));
            deck.remove(0);
        }
    }
    //compares two hand types
    public int compareTo(Hand hand) {
        return handType() - hand.handType();
    }
    //identifies what hand they have
    public int handType()
    {
        setUp();
        if(isStraight() && isFlush())
            return 10000+bestStraight;
        else if(isKindOf4())
            return 9000+bestKindOf4;
        else if(fullHouse())
            return 8000 + bestFullHouseTrio * 20 + bestFullHousePair;
        else if(isFlush())
            return 7000+bestFlush;
        else if(isStraight())
            return 6000+bestStraight;
        else if(isKindOf3())
            return 5000+kind3;
        else if(twoPair())
            return 4000+bestTwoPair1 *20+bestTwoPair2;
        else if(isKindOf2())
            return 3000+bestKindOf2;
        else
            return 2000+findTopNumCard(num, 5);
    }
    //decides if they have a flush
    public boolean isFlush()
    {
        char finals = ' ';
        int totals = 0;
        int totalh = 0;
        int totalc = 0;
        int totald = 0;
        for(int i = 0; i < 7; i++)
        {
            if(type.get(i) == 'C')
                totalc++;
            else if(type.get(i) == 'S')
                totals++;
            else if(type.get(i) == 'H')
                totalh++;
            else if(type.get(i) == 'D')
                totald++;
            if(totals >= 5 || totalc >= 5 || totalh >= 5 || totald >= 5)
            {
                if(totals >= 5)
                    finals = 'S';
                else if(totalc >=5)
                    finals = 'C';
                else if(totalh >=5)
                    finals = 'H';
                else if(totald >=5)
                    finals = 'D';
                for(int j = 0; j < flop.size(); j++)
                {
                    if(flop.get(j).getSuit()==finals)
                    {
                        best.add(flop.get(j).getNumber());
                    }
                }
                bestFlush = findTopCard(best);
                best.clear();
                return true;
            }
        }
        return false;
    }
    //decides if they have a straight
    public boolean isStraight()
    {
        ArrayList<Integer> num2 = (ArrayList<Integer>)num.clone();
        boolean straight = false;
        for(int i = 0; i < num2.size()-1; i++)
        {

            for(int j = i+1; j < num2.size(); j++)
            {
                if(num2.get(i) == num2.get(j))
                {
                    num2.remove(j);
                    j--;
                }
            }
        }
        if(num2.size()>=5) {
            for (int i = num2.size()-1; i >= 4; i--) {
                straight = true;
                for (int j = i; j >= i - 3; j--) {
                    if (num2.get(j) - num2.get(j-1) != 1) {
                        straight = false;
                        break;
                    }
                }
                if (straight) {
                    bestStraight = num2.get(i);
                    break;
                }
            }
        }
        return straight;
    }
    //decides if they have 2 of a kind
    public boolean isKindOf2()
    {

        int pair = 0;
        int total = 1;
        for(int i = num.size()-1; i > 0; i--)
        {
            total = 1;
            for(int j = i-1; j >= 0; j--)
            {
                if(num.get(i) == num.get(j))
                {
                    pair = num.get(i);
                    total++;
                }
            }
            if(total == 2) {
                bestKindOf2 = pair;
                return true;
            }
        }
        return false;
    }
    public boolean twoPair()
    {
        int total = 1;
        int pair = 0;
        int pairNum = 0;
        for(int i = num.size()-1; i > 0; i--)
        {
            if(num.get(i)!=pair) {
                total = 1;
                for (int j = i-1;j >= 0; j--) {
                    if (num.get(i) == num.get(j)) {
                        total++;
                        pair = num.get(i);
                    }
                }
                if (total == 2) {
                    pairNum++;
                    if(pairNum == 1)
                    {
                        bestTwoPair1 = pair;
                    }
                    if(pairNum >= 2)
                    {
                        bestTwoPair2 = pair;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean fullHouse()
    {
        int pair = 0;
        if(isKindOf3())
        {
            int total = 1;
            for(int i = num.size()-1; i > 0; i--)
            {
                total = 1;
                for(int j = i-1;j >= 0; j--)
                {
                    if(num.get(i) == num.get(j))
                    {
                        pair = num.get(i);
                        total++;
                    }
                }
                if(total == 2 && pair != kind3)
                {
                    bestFullHousePair = pair;
                    bestFullHouseTrio = kind3;
                    return true;
                }

            }
        }
        return false;
    }
    //decides if they have 3 of a kind
    public boolean isKindOf3()
    {
        int kind = 0;
        int total = 1;
        for(int i = num.size()-1; i > 0; i--)
        {
            int checkRemove = 0;
            total = 1;
            for(int j = i-1; j >= 0; j--)
            {
                if(num.get(i) == num.get(j))
                {
                    kind = num.get(i);
                    total++;
                    checkRemove++;
                }
            }
            if(total == 3) {
                kind3 = kind;
                return true;
            }
        }
        return false;
    }
    //decides if they have 4 of a kind
    public boolean isKindOf4()
    {
        int pair = 0;
        int total = 1;
        for(int i = num.size()-1; i > 0; i--)
        {
            int checkRemove = 0;
            total = 1;
            for(int j = i-1;j >= 0; j--)
            {
                if(num.get(i) == num.get(j))
                {
                    pair = num.get(i);
                    total++;
                }
            }
            if(total == 4) {
                bestKindOf4 = pair;
                return true;
            }
        }
        return false;
    }
    //accessors and mutator methods
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
    //adds up all the possible cards they can use to make their hands
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
    public int findTopCard(ArrayList<Integer> list)
    {
        int top = list.get(0);
        int topIndex = 0;
        for(int i = 1; i < list.size(); i++)
        {
            if(list.get(i) > top)
            {
                top = list.get(i);
                topIndex = i;
            }
        }
        return top;
    }
    public int findTopNumCard(ArrayList<Integer> list, int nums) {
        ArrayList<Integer> best = new ArrayList<>();
        int total = 0;
        for(int k = 0; k < nums; k++) {
            int top = list.get(0);
            int topIndex = 0;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) > top) {
                    top = list.get(i);
                    topIndex = i;
                }
            }
            best.add(list.get(topIndex));
            list.remove(topIndex);
        }
        for(int i = 0; i < best.size(); i++)
        {
            total+=best.get(i);
        }
        return total;
    }
    public ArrayList<Integer> getNum()
    {
        return num;
    }
    public int tieCase(int posGo)
    {
        return num.size()-(posGo+1);
    }
    public String toString()
    {
        return ""+hand;
    }
}
