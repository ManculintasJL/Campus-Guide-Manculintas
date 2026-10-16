
package gradeanalyzer.java;

import java.util.Scanner;
import java.util.ArrayList;
public class GradeAnalyzerJava {

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> grades = new ArrayList<>();
        
        int choice;
        
        for(int i = 1; i <= 5; i++){
        System.out.print("Enter grade " +  i + " : ");
        grades.add (sc.nextInt());
        
      
      
    }
     int highest = grades.get(0);
     int lowest = grades.get(0);
     int sum = 0;
     
     for (int i = 0; i < grades.size(); i++ ) {
         int grade = grades.get(i);
         
         if (grade > highest)
             highest = grade;
         
         if (grade < lowest)
             lowest = grade;
         sum += grade;
        
    }
     double average = (double) sum/ grades.size();
     System.out.println("Highest grade = " + highest);
       System.out.println(" lowest grade = " + lowest);
        System.out.println(" Average grade = " + average);
        
        sc.close();
    }
    
}
