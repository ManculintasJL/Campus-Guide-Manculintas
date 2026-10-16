package javaapplication29;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
public class JavaApplication29 {

  
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        Queue<String> queue = new LinkedList<>();
       
 
      
        String[] students = {"Ana", "Ben", "Carla"};
 

        for (String name : students) {
            queue.offer(name);
        }
 
        System.out.println("Service Order:");
 
    
        boolean firstServed = false;
        while (!queue.isEmpty()) {
            String served = queue.poll();
       
            System.out.println("Serving: " + served);
 
        
            if (!firstServed) {
                firstServed = true;
                queue.offer("Dan");
                System.out.println("New student added: Dan");
                System.out.println("Updated service order: " + queue);
            }
        }
    }
}
 