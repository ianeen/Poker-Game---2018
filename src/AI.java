//This is my AI class
public class AI
{
    private Main main = new Main();
    private boolean call;
    private boolean bet;
    private boolean fold;
    private int betNum;
    private boolean turn = false;
    private int AINumber;
    private long time = 0;
    private boolean allIn = false;
    public AI(int num) //AI constuctor
    {
        AINumber = num;
    }

    public void makeMove() //AI decides what move to make
    {
        int rand = genRandom(0, 20);
        if (rand <= 20 && rand >= 5)
        {
            call = true;
        }
        else if (rand <=4 && rand>=1)
        {
            boolean working = false;
            bet = true;
            while(!working) {
                rand = genRandom(1, 5);
                betNum = rand * main.betMultiple;
                if(betNum>=main.betMultiple*2 && main.balance.get(AINumber)-betNum>=0 && betNum < main.balance.get(main.findMinAmount()))
                {
                    working = true;
                    main.betMultiple = betNum;
                }
                if(rand == 5)
                {
                    bet = false;
                    call = true;
                    working = true;
                }

            }
        }
        else
        {
            fold = true;
        }
        delay();
        round();
    }
    public void setTurn(boolean t)
    {
        turn = t;
    }
    public int genRandom(int start, int end) //Generates a random number between the two inputs (inclusive)
    {
        return (int)((end-start+1)*Math.random() + start);
    }
    public void round() {
        if (turn && !fold && !allIn) {
            if (bet) {
                main.price.set(AINumber, main.price.get(AINumber)+betNum);
                main.balance.set(AINumber, main.balance.get(AINumber)-betNum);
                betNum = 0;
                main.playerTurn++;
                bet = false;
                if(main.balance.get(AINumber)<=0)
                    allIn = true;
            } else if (call) {
                main.balance.set(AINumber, main.balance.get(AINumber)-(main.price.get(main.findMaxBet())-main.price.get(AINumber)));
                main.price.set(AINumber, main.price.get(main.findMaxBet()));
                main.playerTurn++;
                if(main.balance.get(AINumber)<0)
                    main.balance.set(AINumber, 0);
                call = false;
                if(main.balance.get(AINumber)<=0)
                    allIn = true;
            }
        }
        else if(turn && fold)
        {
            main.playerTurn++;
        }
        else if(turn && allIn)
        {
            main.playerTurn++;
        }
    }
    public boolean getFold()
    {
        return fold;
    }
    public void delay()
    {
        int rand = genRandom(500, 2000);
        long time = System.nanoTime();
        long elapsed = (System.nanoTime()-time)/1000000;
        while (elapsed<=rand)
        {
            elapsed = (System.nanoTime()-time)/1000000;
        }
    }
}
