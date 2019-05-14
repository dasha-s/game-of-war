/**************************************
       Name: Daria Sykuleva
            Game of war
**************************************/   
//standard imports     
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//class for screen output
public class GameOfWarView extends JFrame
{
    //constants for app width and height
    private final int APP_WIDTH = 1000;
    private final int APP_HEIGHT = 1000;
    //instance variables
    //left player cards
    private Card leftToGo;
    private Card leftCurrent;
    private Card leftFlipped;
    //right player cards
    private Card rightToGo;
    private Card rightCurrent;
    private Card rightFlipped;
    //conrtols inside the frame
    private JButton flip;
    private JButton newGame;
    private JButton exit;
    private JLabel warText;
    private JLabel leftToGoCounter;
    private JLabel leftCurrentCounter;
    private JLabel leftFlippedCounter;
    private JLabel rightToGoCounter;
    private JLabel rightCurrentCounter;
    private JLabel rightFlippedCounter; 
    //instance for storing game data   
    private GameOfWarDoc doc;
    //constants
    private final int LEFT_STATUS_X = 430;
    private final int LEFT_STATUS_Y = 60;
    private final int LEFT_STATUS_WIDTH = 20;
    private final int LEFT_STATUS_HEIGHT = 20;   
    private final int RIGHT_STATUS_X = 550;
    private final int RIGHT_STATUS_Y = 60;
    private final int RIGHT_STATUS_WIDTH = 20;
    private final int RIGHT_STATUS_HEIGHT = 20;
    //war control
    private boolean war;
    private int warCounter;
    //Game Over message
    private JLabel statusBar;
    //Game Over indicator
    private boolean gameOver;
    
    //inner class
    //called every time you click the Flip button
    private class FlipActionListener implements ActionListener
    {
       //called to open cards
       public void openCards()
       {
           //perform open cards
           doc.moveToLeftCurrent();
           //if nothing left to flip - change the image 
           if (doc.getLeftToGo().size() == 0)
           {
               leftToGo.setImage("/cards/blank.png");
           }
           //perform open cards
           doc.moveToRightCurrent(); 
           //if nothing left to flip - change the image
           if (doc.getRightToGo().size() == 0)
           {
               rightToGo.setImage("/cards/blank.png");
           }   
           //war processing        
           if (war && warCounter < 3)
           {
               //place 3 cards cardback up
               warCounter++;
               //draw image of the cardback
               leftCurrent.setImage("/cards/cardback.jpg");
               rightCurrent.setImage("/cards/cardback.jpg"); 
               //show counter for placed cards
               leftCurrentCounter.setText(String.format("%d",doc.getLeftCurrent().size()));
               rightCurrentCounter.setText(String.format("%d",doc.getRightCurrent().size()));                                           
           }
           //war is over
           else
           {   
               //reset war counter
               warCounter = 0;      
               //"open" cards                    
               leftCurrent.setImage(doc.getLastLeftCurrent().getImageFileName());
               rightCurrent.setImage(doc.getLastRightCurrent().getImageFileName());
               //check for war situation 
               if (doc.getLastLeftCurrent().getRank() == doc.getLastRightCurrent().getRank())
               {
                   //we have a war
                   war = true;
                   warText.setVisible(true);
                   leftCurrentCounter.setVisible(true);
                   rightCurrentCounter.setVisible(true); 
                   leftCurrentCounter.setText(String.format("%d",doc.getLeftCurrent().size()));
                   rightCurrentCounter.setText(String.format("%d",doc.getRightCurrent().size()));                                           
                                    
               } 
               //not a war 
               else 
               {
                   war = false;
                   warText.setVisible(false); 
                   leftCurrentCounter.setVisible(false);
                   rightCurrentCounter.setVisible(false);                                          
               }                                                   
           }     
       }
       //method called when button is clicked
       public void actionPerformed (ActionEvent e)  
       {       
           //moving the cards from the deck                  
           if (war || (doc.getLeftCurrent().isEmpty()
             && doc.getRightCurrent().isEmpty()))
           {
               //check for empty arrays and game over situation
               if (!checkArrays())
               {
                  openCards();
                  leftToGoCounter.setText(String.format("%d",doc.getLeftToGo().size()));
                  rightToGoCounter.setText(String.format("%d",doc.getRightToGo().size()));
               }                    
           }
           //winner of the round takes the cards to his deck
           else
           {
               //winner is the left player
               if (doc.getLastLeftCurrent().getRank()>doc.getLastRightCurrent().getRank())
               {
                   doc.moveToLeftFlipped();
                   leftFlippedCounter.setText(String.format("%d",doc.getLeftFlippedSize()));                   
                   leftFlipped.setImage("/cards/cardback.jpg");
                   leftCurrent.setImage("/cards/blank.png");
                   rightCurrent.setImage("/cards/blank.png");
                      
               } 
               //winner is the right player
               else if (doc.getLastLeftCurrent().getRank()<doc.getLastRightCurrent().getRank())
               {
                   doc.moveToRightFlipped();
                   rightFlippedCounter.setText(String.format("%d",doc.getRightFlippedSize()));                                      
                   rightFlipped.setImage("/cards/cardback.jpg");
                   leftCurrent.setImage("/cards/blank.png");                   
                   rightCurrent.setImage("/cards/blank.png");                   
               }                
           }  
           repaint();           
       }
       public boolean checkArrays()  
       {   
           //do nothing if Game is Over  
           if (gameOver)
           {
               return true;
           }     
           boolean result = false;  
           //left player deck is empty                     
           if (doc.getLeftToGo().isEmpty())
           {
               result = true;
               //Left player has no cards left 
               if (doc.getLeftFlippedSize() == 0)
               {
                   gameOver = true;
                   statusBar.setText("LEFT PLAYER LOST; RIGHT PLAYER WON");
                   statusBar.setVisible(true);                                                                                    
               }
               //refill the left player Deck from played cards
               else
               {
                   doc.shuffleLeftFlipped();
                   doc.moveToLeftToGo();
                   leftFlipped.setImage("/cards/blank.png");
                   leftToGo.setImage("/cards/cardback.jpg");
                   leftToGoCounter.setText(String.format("%d",doc.getLeftToGo().size()));
                   leftFlippedCounter.setText(String.format("%d",doc.getLeftFlippedSize()));                                                                                              
               }
           }
           //right player deck is empty
           if (doc.getRightToGo().isEmpty())
           {
               result = true;
               //right player has no cards left
               if (doc.getRightFlippedSize() == 0)
               {
                   gameOver = true;
                   statusBar.setText("RIGHT PLAYER LOST ; LEFT PLAYER WON");
                   statusBar.setVisible(true);                                                                                    
               }
               //refill the right player deck from played cards
               else
               {
                   doc.shuffleRightFlipped(); 
                   doc.moveToRightToGo(); 
                   rightFlipped.setImage("/cards/blank.png");
                   rightToGo.setImage("/cards/cardback.jpg");
                   rightToGoCounter.setText(String.format("%d",doc.getRightToGo().size()));
                   rightFlippedCounter.setText(String.format("%d",doc.getRightFlippedSize()));                                                                                                                                                                                                                                 
               }
           } 
           return result;         
       } 
    }  
    //New Game button click handling     
    private class NewGameActionListener implements ActionListener
    {       
       public void actionPerformed (ActionEvent e)  
       {  
           //initialize new game
           gameOver = false;
           statusBar.setVisible(false);
           warText.setVisible(false); 
           leftCurrentCounter.setVisible(false);
           rightCurrentCounter.setVisible(false);                                                                                                                             
           initGame();
           repaint();                      
       }
    } 
    //Exit button click handling
    private class ExitActionListener implements ActionListener
    {       
       public void actionPerformed (ActionEvent e)  
       { 
           //exit program
           System.exit(0); 
       }
    }                 

    //constructor
    public GameOfWarView()
    {
        //set title for the game window
        setTitle("Game of War");
        //Required
        setSize(APP_WIDTH, APP_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set control auto placement off
        setLayout(null);
        //get the images and set sizes for the images
        leftToGo = new Card("/cards/cardback.jpg", 0, 0);
        add(leftToGo);
        leftToGo.setLocation(200,100);

        leftCurrent = new Card("/cards/blank.png", 0, 0);
        add(leftCurrent);
        leftCurrent.setLocation(390,100);
                
        leftFlipped = new Card("/cards/blank.png", 0, 0);
        add(leftFlipped);
        leftFlipped.setLocation(200,300);
        //do the same for the right side
        rightToGo = new Card("/cards/cardback.jpg", 0, 0);
        add(rightToGo);
        rightToGo.setLocation(700,100);
        
        rightCurrent = new Card("/cards/blank.png", 0, 0);
        add(rightCurrent);
        rightCurrent.setLocation(510,100);        
        
        rightFlipped = new Card("/cards/blank.png", 0, 0);
        add(rightFlipped);
        rightFlipped.setLocation(700,300);
        //create the FLIP button
        flip = new JButton("FLIP");
        add(flip);
        flip.setLocation(450,300);
        flip.setSize(100,20);
        //add button action listener 
        flip.addActionListener(new FlipActionListener());
        //create the NEW GAME button
        newGame = new JButton("NEW GAME");
        add(newGame);
        newGame.setLocation(450,500);
        newGame.setSize(100,20);
        //add button action listener
        newGame.addActionListener(new NewGameActionListener());
        //create the EXIT button
        exit = new JButton("EXIT");
        add(exit);
        exit.setLocation(450,550);
        exit.setSize(100,20);
        //add button action listener
        exit.addActionListener(new ExitActionListener());
        //add the message WAR when the war is on
        warText = new JLabel("WAR!");
        add(warText);
        warText.setLocation(485,70);
        warText.setSize(40,20);
        //make it invisible during the game
        warText.setVisible(false);
        //Cards amount in the deck for the left player
        leftToGoCounter = new JLabel();
        add(leftToGoCounter);
        leftToGoCounter.setLocation(240,80);
        leftToGoCounter.setSize(20,20);
        leftToGoCounter.setVisible(true); 
        
        leftFlippedCounter = new JLabel();
        add(leftFlippedCounter);
        leftFlippedCounter.setLocation(240,450);
        leftFlippedCounter.setSize(20,20);
        leftFlippedCounter.setVisible(true); 
        //Cards amount in the deck for the right player
        rightToGoCounter = new JLabel();
        add(rightToGoCounter);
        rightToGoCounter.setLocation(740,80);
        rightToGoCounter.setSize(20,20);
        rightToGoCounter.setVisible(true); 
        
        rightFlippedCounter = new JLabel();
        add(rightFlippedCounter);
        rightFlippedCounter.setLocation(740,450);
        rightFlippedCounter.setSize(20,20);
        rightFlippedCounter.setVisible(true); 
        //Counter for the opened cards during the WAR situation (left side)
        leftCurrentCounter = new JLabel();
        add(leftCurrentCounter);
        leftCurrentCounter.setLocation(430,250);
        leftCurrentCounter.setSize(20,20);
        leftCurrentCounter.setVisible(false); 
        //Counter for the opened cards during the War situation (right side) 
        rightCurrentCounter = new JLabel();
        add(rightCurrentCounter);
        rightCurrentCounter.setLocation(550,250);
        rightCurrentCounter.setSize(20,20);
        rightCurrentCounter.setVisible(false); 
        //Message that appears when game is over and shows who won and lost                                                                     
        statusBar = new JLabel();
        add(statusBar);
        statusBar.setLocation(380,380);
        statusBar.setSize(400,40);
        statusBar.setVisible(false);                                                                                    
        //create new class for storing the game data
        doc = new GameOfWarDoc();
        //new game initialization
        initGame();
        //initialize war variable       
        war = false;
        warCounter = 0;
        
        gameOver = false;
        
        setVisible(true);
    }
    public void initGame()
    {
        //create array of 52 cards
        doc.loadDeck();
        //divide the deck of 52 cards between 2 players
        doc.divideBetweenPlayers();
        //set various counters values
        leftToGoCounter.setText(String.format("%d",doc.getLeftToGo().size()));
        rightToGoCounter.setText(String.format("%d",doc.getRightToGo().size()));        
        leftFlippedCounter.setText(String.format("%d",doc.getLeftFlippedSize()));
        rightFlippedCounter.setText(String.format("%d",doc.getRightFlippedSize())); 
        //set initial images  
        leftCurrent.setImage("/cards/blank.png");
        rightCurrent.setImage("/cards/blank.png");
        leftFlipped.setImage("/cards/blank.png");
        rightFlipped.setImage("/cards/blank.png");
        leftToGo.setImage("/cards/cardback.jpg");
        rightToGo.setImage("/cards/cardback.jpg");
    }
    //method for painting 
    public void paint(Graphics g)
    {
        // Call the parent to clear the screen
        super.paint(g);
        //draw the indicators for who won the current turn
        if (!doc.getLeftCurrent().isEmpty() 
          && !doc.getRightCurrent().isEmpty()
          && !war)
        {
            //when the left player wins the current turn
            if (doc.getLastLeftCurrent().getRank()>doc.getLastRightCurrent().getRank())
            {
                //draw green and red rectangles above the cards
                g.setColor(Color.green);
                g.fillRect(LEFT_STATUS_X,LEFT_STATUS_Y, LEFT_STATUS_WIDTH, LEFT_STATUS_HEIGHT);
                g.setColor(Color.red);
                g.fillRect(RIGHT_STATUS_X, RIGHT_STATUS_Y, RIGHT_STATUS_WIDTH, RIGHT_STATUS_HEIGHT);
            } 
            //when the right player wins the current turn
            if (doc.getLastLeftCurrent().getRank()<doc.getLastRightCurrent().getRank())
            {
                //draw green and red rectangles above the cards
                g.setColor(Color.red);
                g.fillRect(LEFT_STATUS_X,LEFT_STATUS_Y, LEFT_STATUS_WIDTH, LEFT_STATUS_HEIGHT);
                g.setColor(Color.green);
                g.fillRect(RIGHT_STATUS_X, RIGHT_STATUS_Y, RIGHT_STATUS_WIDTH, RIGHT_STATUS_HEIGHT);
            }                       
        }

    
    }
    //Driver that starts the program
    public static void main (String[]args)
    {
        GameOfWarView war = new GameOfWarView();
    } 
}     




