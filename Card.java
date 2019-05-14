//standard imports
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.io.IOException;
import java.awt.Image;
//class extending JPanel (cards drawing)
public class Card extends JPanel 
{
    //coordinates for x and y top left corner
    private final int x;
    private final int y;
    //image to draw
    private BufferedImage image; 
    //constructor
    public Card(String fileName, int x, int y)
    {
        try 
        {
            //try to load image from the file
            this.image = ImageIO.read(getClass().getResource(fileName));
        } catch(IOException e)
        {
            //exception during the load, set image to null
            this.image = null;
        }
        //standard card size
        setSize(100,150);
        //save top left corner coordinates
        this.x = x;
        this.y = y; 
    }
    //paint itself
    protected void paintComponent(Graphics g)
    {
        //do default component painting (clear the screen)
        super.paintComponent(g); 
        //draw scaled image
        Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, x, y, null);
        
    } 
    //setter
    public void setImage(String fileName)
    {
        try 
        {
            this.image = ImageIO.read(getClass().getResource(fileName));
        } catch(IOException e)
        {
            this.image = null;
        }
      
    }
}