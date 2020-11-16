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
    //Variables
    int x = 0;//mouseX
    int y = 0;//mouseY
    Deck deck = new Deck();//deck object
    Flop flop = new Flop();//flop object
    Image dbImage; //image variable for double buffer
    Graphics dbg;//double buffer
    Image table, background, cardBack, checkButton, foldButton, betButton, callButton, arrows, dealer, big, little, chip, cardTitle, all, back;
    Hand hand = new Hand();//Hand objetcs
    Hand hand2 = new Hand();
    Hand hand3 = new Hand();
    Hand hand4 = new Hand();
    File clap, shuffle, song, woah, buyBack;
    ArrayList<Image> drawCards = new ArrayList<Image>();//Draw cards list
    private boolean flip, flip2, flip3, bet, check, fold, call, turn, round1, round2 = true, round3 = true, round4 = true, roundOver, reveal;
    private int flipCount = 0;//counts the number of flips that have happened
    private int betX = 20, newBet = 0;//the amount you have bet
    private String winner = "";//a winner string
    public static ArrayList<Integer> price = new ArrayList<>((Arrays.asList(0, 10, 20, 0)));//a price arrray
    public static ArrayList<Integer> balance = new ArrayList<>((Arrays.asList(1000, 990, 980, 1000)));//an array of the player's current balance
    int dealerLocX = 600, dealerLocY = 550;//location of all the chips
    ArrayList<AI> player = new ArrayList<AI>();
    int littleLocX = 300, littleLocY = 400;
    int bigLocX = 580, bigLocY = 200;
    int emptyX = 1250, emptyY = 350;
    public static int bigBlind = 2;
    int littleBlind = 1;
    public static int playerTurn = bigBlind + 1;//variable used to decide whose turn it is
    public static int betMultiple = 10;
    boolean goneOnce = false;
    Font font;
    Font font2;
    boolean printWin = false;
    boolean allIn = false;
    private boolean title = true;//boolean used to decide what screen the user is on

    //initiation method
    public void init()
    {
        player.add(new AI(1));
        player.add(new AI(2));
        player.add(new AI(3));
        turn = false;
        table = getImage(this.getClass().getResource("table.png"));
        background = getImage(this.getClass().getResource("background.png"));
        cardBack = getImage(this.getClass().getResource("cardback.jpg"));
        betButton = getImage(this.getClass().getResource("bet.png"));
        foldButton = getImage(this.getClass().getResource("fold.png"));
        checkButton = getImage(this.getClass().getResource("check.png"));
        callButton = getImage(this.getClass().getResource("call.png"));
        arrows = getImage(this.getClass().getResource("arrows.png"));
        dealer = getImage(this.getClass().getResource("dealer.png"));
        big = getImage(this.getClass().getResource("big.png"));
        little = getImage(this.getClass().getResource("little.png"));
        chip = getImage(this.getClass().getResource("chip.png"));
        cardTitle = getImage(this.getClass().getResource("title.png"));
        all = getImage(this.getClass().getResource("all.png"));
        back = getImage(this.getClass().getResource("back.png"));
        clap = new File("win.WAV");
        shuffle = new File("shuffle.WAV");
        song = new File("song.WAV");
        woah = new File("woah.WAV");
        buyBack = new File("buyBack.WAV");
        playSound(song);
        font = new Font("TimesRomann", Font.BOLD, 26);
        font2 = new Font("TimesRomann", Font.BOLD, 100);
        setFont(font);
        addMouseListener(this);//starting mouse listener
        deck.shuffle();
        deal();
    }

    //paint method
    public void paint(Graphics g)
    {
        //draws the field, the cards, the chips and everything else
        g.drawImage(background,0,0,this);
        g.setColor(Color.white);
        if(!title) {
            System.out.println("Painted");
            g.drawImage(table, 0, 0, this);
            g.drawImage(dealer, dealerLocX, dealerLocY, this);
            g.drawImage(little, littleLocX, littleLocY, this);
            g.drawImage(chip, 1400, 500, this);
            g.drawString("$" + price.get(3), 1400, 500);
            g.drawString("Balance: $" + balance.get(3), 1300, 300);
            g.drawImage(chip, 200, 500, this);
            g.drawString("$" + price.get(1), 200, 500);
            g.drawString("Balance: $" + balance.get(1), 100, 300);
            g.drawImage(chip, 900, 600, this);
            g.drawString("$" + price.get(0), 900, 600);
            g.drawString("Balance: $" + balance.get(0), 900, 700);
            g.drawImage(chip, 900, 150, this);
            g.drawString("$" + price.get(2), 900, 150);
            g.drawString("Balance: $" + balance.get(2), 900, 100);
            g.drawImage(big, bigLocX, bigLocY, this);
            if (turn) {
                g.drawImage(betButton, 1230, 700, this);
                if (!bet) {
                    g.drawImage(checkButton, 430, 700, this);
                    g.drawImage(foldButton, 830, 700, this);
                    g.drawImage(callButton, 30, 700, this);
                } else {
                    g.drawImage(arrows, 1300, 550, this);
                    g.drawString("$" + betX, 1200, 620);
                    g.drawImage(all,30, 700, this);
                    g.drawImage(back, 10, 10, this);
                }
            }
            if(!fold) {
                g.drawImage(drawCards.get(0), 700, 550, this);
                g.drawImage(drawCards.get(1), 750, 550, this);
            }
            g.drawImage(cardBack, 1100, 310, this);
            if (reveal) {
                if (!player.get(2).getFold())
                {
                    g.drawImage(drawCards.get(2), 1350, 325, this);
                    g.drawImage(drawCards.get(3), 1400, 325, this);
                }
                if (!player.get(0).getFold()) {
                    g.drawImage(drawCards.get(4), 150, 325, this);
                    g.drawImage(drawCards.get(5), 200, 325, this);
                }
                if (!player.get(1).getFold()) {
                    g.drawImage(drawCards.get(6), 700, 110, this);
                    g.drawImage(drawCards.get(7), 750, 110, this);
                }
                g.setColor(Color.white);
                g.setFont(font2);
                g.drawString(printWinner(), 480, 540);
                g.setFont(font);
                g.setColor(Color.black);
            } else {
                if (!player.get(2).getFold()) {
                    g.drawImage(cardBack, 1350, 325, this);
                    g.drawImage(cardBack, 1400, 325, this);
                }
                if (!player.get(0).getFold())
                {
                    g.drawImage(cardBack, 150, 325, this);
                    g.drawImage(cardBack, 200, 325, this);
                }
                if(!player.get(1).getFold())
                {
                    g.drawImage(cardBack, 700, 110, this);
                    g.drawImage(cardBack, 750, 110, this);
                }
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
    public void mouseClicked(MouseEvent e)
    {
        //decides what button has been clicked
        x = e.getX();
        y = e.getY();
        if(title) {
            playSound(shuffle);
            title = false;
        }
        if(x>1300&&x<1400&&y>550&&y<625&&bet&&betX<balance.get(0)&&betX<balance.get(findMinAmount()))
            betX+=10;
        else if(x>1300&&x<1400&&y>625&&y<700&&betX>20)
            betX-=10;
        else if(x < 280 && x > 30 && y < 800 && y > 720 && bet && balance.get(findMinAmount()) >= balance.get(0)) {
            betX = balance.get(0);
        }
        else if(x < 110 && x > 10 && y < 110 && y > 10 && bet)
            bet = false;
        if(x < 1551 && x > 1244 && y < 800 && y > 720 && !bet)
        {
            bet = true;
        }
        else if(x < 1551 && x > 1244 && y < 800 && y > 720 && bet && betX >= betMultiple*2 && balance.get(0)-(betX)>=0)
        {
            newBet = (betX);
            betX = 20;
            betMultiple = newBet;
            goneOnce = true;
            if(balance.get(0) - newBet <= 0) {
                playSound(woah);
                allIn = true;
            }
        }
        else if(x < 1150 && x > 800 && y < 800 && y > 720 && !bet)
        {
            fold = true;
            goneOnce = true;
        }
        else if(x < 750 && x > 445 && y < 800 && y > 720 && !bet)
        {
            if(price.get(0)==price.get(findMaxBet())) {
                check = true;
                goneOnce = true;
            }
        }
        else if(x < 350 && x > 40 && y < 800 && y > 720 && !bet)
        {
            call = true;
            goneOnce = true;
        }
        if(roundOver && flipCount == 3)
        {
            roundOver = false;
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
            reveal = false;
            flipCount = 0;
            bigBlind++;
            if(bigBlind == 4)
                bigBlind = 0;
            littleBlind = bigBlind - 1;
            if(littleBlind == -1)
            {
                littleBlind = 3;
            }
            playerTurn = bigBlind+1;
            if(playerTurn==4)
            {
                playerTurn = 0;
            }
            flip = false;
            flip2 = false;
            flip3 = false;
            bet = false;
            newBet = 0;
            call = false;
            fold = false;
            check = false;
            player.clear();
            player.add(new AI(1));
            player.add(new AI(2));
            player.add(new AI(3));
            for(int i = 0; i < balance.size(); i++)
            {
                if(balance.get(i)==0)
                {
                    balance.set(i, 1000);
                    playSound(buyBack);
                }
            }
            price.set(bigBlind, 20);
            price.set(littleBlind, 10);
            balance.set(bigBlind,balance.get(bigBlind)-20);
            balance.set(littleBlind,balance.get(littleBlind)-10);
            hand = new Hand();
            hand2 = new Hand();
            hand3 = new Hand();
            hand4 = new Hand();
            flop = new Flop();
            deck = new Deck();
            deck.shuffle();
            playSound(shuffle);
            drawCards.clear();
            deal();
            round1 = false;
            round2 = true;
            round3 = true;
            round4 = true;
            printWin = false;
            allIn = false;
            betMultiple = 10;

        }
        if (allIn)
        {
            while(!reveal)
            {
                goneOnce = true;
                round1();
                round2();
                round3();
                round4();
            }
        }
        else if(newBet != 0 || call || check || !turn) {
            round1();
            round2();
            round3();
            round4();
        }
        else if(fold)
        {
            while(!reveal)
            {

                goneOnce = true;
                round1();
                round2();
                round3();
                round4();
            }
        }
        else if(fold)
        {
            while(!reveal)
            {

                goneOnce = true;
                round1();
                round2();
                round3();
                round4();
            }
        }

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
    //creates the different card objects
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
    //what happens in round1
    public void round1() {
        if (!round1&&foldCount()<3)
        {
            turnDecider();
            if (turn && !fold && !allIn) {
                if (check) {
                    playerTurn++;
                    check = false;
                }
                else if (bet) {
                    price.set(0, price.get(0)+newBet);
                    balance.set(0, balance.get(0)-newBet);
                    playerTurn++;
                    newBet = 0;
                    bet = false;
                    if(balance.get(0)<=0)
                        allIn = true;
                } else if (call) {
                    balance.set(0, balance.get(0)-(price.get(findMaxBet())-price.get(0)));
                    price.set(0, price.get(findMaxBet()));
                    playerTurn++;
                    call = false;
                    if(balance.get(0)<=0)
                        allIn = true;
                }
                System.out.println(price);

            }
            else if(turn && fold)
            {
                playerTurn++;
            }
            else if(turn && allIn)
            {
                playerTurn++;
            }
            for(int i = 0; i < 3; i++)
            {
                turnDecider();
                repaint();
                player.get(i).makeMove();
                repaint();
                turnDecider();
            }
            boolean allEqual;
            int i = 0;
            if(fold)
                i = 1;
            allEqual = true;
            for(int j = i+1; j < 4; j++){
                if(price.get(i)!=price.get(j) && !player.get(j-1).getFold())
                {
                    allEqual = false;
                    break;
                }
            }
            if(allEqual && goneOnce)
            {
                round1 = true;
                goneOnce = false;
            }
            if(round1)
            {
                roundOver = true;
            }
            if(roundOver && flipCount == 0)
            {
                flip = true;
                round2 = false;
                roundOver = false;
                flipCount++;
                betMultiple = 10;
                playerTurn = littleBlind;
            }
        }
        else if(foldCount()>=3)
        {
            reveal = true;
        }
        else if(foldCount()>=3)
        {
            reveal = true;
        }
    }
    //what happens in round2
    public void round2()
    {
        if (!round2&&foldCount()<3) {
            turnDecider();
            if (turn && !fold && !allIn) {
                if (check) {
                    playerTurn++;
                    check = false;
                }
                else if (bet) {
                    price.set(0, price.get(0)+newBet);
                    balance.set(0, balance.get(0)-newBet);
                    newBet = 0;
                    playerTurn++;
                    bet = false;
                    if(balance.get(0)<=0)
                        allIn = true;
                } else if (call) {
                    balance.set(0, balance.get(0)-(price.get(findMaxBet())-price.get(0)));
                    price.set(0, price.get(findMaxBet()));
                    playerTurn++;
                    call = false;
                    if(balance.get(0)<=0)
                        allIn = true;
                }
            }
            else if(turn && fold)
            {
                playerTurn++;
            }
            else if(turn && allIn)
            {
                playerTurn++;
            }
            for (int i = 0; i < 3; i++) {
                turnDecider();
                player.get(i).makeMove();
                turnDecider();
            }
            boolean allEqual;
            int i = 0;
            if(fold)
                i = 1;
            allEqual = true;
            for(int j = i+1; j < 4; j++){
                if(price.get(i)!=price.get(j) && !player.get(j-1).getFold())
                {
                    allEqual = false;
                    break;
                }
            }
            if(allEqual && goneOnce)
            {
                round2 = true;
                goneOnce = false;
            }
            if(round2)
            {
                roundOver = true;
            }
            if(roundOver && flipCount == 1) {
                flip2 = true;
                round3 = false;
                roundOver = false;
                flipCount++;
                betMultiple = 10;
                playerTurn = littleBlind;
            }
        }
        else if(foldCount()>=3)
        {
            reveal = true;
        }
    }
    //what happens in round3
    public void round3()
    {
        if (!round3 && foldCount()<3) {
            turnDecider();
            if (turn && !fold) {
                if (check) {
                    playerTurn++;
                    check = false;
                }
                else if (bet) {
                    price.set(0, price.get(0)+newBet);
                    balance.set(0, balance.get(0)-newBet);
                    playerTurn++;
                    bet = false;
                    newBet = 0;
                    if(balance.get(0)<=0)
                        allIn = true;
                } else if (call) {
                    balance.set(0, balance.get(0)-(price.get(findMaxBet())-price.get(0)));
                    price.set(0, price.get(findMaxBet()));
                    playerTurn++;
                    call = false;
                    if(balance.get(0)<=0)
                        allIn = true;
                }
            }
            else if(turn && fold)
            {
                playerTurn++;
            }
            else if(turn && allIn && !allIn)
            {
                playerTurn++;
            }
            for (int i = 0; i < 3; i++) {
                turnDecider();
                player.get(i).makeMove();
                turnDecider();
            }
            boolean allEqual;
            int i = 0;
            if(fold)
                i = 1;
            allEqual = true;
            for(int j = i+1; j < 4; j++){
                if(price.get(i)!=price.get(j) && !player.get(j-1).getFold())
                {
                    allEqual = false;
                    break;
                }
            }
            if(allEqual && goneOnce)
            {
                round3 = true;
                goneOnce = false;
            }
            if(round3)
            {
                roundOver = true;
            }
            if(roundOver && flipCount == 2) {
                flip3 = true;
                round4 = false;
                roundOver = false;
                flipCount++;
                betMultiple = 10;
                playerTurn = littleBlind;
            }
        }
        else if(foldCount()>=3)
        {
            reveal = true;
        }
    }
    //what happens in round4
    public void round4()
    {
        if (!round4&&foldCount()<3) {
            turnDecider();
            if (turn&&!fold&&!allIn) {
                if (check) {
                    playerTurn++;
                    check = false;
                }
                else if (bet) {
                    price.set(0, price.get(0)+newBet);
                    balance.set(0, balance.get(0)-newBet);
                    playerTurn++;
                    bet = false;
                    newBet = 0;
                    if(balance.get(0)<=0)
                        allIn = true;
                } else if (call) {
                    balance.set(0, balance.get(0)-(price.get(findMaxBet())-price.get(0)));
                    price.set(0, price.get(findMaxBet()));
                    playerTurn++;
                    call = false;
                    if(balance.get(0)<=0)
                        allIn = true;
                }
            }
            else if(turn && fold)
            {
                playerTurn++;
            }
            else if(turn && allIn)
            {
                playerTurn++;
            }
            for(int i = 0; i < 3; i++)
            {
                turnDecider();
                player.get(i).makeMove();
                turnDecider();
            }
            boolean allEqual;
            int i = 0;
            if(fold)
                i = 1;
            allEqual = true;
            for(int j = i+1; j < 4; j++){
                if(price.get(i)!=price.get(j) && !player.get(j-1).getFold())
                {
                    allEqual = false;
                    break;
                }
            }
            if(allEqual && goneOnce)
            {
                round4 = true;
                goneOnce = false;
            }
            if(round4)
            {
                roundOver = true;
            }
            if(roundOver && flipCount == 3)
            {
                reveal = true;
            }
        }
        else if(foldCount()>=3)
        {
            reveal = true;
        }
    }
    //finds out who the winner is and prints it
    public String printWinner()
    {
        if(!printWin) {
            winner = "";
            ArrayList<Integer> winners = new ArrayList<>();
            boolean tie = false;
            int[] arr = new int[4];
            arr[0] = hand.handType();
            arr[1] = hand2.handType();
            arr[2] = hand3.handType();
            arr[3] = hand4.handType();
            Hand[] arr2 = new Hand[4];
            arr2[0] = hand;
            arr2[1] = hand2;
            arr2[2] = hand3;
            arr2[3] = hand4;
            int bestIndex = 0;
            if(fold)
                arr[0] = 0;
            if(player.get(2).getFold())
                arr[1] = 0;
            if(player.get(0).getFold())
                arr[2] = 0;
            if(player.get(1).getFold())
                arr[3] = 0;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > arr[bestIndex])
                {
                    bestIndex = i;
                    tie = false;
                }
                /*else if(arr[i]==arr[bestIndex])
                {
                    boolean win = false;
                    int count = 0;
                    while(!win)
                    {
                        if(count>6) {
                            win = true;
                            winners.add(i);
                            winners.add(bestIndex);
                            tie = true;
                        }
                        else if(arr2[i].tieCase(count)>arr2[bestIndex].tieCase(count)) {
                            bestIndex = i;
                            win = true;
                        }
                        else
                            count++;
                    }
                }*/
            }

            if (bestIndex == 0) {
                winner = "   You Win!";
                balance.set(0, balance.get(0)+price.get(0)+price.get(1)+price.get(2)+price.get(3));
                price.set(0, 0); price.set(1, 0); price.set(2, 0); price.set(3, 0);
                playSound(clap);
            } else if (bestIndex == 1) {
                winner = "Player 4 Wins!";
                balance.set(3, balance.get(3)+price.get(0)+price.get(1)+price.get(2)+price.get(3));
                price.set(0, 0); price.set(1, 0); price.set(2, 0); price.set(3, 0);
            } else if (bestIndex == 2) {
                winner = "Player 2 Wins!";
                balance.set(1, balance.get(1)+price.get(0)+price.get(1)+price.get(2)+price.get(3));
                price.set(0, 0); price.set(1, 0); price.set(2, 0); price.set(3, 0);
            } else if (bestIndex == 3)
            {
                winner = "Player 3 Wins!";
                balance.set(2, balance.get(2)+price.get(0)+price.get(1)+price.get(2)+price.get(3));
                price.set(0, 0); price.set(1, 0); price.set(2, 0); price.set(3, 0);
            }
            printWin = true;
        }

        return winner;
    }
    public int foldCount()
    {
        int count = 0;
        if(fold)
            count++;
        else if(player.get(0).getFold())
            count++;
        else if(player.get(1).getFold())
            count++;
        else if(player.get(2).getFold())
            count++;
        return count;
    }
    //returns a string to draw the cards
    public String drawCard(Card card) {
        String name = "";
        name += card.getSuit();
        name += card.getNumber();
        name += ".jpg";
        return name;
    }
    //finds the max bet a the table
    public int findMaxBet()
    {
        int bestIndex = 0;
        for(int i = 1; i < price.size(); i++)
        {
            if(price.get(i) > price.get(bestIndex))
            {
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    public int findMinAmount()
    {
        int bestIndex = 0;
        for(int i = 1; i < balance.size(); i++)
        {
            if(balance.get(i) < balance.get(bestIndex))
            {
                bestIndex = i;
            }
        }
        return bestIndex;
    }
    public void turnDecider()//decides who's turn it is
    {
        if(playerTurn == 0)
            turn = true;
        else
            turn = false;
        if(playerTurn == 1)
            player.get(0).setTurn(true);
        else
            player.get(0).setTurn(false);
        if(playerTurn == 2)
            player.get(1).setTurn(true);
        else
            player.get(1).setTurn(false);
        if(playerTurn == 3)
            player.get(2).setTurn(true);
        else
            player.get(2).setTurn(false);
        if(playerTurn == 4) {
            playerTurn = 0;
            turn = true;
        }
    }
    //playing sound method
    public void playSound(File Sound)
    {
        try
        {
            Clip clip = AudioSystem.getClip();//get clip
            clip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(""+Sound)));
            clip.start();//start clip
            if(Sound == song)//if clip is the song
                clip.loop(Clip.LOOP_CONTINUOUSLY);//loop continuously
        }
        catch(Exception e)
        {

        }
    }
}