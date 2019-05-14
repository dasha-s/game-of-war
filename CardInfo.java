//class for storing card information
public class CardInfo 
{
    //card rank from 2 to Ace (14)
    private int rank;
    //corresponding image file name
    private String imageFileName;
    //getters and setters
    public int getRank() 
    {
        return rank;
    }
    public void setRank(int rank)
    {
        this.rank = rank;
    }
    public String getImageFileName() 
    {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName)
    {
        this.imageFileName = imageFileName;
    }
    
        
}