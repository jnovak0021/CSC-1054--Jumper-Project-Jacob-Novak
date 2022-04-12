import java.awt.*;
import javax.swing.*;
import java.util.*;
public class Player extends GameObject
{
   //Player constuctor that passes parameter into GameObject constructor
   public Player(int x, int y, Color color_in)
   {
      super(x,y,color_in);
   }



   //isOnGround method returns true if object is directly over another game object,
   // otherwise moves object 1 pixel lower and checks to see if it collides
   public boolean isOnGround(ArrayList<ArrayList<GameObject>> objectList)
   {
       if((move(0,1,objectList)))
       {
         move(0,1,objectList);
         return false;
       }
       else 
       {
         return true;
       }
   }
   
   //isOnCeiling method returns true if object is directly below another game object,
   // otherwise moves object 1 pixel higher and checks to see if it collides
   public boolean isOnCeiling(ArrayList<ArrayList<GameObject>> objectList)
   {
   
       if(move(0,-1,objectList))
       {
         move(0,-1,objectList);
         return false;
       }
       else 
       {
         return true;
       }
   }
   
   
   //move method which takes in two ints and ArrayList of ArrayList to see if user can move
   public boolean move(int moveX, int moveY, ArrayList<ArrayList<GameObject>> objectList)
   {
   
      
      //set x and y of player by calling GameObject setX and SetY + move
      setX(getX()+moveX);
      setY(getY()+moveY);
      
      //nested for loop to iterate through objectlist 2d ArrayList
      for(int i = 0; i < objectList.size();i++)
      {
         for(int j = 0; j <objectList.get(i).size();j++)
         {
            //if object is EmptyObject, allow user to move
            if(objectList.get(i).get(j) instanceof EmptyObject)
            {
            }
            //if Player object is within 50 pixels of Player object, see if they collide
            else if(((objectList.get(i).get(j).getX() > getX()-50 && objectList.get(i).get(j).getX() < getX()+50) 
              && (objectList.get(i).get(j).getY() > getY()-50 && objectList.get(i).get(j).getY() <getY()+50)))
            {
               //if player is within 50 pixels of objectList.get(i).get(j), see if they collide
               //if they collide, move x and y back to previous position, return false
               if(collides(objectList.get(i).get(j)))
               {
                  setX(getX()-moveX);
                  setY(getY()-moveY);
                  return false;
               }
            }
            
         }
      }
      return true;
   }     //move{}
}
   

