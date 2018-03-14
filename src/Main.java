//Ian Rokas 10A - Final Project Code

//import statements
import java.util.Random;
import java.util.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//main class
public class TicTacToe extends Applet implements MouseListener
{
    //variable declaration
    int size = 3;//TicTacToe size
    int x = 0;//mouse click x
    int y = 0;//mouse click y
    int arr[][] = new int[100][100];//2d array of field
    boolean p1 = true; //boolean for knowing which player turn
    boolean p2 = false;//boolean for knowing which player turn
    boolean p1line = false;//player1 made a line
    boolean p2line = false;//player2 made a line
    boolean retry =false;//boolean for restart
    boolean start = false;//game has started boolean
    boolean gameDraw = false;//boolean to see if it is a draw
    boolean draw = false;//boolean so draw is not printed each if someone won
    boolean p2layer = false;//2 player mode selected
    boolean p1layer = false;//1 player mode selected
    boolean easy = false;// easy mode selected
    boolean hard = false;// hard mode selected
    boolean songPlaying = false;//song only plays once each loop
    boolean almostWin = false;//if opponennt is about to win
    boolean almostWin2 = false;//if auto is about to win
    boolean chooseSize = false;//choosing TicTacToe size
    boolean auto=false; //if auto is true
    boolean gone = false; //if auto has gone
    boolean retryb = false;//if retry button has been clicked
    int store1 = 0;//auto player placer
    int store2 = 0;//auto player placer
    int b=0;//box checker
    Image img;//java image
    Image img2;//python image
    int lineDistance = 0;//distance between each line (for resizing field)
    Image dbImage; //image variable for double buffer
    Graphics dbg;//double buffer
    File song;//song file for playing WAV files

    //initiation method
    public void init()
    {
        addMouseListener(this);//starting mouse listener
    }

    //paint method
    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);//setting color
        song = new File("song.WAV");//declaring song
        if(songPlaying == false)//stop song from playing over and over
        {
            playSound(song);//play song
            songPlaying = true;
        }
        Font font = new Font("Arial", Font.BOLD, 60);//setting font
        g.setFont(font);
        Image img3 = getImage(getCodeBase(), "background.png");//setting background (just black)
        if(start == true)//if game has started
        {
            lineDistance = 600/size;//set line distance for the size chosen
            g.drawImage(img3, 0, 0, this);//draw background
            for(int i = 1; i < size; i++)//draw lines for chosen size
            {
                g.fillRect(0, i * lineDistance, 600, 4);
                g.fillRect(i * lineDistance, 0, 4, 600);
            }
            //choosing image for chosen size (image won't always fit perfectly)
            if(size==3)
            {
                img = getImage(getCodeBase(), "python3.png");
                img2 = getImage(getCodeBase(), "java3.png");
            }
            else if(size==4)
            {
                img = getImage(getCodeBase(), "python4.png");
                img2 = getImage(getCodeBase(), "java4.png");
            }
            else if(size==5)
            {
                img = getImage(getCodeBase(), "python5.png");
                img2 = getImage(getCodeBase(), "java5.png");
            }
            else if(size==6)
            {
                img = getImage(getCodeBase(), "python6.png");
                img2 = getImage(getCodeBase(), "java6.png");
            }
            else if(size==7)
            {
                img = getImage(getCodeBase(), "python7.png");
                img2 = getImage(getCodeBase(), "java7.png");
            }
            else if(size==8)
            {
                img = getImage(getCodeBase(), "python8.png");
                img2 = getImage(getCodeBase(), "java8.png");
            }
            else
            {
                img = getImage(getCodeBase(), "python9.png");
                img2 = getImage(getCodeBase(), "java9.png");
            }
        }

        startScreen(g);//start screen method

        winCheck(g);//checking if winner method (before)

        auto();//autonomous method

        imageDrawer(g);//image drawing method

        winCheck(g);//checking if winner method (after)

        if(retry == true)//printing
        {
            g.setColor(Color.BLACK);
            g.fillRect(180, 170, 240, 60);
            g.setColor(Color.WHITE);
            g.drawString("Retry?", 210, 220);
        }
    }

    public void update(Graphics g)//double buffer
    {
        dbImage = createImage(600, 600);
        dbg = dbImage.getGraphics();
        paint(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    // mouseclicked method
    public void mouseClicked(MouseEvent e)
    {
        x = e.getX();//reading x and y
        y = e.getY();
        if(start == true)//if start is true
        {
            if(retry == true)// if retry is true (game has ended)
            {
                if(x>222&&x<408&&y>171&&y<220)//retry button is clicked
                {
                    retryb = true;//retry button is true
                }
                if(retryb ==true)
                {
                    retryb=false;//reset everything
                    retry = false;
                    draw = false;
                    for(int i = 0; i<size;i++)
                    {
                        for(int j = 0; j<size;j++)
                        {
                            arr[i][j]=0;
                        }
                    }
                }
            }
            else//if retry is not true
                clicking();//location clicking for player icons
        }

        startScreenClicking();//start screen button clicking

        repaint();//repainting
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

    //start screen clicking method
    public void startScreenClicking()
    {
        if(start == false)//if start is false (game has not started)
        {
            if(p1layer == true)//if 1 player is selected
            {
                if(chooseSize == false)//if size menu has not opened
                {
                    if (x > 200 && x< 360&&y< 140&&y>96)//if easy button is clicked
                    {
                        easy = true;//set autonomous to easy
                        chooseSize = true;//size must now be chosen
                        auto = true;//autonomous can start
                    }
                    else if (x > 220 && x< 360&&y> 450&&y<500)//if hard button is clicked
                    {
                        hard = true;//set autonomous to hard
                        chooseSize = true;//size must now be chosen
                        auto = true;//autonomous can start
                    }
                }
                else//if size menu is open
                {
                    if(x > 422 && x< 568&&y> 455&&y<500)//if done button is clicked
                    {
                        start = true;//start game
                        arr = new int[size][size];//set 2D array to selected size
                    }
                    else if(x > 170 && x < 285&&y > 235&& y< 292&&size<15)//if up arrow is clicked
                    {
                        size++;//increase size by 1
                    }
                    else if(x > 170 && x< 285&&y> 317&&y<367&&size>3)//if down arrow is clicked
                    {
                        size--;//decrease size by 1
                    }
                }
            }
            else if(p2layer == true)//if 2 player has been chosen
            {
                if(chooseSize == false)//if size menu has not been opened
                {
                    if (x < 200 && y < 200)//if python is clicked
                    {
                        p1=false;//set java's turn to false
                        p2=true;//set python's turn to true;
                        chooseSize = true;//size must now be chosen
                    }
                    else if (x > 400 && x < 600 && y < 200)//if java is clicked
                    {
                        p1=true;//do opposite
                        p2=false;
                        chooseSize = true;
                    }
                }
                else//if size menu is opened
                {
                    if(x > 422 && x< 568&&y> 455&&y<500)//same as before
                    {
                        start=true;
                        arr = new int[size][size];
                    }
                    else if(x > 170 && x < 285&&y > 235&& y< 292&&size<15)
                    {
                        size++;
                    }
                    else if(x > 170 && x< 285&&y> 317&&y<367&&size>3)
                    {
                        size--;
                    }
                }
            }
            else//if none have been selected yet
            {
                if (x > 200 && x< 433&&y< 140&&y>96)//if 1 player has been selected
                {
                    p1layer=true;
                }
                else if (x > 200 && x< 433&&y> 450&&y<500)//if 2 player has been selected
                {
                    p2layer=true;
                }
            }
        }
    }

    //player spot clicking method
    public void clicking()
    {
        gone = false;//set gone to false

        int pointx = x/(600/size);//calculation that gives spot clicked on TicTacToe map
        int pointy = y/(600/size);

        if(p1 == true && arr[pointy][pointx] == 0)//if its player 1's turn and the spot is free and they clicked there
        {
            arr[pointy][pointx]=1;//set that spot java
            gone = true;//they have gone
        }
        else if(p2==true && arr[pointy][pointx]==0&&auto==false)//if its player 2's turn and the spot is free and they clicked there
        {
            arr[pointy][pointx]=2;//set that spot python
            gone = true;
        }

        if(p1==true && gone == true)//if was player 1's turn and they went
        {
            p1=false;//switch turns
            p2=true;
            gone = false;
        }
        else if(p2==true && gone == true)//if was player 2's turn and they went
        {
            p1=true;//switch turns
            p2=false;
            gone = false;
        }

        gone = true;
    }

    //image draw method (for player icons)
    public void imageDrawer(Graphics g)
    {
        for(int i = 0; i < size; i++)//loop for one half of array
        {
            for(int j = 0; j < size; j++)//loop for other half
            {
                if(arr[i][j] == 1)//if spot is 1 (java)
                {
                    g.drawImage(img2, j * lineDistance, i * lineDistance, this);//draw java in that location
                }
                else if(arr[i][j] == 2)//if spot is 2 (python)
                {
                    g.drawImage(img, j * lineDistance, i * lineDistance, this);//draw python in that location
                }
            }
        }
    }

    //autonomous method
    public void auto()
    {
        Random rand = new Random();//declaring random
        if(auto == true)//if autonomous is true
        {
            if(p2 == true)//if its player 2's turn (autonomous)
            {
                if(easy == true&& retry == false)//if autonomous is set to easy
                {
                    gone = false;//set gone to false
                    while(gone == false)//while they have not gone
                    {
                        int x = rand.nextInt(size);//set random half of array
                        int y = rand.nextInt(size);//set other random half
                        if(arr[y][x] == 0)//if that spot if free
                        {
                            arr[y][x] = 2;//take it
                            gone = true;//they have gone
                        }
                    }
                    p2 = false;//switch turn
                    p1 = true;
                }
                if(hard == true && retry == false)//if autonomous is hard
                {
                    gone = false;//set gone to false
                    while(gone == false)//while gone is false
                    {
                        int x = rand.nextInt(size);//get random points in case nothing else works
                        int y = rand.nextInt(size);
                        if(checkToWinning2() == true)//if autonomous is 1 away from winning
                        {
                            arr[store2][store1]=2;//take that spot and win
                            almostWin2 = false;
                            gone = true;
                        }
                        else if(checkToWinning() == true)//if oppenent is 1 away from winning
                        {
                            arr[store2][store1] = 2;//take that spot
                            almostWin = false;
                            gone = true;
                        }
                        else if(arr[size/2][size/2] == 0)//if the middle if open
                        {
                            arr[size/2][size/2] = 2;//take that spot
                            gone = true;
                        }
                        else if(arr[size/2][size/2]==1&&arr[0][0]==0)//if the opponent has taken the middle and top left is open
                        {
                            arr[0][0] = 2;//take top left
                            gone = true;
                        }
                        else if(arr[y][x]==0)//else randomly place somewhere
                        {
                            arr[y][x] = 2;
                            gone = true;
                        }
                    }
                    p2 = false;
                    p1 = true;
                }
            }
        }
    }

    //start screen drawing method
    public void startScreen(Graphics g)
    {
        Image img3 = getImage(getCodeBase(), "startbackground.png");//set background
        Image img4 = getImage(getCodeBase(), "updown.png");//set up and down stick
        if(start == false)//if game is not started
        {
            img3 = getImage(getCodeBase(), "background.png");
            g.drawImage(img3, 0, 0, this);
            if(p2layer == true)//if 2 player is true
            {
                if(chooseSize == false)//if size menu is not open
                {
                    img = getImage(getCodeBase(), "python3.png");//set java and python
                    img2 = getImage(getCodeBase(), "java3.png");
                    g.drawString("Choose ", 190, 200);//ask player to choose their language
                    g.drawString("Your ", 190+40, 260);
                    g.drawString("Language! ", 190-35, 320);
                    g.drawImage(img, 0, 0, this);//draw java and python
                    g.drawImage(img2, 400, 0, this);
                }
                else//if size menu is open
                {
                    g.drawImage(img4, 100, 170, this);//draw up and down arrow
                    g.drawString(""+size, 320, 320);//draw current size
                    g.drawString("Done", 420, 500);
                    g.drawString("Size", 230, 150);
                }
            }
            else if(p1layer == true)//if 1 player has been selected
            {
                if(chooseSize == false)//if size menu is not open
                {
                    g.drawString("Easy ", 220,140);//draw difficulty screen
                    g.drawString("Hard ", 220,500);
                }
                else//if size menu is open
                {
                    g.drawImage(img4, 100, 170, this);//draw size screen
                    g.drawString(""+size, 320, 320);
                    g.drawString("Done", 420, 500);
                    g.drawString("Size", 230, 150);
                }
            }
            else
            {
                g.drawString("1 Player! ", 180, 140);//draw info
                g.drawString("2 Player! ", 180, 500);
                Font font2 = new Font("Arial", Font.BOLD, 100);
                g.setFont(font2);
                g.drawString("TicTacCode", 15, 320);
                Font font1 = new Font("Arial", Font.BOLD, 25);
                g.setFont(font1);
                g.drawString("Ian Rokas 10A", 5, 25);
            }
        }
    }

    //player winning method
    public void winCheck(Graphics g)
    {
        for(int k = 0 ; k < 2; k++)//horizontal and vertical check
        {
            for(int i = 0; i < size; i++)
            {
                p1line = true;//set java win line to true
                p2line = true;//set python win line to true
                for(int j = 0; j < size; j++)
                {
                    if(k == 0)//first loop through check horizontal
                    {
                        if(arr[i][j] != 1)//if java does not own the whole line
                        {
                            p1line = false;//set java line to false
                        }
                        if(arr[i][j] != 2)//if python does not own the whole line
                        {
                            p2line = false;//set python line to false
                        }
                    }
                    else//first loop through check verticle
                    {
                        if(arr[j][i] != 1)
                        {
                            p1line = false;
                        }
                        if(arr[j][i] != 2)
                        {
                            p2line = false;
                        }
                    }
                }

                winPrint(g);//print winner

            }
        }
        //repeated for other ways
        p1line = true;
        p2line = true;
        b = 0;
        for(int i = 0; i < size; i++)//diagonal check down right
        {
            if(arr[i][b] != 1)
            {
                p1line = false;
            }
            if(arr[i][b]!= 2)
            {
                p2line = false;
            }
            b++;
        }

        winPrint(g);

        p1line = true;
        p2line = true;
        b = size-1;
        for(int i = 0; i < size; i++)//diagonal check down left
        {
            if(arr[i][b] != 1)
            {
                p1line = false;
            }
            if(arr[i][b]!= 2)
            {
                p2line = false;
            }
            b--;
        }

        winPrint(g);

        p1line = true;
        p2line = true;
        gameDraw = true;
        for(int i = 0; i<size;i++)//draw check
        {
            for(int j = 0; j<size;j++)
            {
                if(arr[i][j]==0)//if a spot is not filled
                {
                    gameDraw = false;//draw is false
                }
            }
        }
        if(gameDraw == true)//if draw is true
        {
            if(draw == false)//if no one won when it was full
            {
                g.setColor(Color.BLACK);//print draw
                g.fillRect(190, 50, 220, 60);
                g.setColor(Color.WHITE);
                g.drawString("DRAW!",200, 100);
            }
            retry = true;//set retry to true
        }
    }

    //printing winner method
    public void winPrint(Graphics g)
    {
        File coffee = new File("coffee.WAV");//declaring sounds
        File snake = new File("snake.WAV");
        if(p1line == true)//if 1 player won
        {
            g.setColor(Color.BLACK);//draw java wins
            g.fillRect(150, 50, 335, 60);
            g.setColor(Color.WHITE);
            g.drawString("Java Wins!", 160, 100);
            draw = true;
            if(retry == false)
                playSound(coffee);//play coffee sound once
            retry = true;

        }
        else if(p2line == true)//opposite
        {
            g.setColor(Color.BLACK);
            g.fillRect(110, 50, 395, 60);
            g.setColor(Color.WHITE);
            g.drawString("Python Wins!", 120, 100);
            draw = true;
            if(retry == false)
                playSound(snake);//play snake sound once
            retry =true;
        }
    }

    //playing sound method
    public void playSound(File Sound)
    {
        try
        {
            Clip clip = AudioSystem.getClip();//get clip
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.start();//start clip
            if(Sound == song)//if clip is the song
                clip.loop(Clip.LOOP_CONTINUOUSLY);//loop continuously
        }
        catch(Exception e)
        {

        }
    }

    //see if opponent is 1 away from winning method
    public boolean checkToWinning()
    {
        store1 = 2;//array point variables
        store2 = 2;
        //*all the checking below is commented in the winCheck method*
        for(int i = 0; i < size; i++)//horizontal check
        {
            int check = 0;//counting variable
            int ocheck = 0;
            for(int j = 0; j < size; j++)
            {
                if(arr[i][j] == 1)//if point belongs to player 1 (java)
                    check++;//add 1 to check
                else if(arr[i][j] == 0)//if point belongs to no one
                {
                    ocheck++;//add one to other count
                    store1 = j;//set store 1 and 2 as i and j points
                    store2 = i;
                }
            }
            if(check == size-1 && ocheck+check == size)//if check is equal to size-1(1 away from winning) and it all adds up to total(not already blocked)
            {
                almostWin = true;//set almost win to true
                return almostWin;
            }
        }
        if(almostWin==false)
        {
            for(int i = 0; i < size; i++)//vertical check
            {
                int check = 0;
                int ocheck = 0;
                for(int j = 0; j < size; j++)
                {
                    if(arr[j][i ]== 1)
                        check++;
                    else if(arr[j][i] == 0)
                    {
                        ocheck++;
                        store1 = i;
                        store2 = j;
                    }
                }
                if(check == size-1 && ocheck+check == size)
                {
                    almostWin = true;
                    return almostWin;
                }
            }
        }
        if(almostWin==false)
        {
            b = 0;
            int check = 0;
            int ocheck = 0;
            for(int i = 0; i < size; i++)//diagonal check right
            {
                if(arr[i][b] == 1)
                    check++;
                else if(arr[i][b] == 0)
                {
                    ocheck++;
                    store1 = b;
                    store2 = i;
                }
                b++;
            }
            if(check == size-1 && ocheck+check == size)
            {
                almostWin = true;
                return almostWin;
            }
        }
        if(almostWin==false)
        {
            b = size-1;
            int check = 0;
            int ocheck = 0;
            for(int i = 0; i < size; i++)//diagonal check left
            {
                if(arr[i][b] == 1)
                    check++;
                else if(arr[i][b] == 0)
                {
                    ocheck++;
                    store1 = b;
                    store2 = i;
                }
                b--;
            }
            if(check == size-1 && ocheck+check == size)
            {
                almostWin = true;
                return almostWin;
            }
        }
        return false;
    }

    //see if autonomous is 1 away from winning method
    public boolean checkToWinning2()
    {
        //opponent method repeated but for autonomous
        store1 = 2;
        store2 = 2;
        for(int i = 0; i < size; i++)//horizontal
        {
            int check = 0;
            int ocheck = 0;
            for(int j = 0; j < size; j++)
            {
                if(arr[i][j] == 2)
                    check++;
                else if(arr[i][j] == 0)
                {
                    ocheck++;
                    store1 = j;
                    store2 = i;
                }
            }
            if(check == size-1 && ocheck+check == size)
            {
                almostWin2 = true;
                return almostWin2;
            }
        }
        if(almostWin2==false)
        {
            for(int i = 0; i < size; i++)//vertical
            {
                int check = 0;
                int ocheck = 0;
                for(int j = 0; j < size; j++)
                {
                    if(arr[j][i] == 2)
                        check++;
                    else if(arr[j][i] == 0)
                    {
                        ocheck++;
                        store1 = i;
                        store2 = j;
                    }
                }
                if(check == size-1 && ocheck+check == size)
                {
                    almostWin2 = true;
                    return almostWin2;
                }
            }
        }
        if(almostWin2 == false)
        {
            b = 0;
            int check = 0;
            int ocheck = 0;
            for(int i = 0; i < size; i++)//diagonal right
            {
                if(arr[i][b] == 2)
                    check++;
                else if(arr[i][b] == 0)
                {
                    ocheck++;
                    store1 = b;
                    store2 = i;
                }
                b++;
            }
            if(check == size-1 && ocheck+check == size)
            {
                almostWin2 = true;
                return almostWin2;
            }
        }
        if(almostWin2==false)
        {
            b=size-1;
            int check = 0;
            int ocheck = 0;
            for(int i = 0; i < size; i++)//diagonal left
            {
                if(arr[i][b] == 2)
                    check++;
                else if(arr[i][b] == 0)
                {
                    ocheck++;
                    store1 = b;
                    store2 = i;
                }
                b--;
            }
            if(check == size-1 && ocheck+check == size)
            {
                almostWin2 = true;
                return almostWin2;
            }
        }
        return false;
    }
}