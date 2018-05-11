//Ian Rokas 11A - Final Project Code

//import statements
import java.net.URL;
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
    Deck deck = new Deck();
    Flop flop = new Flop();
    Image dbImage; //image variable for double buffer
    Graphics dbg;//double buffer
    Image table, background, cardBack, checkButton, foldButton, betButton, callButton, triangle, line, dealer, big, little, chip, cardTitle;
    Hand hand = new Hand();
    Hand hand2 = new Hand();
    Hand hand3 = new Hand();
    Hand hand4 = new Hand();
    ArrayList<Image> drawCards = new ArrayList<Image>();
    private boolean flip, flip2, flip3, bet, check, fold, call, turn, round1, round2 = true, round3 = true, round4 = true, roundOver, reveal;
    private int flipCount = 0;
    private int betX = 600, newBet = 0;
    private int playerTurn = 0;
    private String winner = "";
    private int[] price = {0, 5, 10, 0};
    int dealerLocX = 600, dealerLocY = 550;
    int littleLocX = 300, littleLocY = 400;
    int bigLocX = 580, bigLocY = 200;
    int emptyX = 1250, emptyY = 350;
    private boolean title = true;
    //Image cards, cards1;

    //initiation method
    public void init()
    {
        turn = true;
        table = getImage(this.getClass().getResource("table.png"));
        background = getImage(this.getClass().getResource("background.png"));
        cardBack = getImage(this.getClass().getResource("cardback.jpg"));
        betButton = getImage(this.getClass().getResource("bet.png"));
        foldButton = getImage(this.getClass().getResource("fold.png"));
        checkButton = getImage(this.getClass().getResource("check.png"));
        callButton = getImage(this.getClass().getResource("call.png"));
        triangle = getImage(this.getClass().getResource("triangle.png"));
        line = getImage(this.getClass().getResource("line.png"));
        dealer = getImage(this.getClass().getResource("dealer.png"));
        big = getImage(this.getClass().getResource("big.png"));
        little = getImage(this.getClass().getResource("little.png"));
        chip = getImage(this.getClass().getResource("chip.png"));
        cardTitle = getImage(this.getClass().getResource("title.png"));
        Font font = new Font("TimesRomann", Font.BOLD, 26);
        setFont(font);
        addMouseListener(this);//starting mouse listener
        deck.shuffle();
        deal();
    }

    //paint method
    public void paint(Graphics g)
    {
        //must redo later
        g.drawImage(background,0,0,this);
        if(!title) {
            g.drawImage(table, 0, 0, this);
            g.drawImage(dealer, dealerLocX, dealerLocY, this);
            g.drawImage(little, littleLocX, littleLocY, this);
            g.drawImage(chip, 1400, 500, this);
            g.drawString("$" + price[3], 1400, 500);
            g.drawImage(chip, 200, 500, this);
            g.drawString("$" + price[1], 200, 500);
            g.drawImage(chip, 900, 600, this);
            g.drawString("$" + price[0], 900, 600);
            g.drawImage(chip, 900, 150, this);
            g.drawString("$" + price[2], 900, 150);
            g.drawImage(big, bigLocX, bigLocY, this);
            if (turn) {
                g.drawImage(betButton, 1230, 700, this);
                if (!bet) {
                    g.drawImage(checkButton, 430, 700, this);
                    g.drawImage(foldButton, 830, 700, this);
                    g.drawImage(callButton, 30, 700, this);
                } else {
                    g.drawImage(triangle, betX, 760, this);
                    g.drawImage(line, 500, 760, this);
                    g.drawString("$" + (betX - 520), betX, 795);
                }
            }
            if(!fold) {
                g.drawImage(drawCards.get(0), 700, 550, this);
                g.drawImage(drawCards.get(1), 750, 550, this);
            }
            g.drawImage(cardBack, 1000, 310, this);
            if (reveal) {
                g.drawImage(drawCards.get(2), 1350, 325, this);
                g.drawImage(drawCards.get(3), 1400, 325, this);
                g.drawImage(drawCards.get(4), 150, 325, this);
                g.drawImage(drawCards.get(5), 200, 325, this);
                g.drawImage(drawCards.get(6), 700, 110, this);
                g.drawImage(drawCards.get(7), 750, 110, this);
                g.drawString(printWinner(), 500, 500);
            } else {
                g.drawImage(cardBack, 1350, 325, this);
                g.drawImage(cardBack, 1400, 325, this);
                g.drawImage(cardBack, 150, 325, this);
                g.drawImage(cardBack, 200, 325, this);
                g.drawImage(cardBack, 700, 110, this);
                g.drawImage(cardBack, 750, 110, this);
            }
            if (flip) {
                g.drawImage(drawCards.get(8), 890, 310, this);
                g.drawImage(drawCards.get(9), 790, 310, this);
                g.drawImage(drawCards.get(10), 690, 310, this);
            }
            if (flip2) {
                g.drawImage(drawCards.get(11), 590, 310, this);
            }
            if (flip3) {
                g.drawImage(drawCards.get(12), 490, 310, this);
            }
        }
        else
        {
            g.drawImage(cardTitle, 400,100,this);
        }
    }

    public void update(Graphics g)//double buffer
    {
        dbImage = createImage(1600, 800);
        dbg = dbImage.getGraphics();
        paint(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    // mouseClicked method
    public void mouseClicked(MouseEvent e) {

        x = e.getX();
        y = e.getY();
        if(title)
            title = false;
        if(x>520&&x<1060&&bet)
            betX = x-50;
        if(x < 1551 && x > 1244 && y < 800 && y > 720 && !bet)
        {
            bet = true;
        }
        else if(x < 1551 && x > 1244 && y < 800 && y > 720 && bet)
        {
            newBet = betX-520;
            roundOver = true;
        }
        else if(x < 1150 && x > 800 && y < 800 && y > 720 && !bet)
        {
            fold = true;
            reveal = true;
        }
        else if(x < 750 && x > 445 && y < 800 && y > 720)
        {
            check = true;
        }
        else if(x < 350 && x > 40 && y < 800 && y > 720)
        {
            call = true;
        }
        round1();
        round2();
        round3();
        round4();
        System.out.println(x + " " + y);
        repaint();
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
    public void deal()
    {
        hand.genHand(deck.getDeck());
        hand2.genHand(deck.getDeck());
        hand3.genHand(deck.getDeck());
        hand4.genHand(deck.getDeck());
        flop.genFlop3(deck.getDeck());
        flop.genFlopAfter(deck.getDeck());
        flop.genFlopAfter(deck.getDeck());
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand.getCard1()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand.getCard2()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand2.getCard1()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand2.getCard2()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand3.getCard1()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand3.getCard2()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand4.getCard1()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(hand4.getCard2()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(flop.getCard1()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(flop.getCard2()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(flop.getCard3()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(flop.getCard4()))));
        drawCards.add(getImage(this.getClass().getResource(drawCard(flop.getCard5()))));
        hand.addFlop(flop.getCard1());
        hand.addFlop(flop.getCard2());
        hand.addFlop(flop.getCard3());
        hand.addFlop(flop.getCard4());
        hand.addFlop(flop.getCard5());
        hand2.addFlop(flop.getCard1());
        hand2.addFlop(flop.getCard2());
        hand2.addFlop(flop.getCard3());
        hand2.addFlop(flop.getCard4());
        hand2.addFlop(flop.getCard5());
        hand3.addFlop(flop.getCard1());
        hand3.addFlop(flop.getCard2());
        hand3.addFlop(flop.getCard3());
        hand3.addFlop(flop.getCard4());
        hand3.addFlop(flop.getCard5());
        hand4.addFlop(flop.getCard1());
        hand4.addFlop(flop.getCard2());
        hand4.addFlop(flop.getCard3());
        hand4.addFlop(flop.getCard4());
        hand4.addFlop(flop.getCard5());
    }
    public void round1() {
        if(roundOver && flipCount == 0)
        {
            flip = true;
            round2 = false;
            roundOver = false;
            flipCount++;
        }
        if (!round1)
            if (turn) {
                if (check) {
                    playerTurn++;
                    round1 = true;
                } else if (fold) {
                    playerTurn++;
                    round1 = true;
                } else if (bet) {
                    price[0] = newBet;
                    playerTurn++;
                    round1 = true;
                    bet = false;
                } else if (call) {
                    price[0] = price[getMaxIndex()];
                    playerTurn++;
                    round1 = true;
                }
            }
    }
    public void round2()
    {
        if(roundOver && flipCount == 1) {
            flip2 = true;
            round3 = false;
            roundOver = false;
            flipCount++;
        }
        if (!round2)
            if (turn) {
                if (check) {
                    playerTurn++;
                    round2 = true;
                } else if (fold) {
                    playerTurn++;
                    round2 = true;
                } else if (bet) {
                    price[0] = newBet;
                    playerTurn++;
                    bet = false;
                    round2 = true;
                } else if (call) {
                    price[0] = price[getMaxIndex()];
                    playerTurn++;
                    round2 = true;
                }
            }
    }
    public void round3()
    {
        if(roundOver && flipCount == 2) {
            flip3 = true;
            round4 = false;
            roundOver = false;
            flipCount++;
        }
        if (!round3)
            if (turn) {
                if (check) {
                    playerTurn++;
                    round3 = true;
                } else if (fold) {
                    playerTurn++;
                    round3 = true;
                } else if (bet) {
                    price[0] = newBet;
                    playerTurn++;
                    round3 = true;
                    bet = false;
                } else if (call) {
                    price[0] = price[getMaxIndex()];
                    playerTurn++;
                    round3 = true;
                }
            }
    }
    public void round4()
    {
        if(roundOver && flipCount == 3)
        {
            reveal = true;
            int tempX = dealerLocX;
            int tempY = dealerLocY;
            dealerLocX = littleLocX;
            dealerLocY = littleLocY;
            littleLocX = bigLocX;
            littleLocY = bigLocY;
            bigLocX = emptyX;
            bigLocY = emptyY;
            emptyX = tempX;
            emptyY = tempY;
        }
        if (!round4)
            if (turn) {
                if (check) {
                    playerTurn++;
                    round4 = true;
                } else if (fold) {
                    playerTurn++;
                    round4 = true;
                } else if (bet) {
                    price[0] = newBet;
                    playerTurn++;
                    round4 = true;
                    bet = false;
                } else if (call) {
                    price[0] = price[getMaxIndex()];
                    playerTurn++;
                    round4 = true;
                }
            }
    }
    public int getMaxIndex()
    {
        int max = price[0];
        int index = 0;
        for(int i = 1; i < price.length; i++)
        {
            if(price[i] > max)
            {
                max = price[i];
                index = i;
            }
        }
        return index;
    }
    public String printWinner()
    {
        if(hand.compareTo(hand2)>=0)
        {
            if(hand.compareTo(hand3)>=0)
            {
                if(hand.compareTo(hand4)>=0)
                    winner = "You Win!";
                else
                    winner = "Player 4 Wins!";
            }
            else
            {
                if(hand3.compareTo(hand4)>=0)
                    winner = "Player 3 Wins!";
                else
                    winner = "Player 4 Wins!";
            }
        }
        else
        {
            if(hand2.compareTo(hand3)>=0)
            {
                if(hand2.compareTo(hand4)>=0)
                    winner = "You Win!";
                else
                    winner = "Player 4 Wins!";
            }
            else
            {
                if(hand3.compareTo(hand4)>=0)
                    winner = "Player 3 Wins!";
                else {
                    winner = "Player 4 Wins!";
                }
            }
        }
        return winner;
    }
    public String drawCard(Card card) {
        String name = "";
        name += card.getSuit();
        name += card.getNumber();
        name += ".jpg";
        return name;
    }
}