import java.util.*;
import java.awt.*;
import javax.swing.*;
public class GameObject
{
   //position of middle of rectangle
   private int x,y;
   
   //boolean collides to keep track if Player hits other object -- and win to see if GameObject collided with victory block
   private boolean collides, win;
   
   //color of GameObject reference
   private Color objColor = null;
 
   //constructor -- take in x, y and color as parameters and update instance variables
   public GameObject(int x_in, int y_in, Color color_in)
   {
      x = x_in;
      y = y_in;
      objColor = color_in;
   }
   
   //accesor color method
   public Color getColor()
   {
      return objColor;
   }
   
   //accesor x method
   public int getX()
   {
      return x;
   }
   
   //accesor y method
   public int getY()
   {
      return y;
   }
   
   //get win boolean
   public boolean getWin()
   {
      return win;
   }
   
   //accesor boolean collides
   public boolean collides()
   {
      return collides;
   }
   
   //mutator x method
   public void setX(int x_in)
   {
      x = x_in;
   }
   
   //mutator y method
   public void setY(int y_in)
   {
      y = y_in;
   }

   //collides method that takes in a GameObject and returns a boolean to indicate wether object collides with GameObject
   public boolean collides (GameObject other)
   {
      // topthis = the top of this square
      int topthis = y - 12;
      // bottomthis = bottom of this square
      int bottomthis = y +13;
      // leftthis = left of this square
      int leftthis = x - 13;
      // rightthis = right of this square
      int rightthis = x + 12;
      // topother = the top of the other object square
      int topother = other.getY() - 12;
      // bottomother = bottom of the other object square
      int bottomother = other.getY() + 12;
      // leftother = left of the other object square
      int leftother = other.getX() - 13 ;
      // rightother = right of the other object square
      int rightother = other.getX() + 12;
      
      //if statement to see if object is within other object -- if so, return true
      if((bottomthis > topother && bottomthis <= bottomother) ||
        (topthis < bottomother && topthis >= topother))
        {
         if((leftthis < rightother && leftthis >= leftother) ||
         (rightthis > leftother && rightthis <= rightother))
         {
            //check to see if it is a victory block since only block with green color is victory block
            if(other.getColor() ==Color.GREEN)
            {
               win = true;
            }
            return true;
         }
        }

        //return false is object is not empty and GameObject does not collide with other
        return false;
   }


   //draw method -- takes in graphics object of parameter, paints object with x and y at center size 25,25
   public void draw(Graphics g)
   {
      g.setColor(objColor);
      g.fillRect(x-10,y-10,25,25);
   }
}
