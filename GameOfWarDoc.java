//standard imports
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
//class that stores game data, no graphics
public class GameOfWarDoc 
{
   //initialize arrays that store the cards
   private List<CardInfo> deck;
   private List<CardInfo> leftToGo;
   private List<CardInfo> leftFlipped;
   private List<CardInfo> rightToGo;
   private List<CardInfo> rightFlipped; 
   private List<CardInfo> leftCurrent;
   private List<CardInfo> rightCurrent; 
   //initialization of the deck of cards
   void loadDeck()
   {
      deck = new ArrayList<CardInfo>();
      //all the cards from 2 to Ace (Ace is number 14)
      for (int i=2; i<15; i++)
      {
         //cards' suits (diamonds, hearts, clubs, spades)
         for (int j=0; j<4; j++)
         {
            //load various file names for images
            String name = "/cards/" + i;
            switch (j)
            {
               case 0:
                  name = name + "_of_diamonds.png";
                  break;
               case 1:
                  name = name + "_of_hearts.png";
                  break;
               case 2:
                  name = name + "_of_clubs.png";
                  break;
               case 3:
                  name = name + "_of_spades.png";
                  break;  
            }
            //structure that holds ranks and images of the cards
            CardInfo info = new CardInfo();
            info.setRank(i);
            info.setImageFileName(name);
            deck.add(info);
         }
      }
      //shuffle the deck
      Collections.shuffle(deck);      
   }    
   //divide all the cards between players (26 cards for each player)
   public void divideBetweenPlayers()
   {
      //check the deck
      if (deck.size() == 0)
      {
         return;
      }
      //create decks for left and right players
      else
      {
         leftToGo = new ArrayList(deck.subList(0,26));
         rightToGo = new ArrayList(deck.subList(26,52));
         //clear all additional arrays
         if (leftCurrent != null)
         {
             leftCurrent.clear();
         }
         if (rightCurrent != null)
         {
             rightCurrent.clear();
         }
         if (leftFlipped != null)
         {
             leftFlipped.clear();
         }
         if (rightFlipped != null)
         {
             rightFlipped.clear();
         }                          
      }
   }
   //open the card
   public void moveToLeftCurrent()
   {
      //check that the player's deck is not empty
      if (leftToGo.size() > 0)
      {
         //move array element between arrays
         leftCurrent.add(leftToGo.get(leftToGo.size() - 1));
         leftToGo.remove(leftToGo.size() - 1);
      }   
   }
   //open the card
   public void moveToRightCurrent()
   {
      //check that the player's deck is not empty   
      if (rightToGo.size() > 0)
      {
         //move array element between arrays
         rightCurrent.add(rightToGo.get(rightToGo.size() - 1));
         rightToGo.remove(rightToGo.size() - 1);
      }   
   }
   //winner takes played cards
   public void moveToLeftFlipped()
   {
       //create if null
       if (leftFlipped == null) 
       {
           leftFlipped = new ArrayList();
       } 
       //common part         
       moveToFlipped(leftFlipped);             
   }
   //winner takes played cards
   public void moveToRightFlipped()
   { 
       //create if null
       if (rightFlipped == null) 
       {
           rightFlipped = new ArrayList();
       }  
       //common part     
       moveToFlipped(rightFlipped);      
   }
   //moves array elements
   public void moveToFlipped(List<CardInfo> target)
   {
       //adds all source array elements to target
       target.addAll(leftCurrent);
       target.addAll(rightCurrent);  
       //clear both source arrays    
       leftCurrent.clear();
       rightCurrent.clear();      
   }
   //get last element from array of played cards
   public CardInfo getLastLeftCurrent()
   {
       //common part
       return getLastCurrent(leftCurrent);      
   }
   //get last element from array of played cards
   public CardInfo getLastRightCurrent()
   {
       //common part
       return getLastCurrent(rightCurrent);      
   }
   //get last element from given array 
   public CardInfo getLastCurrent(List<CardInfo> current)
   {
       //check if not empty
       if (!current.isEmpty())
       {
           //last element has index size-1
           int size = current.size();
           return current.get(size - 1);
       }
       //return null if empty array
       else 
       {
           return null;
       }
   }
   //shuffle array
   public void shuffleLeftFlipped()
   {
       if (leftFlipped != null)
       {
           Collections.shuffle(leftFlipped);
       } 
   }
   //shuffle array
   public void shuffleRightFlipped()
   {
       if (rightFlipped != null)
       {
           Collections.shuffle(rightFlipped);
       } 
   }
   //refill player deck
   public void moveToLeftToGo()
   {
       leftToGo.addAll(leftFlipped);
       leftFlipped.clear();
   }
   //refill player deck
   public void moveToRightToGo()
   {
       rightToGo.addAll(rightFlipped);
       rightFlipped.clear();
   }
        
   // getters
   public List<CardInfo> getDeck()
   {
       return deck;
   }
   public List<CardInfo> getLeftToGo()
   {
       return leftToGo;
   }
   public List<CardInfo> getRightToGo()
   {
       return rightToGo;
   }
   public List<CardInfo> getLeftCurrent()
   {
       if (leftCurrent == null)
       {
           leftCurrent = new ArrayList();
       } 
       return leftCurrent;
   }
   public List<CardInfo> getRightCurrent()
   {
       if (rightCurrent == null)
       {
           rightCurrent = new ArrayList();
       }    
       return rightCurrent;
   }
   public int getLeftFlippedSize()
   {
       if (leftFlipped == null)
       {
           return 0;
       }
       return leftFlipped.size();
   }   
   public int getRightFlippedSize()
   {
       if (rightFlipped == null)
       {
           return 0;
       }
       return rightFlipped.size();
   }
   
   //setters
   public void setLeftCurrent(List<CardInfo> info)
   {
      leftCurrent = new ArrayList(info);
   }
   public void setRightCurrent(List<CardInfo> info)
   {
      rightCurrent = new ArrayList(info);
   }
   
   

}  
  