
package activity5;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Activity5 {
    public static void main(String[] args) {
        
         Scanner scan = new Scanner (System.in);
         DecimalFormat decformat = new DecimalFormat ("#, ####. 00");
         
         String name = "";
         int age = 0;
         float grade = 0.0f;
         double cash = 0.00;
         boolean isSingle = false;
                 
                 
      System.out.print("Enter name:");
              name = scan.nextLine();
              
     System.out.print("Enter age:");
     age = scan.nextInt();
     
     System.out.print("Enter grade: ");
     grade = scan.nextFloat();
     
        System.out.print("Enter cash");
      cash = scan.nextDouble();
      
        System.out.print("Enter Status" "(True or False)":);
         
     /*
         String name = " ";
         
         int age = 0;
         
         System.out.print("Enter Name: ");
         name = scan.nextLine();
         
          System.out.print("Enter Age: ");
          
          age = scan.nextInt();
          
          
          System.out.println("Hi, " + name + "!" + " You are " + age + " years old. J");
         */
     
     
         
  
        
        
        
    }
    
}
