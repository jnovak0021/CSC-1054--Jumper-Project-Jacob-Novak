import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
//JPanel class
public class ProjectPanel extends JPanel
{
   //take in name of file
   private String fname;
   
   //create Player object and GameObject to store data of userPlayer
   private Player userPlayer;
   private GameObject user;
  
   //JOPTIONPANE to close out JFrame when user hits victory block
   private JOptionPane ein = new JOptionPane();
   
   //timer
   private Timer t;
   
   //direction boolean variables for movement of Player object 
   private boolean up, down,right, left;
   
   //ArrayList of ArrayList of GameObject to store GameObject data
   private ArrayList <ArrayList<GameObject>> objectArrayList = new ArrayList <ArrayList<GameObject>>();
   
   //int for 2d array length
   private int column, row, j =0;

   //int for gravity and jump and count to count timer ticks
   private int jump, gravity = 1, gravityVar, count = 0;

   //GameObject victory for reference that points to index i,j of ArrayList
   GameObject victory;
   
   //Constructor of JPanel ProjectPanel -- param: file name fName
   public ProjectPanel(String fName)
   {
      super();
      setPreferredSize(new Dimension(820,620));
      setBackground(Color.GRAY);
      
      //set focusable
      setFocusable(true);
      
      //store name of file read in parameter
      fname = fName;

      //keyListener
      addKeyListener(new KeyEventDemo());
      
      //try catch: Scanner to read in from fname
      try 
      {
         //instantiate Scanner object to read from file fname
         Scanner read = new Scanner(new File(fname));

         //Create player object reference
         //user position
         int userX = read.nextInt(), userY = read.nextInt();
         //instantiate Player object 
         userPlayer = new Player((userX*25)-13,(userY*25)-13,Color.RED);
         //set GameObject user = Player userPlayer
         user = userPlayer;
         
         //read in size of playing field
         row = read.nextInt();
         column = read.nextInt();
         
      
        
         //create ArrayList of ArrayList of GameObject
         for(int i = 0; i < row; i++)
         {
            ArrayList <GameObject> goArray =  new ArrayList <GameObject>();
            objectArrayList.add(goArray);
         }
      
         //nested for loop to fill levelArray with values
         for(int i = 0; i < row; i++)
         {
            for(int j = 0; j < column; j++ )
            {
               //read in values from ProjectLevelFile.txt into levelArray
               int integer = read.nextInt();    
               
               //if statement to determine which object to add based off of levelArray integer
               //center i,j in middle of box
               if(integer == 0)
               {
                  //Empty Object stored at i,j
                  objectArrayList.get(i).add(new EmptyObject((j*25)+23,(i*25)+23,Color.GRAY));
               }
               else if(integer == 1)
               {
                  
                  //Block object stored at i,j
                  objectArrayList.get(i).add(new Block((j*25)+23,(i*25)+23,Color.MAGENTA));            
               
               }
               //ADD VICTORY OBJECT ONCE CREATED at position i,j
               else if(integer == 2)
               {
                  //block object stored at i,j
                  objectArrayList.get(i).add(new Block((j*25)+23,(i*25)+23,Color.GREEN));
                  
                  //GameObject victory for reference that points to index i,j of ArrayList
                  victory = objectArrayList.get(i).get(j);
               } 
            }
         }
      
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("File not found");
      }catch(Exception e)
      {}
      
      // timer object to tick every ten Milliseconds 
      t = new Timer(10,new TimerListener());
      t.start();
   }     //Constructor{}
   
   //Paint Component
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      g.setColor(Color.BLACK);
      g.fillRect(0,0,820,620);
      
      //paint out the screen based off value of objectArrayList at 
      for(int i = 0; i < row; i++)
      {
         for(int j = 0; j < column; j++)
         {
            //call draw method for all GameObject stored in objectArrayList
            objectArrayList.get(i).get(j).draw(g);
         }
      }
      //draw userPlayer over everything
      userPlayer.draw(g);
   
      
   }

   
   //timer class: implements ActionListener
   public class TimerListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
      
         //if up is true, increase y value by 1 
         if(up)
         {
            //if player is still in the air and hasn't reached max jump height (jump < 0)
            if((userPlayer.isOnCeiling(objectArrayList)) || jump < 0)
            {
               gravity = 1;
               up  = false;
               jump = 0;
            }
            //if player is still in th air
            else
            {
               //move player up -1*jump moves and check to see if user collided with ceiling-- if so, set jump to 0 
               userPlayer.move(0,-1*jump,objectArrayList);

               if(userPlayer.isOnCeiling(objectArrayList))
               {
                  jump = 0;
                 
               }
               //move player back down are checking if player is on ceiling
               userPlayer.isOnGround(objectArrayList);
            }
            
            //decrement jump every 10 ticks of timer
            if(count%10 ==0)
               jump--;
         }
         
         
         //gravity
         //if player is on ground, set gravity to 1
         if(userPlayer.isOnGround(objectArrayList))
         {
            gravity = 1;
         }
         //if player is not on the ground, apply N sing pixel moves downward
         else
         {
            //variable (gravityVar) to keep track of how many movements to make downwards by referencing gravity
            int gravityVar =gravity;
            //while gravityVar is greater than 0, move object down gravity amount of times
            while(gravityVar > 0)
            {
               //move userPlayer down one pixel
               userPlayer.move(0,1,objectArrayList);
               repaint();
               gravityVar--;
            } 
         }
         
         //increment gravity every 20 ticks of the timer
         if(count%20==0)
         {
            gravity++;
         }
         
            
         //if left is true, decrease x value by 1 by calling Player move method
         if(left)
         {
            userPlayer.move(-1,0,objectArrayList);
         }
         //if right is true, increase x value by 1 by calling Player move method
         else if(right)
         {
            userPlayer.move(1,0,objectArrayList);
         }
         
         //increment count every tick of the timer (10 mil)
         count++;

         //call paintComponent
         repaint();
         
         user = userPlayer;

         //call GameObject getWin() method to see if user won
         if(user.getWin())
         {
            //display winner message and close program if user exits JOptionPane
            JOptionPane.showMessageDialog(null, "WIN!!", "", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
         }
      } 
   }
   

   //KeyListner
   public class KeyEventDemo implements KeyListener
   {
      public void keyTyped(KeyEvent e) {}
      
      //while key released
      public void keyReleased(KeyEvent e) 
      {
         if(e.getKeyCode() == KeyEvent.VK_W) {}
      
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            //make left false
            left = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_D)
         {
            //make right false
            right = false;
         }
      }
      public void keyPressed(KeyEvent e)
      {
         //if statements to perform action when w,a,s,d is pressed
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
            //if user is on ground when w is pressed, set jump to 7, gravity to 1 and up to true
            if(userPlayer.isOnGround(objectArrayList))
            {
               up = true;
               jump = 7;
               gravity = 1;
            }
         
          
         }
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            left = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_D)
         {
            right = true;
         }
      }
     
   }     //KeyListener {}
   
}     //Class {}

