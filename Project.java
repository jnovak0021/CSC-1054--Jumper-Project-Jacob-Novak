import java.awt.*;
import javax.swing.*;
import java.util.*;
public class Project extends JFrame
{
   //create ProjectPanel object
   private ProjectPanel pp;
   
   //string to store name of file
   private static String fileName;

   //constructor -- create JFrame
   public Project()
   {
      super("Project");
      setVisible(true);
      setSize(835,660);
      
      setBackground(Color.BLACK);
      
      //add ProjectPanel object with param: fileName -- add to JFrame and requestFocus();
      pp = new ProjectPanel(fileName);
      add(pp);
      pp.requestFocus();
   }
   
   //main
   public static void main(String[]args)
   {
     fileName = "ProjectLevelFile.txt";
     Project p = new Project();
     p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
