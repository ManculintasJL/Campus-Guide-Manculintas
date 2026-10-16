import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
 
public class ServiceQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<String> students = new LinkedList<>();
 
        // Step 1 & 2: Read student names and add each using offer()
        System.out.print("Enter student names (separated by commas): ");
        String[] names = sc.nextLine().split(",");
        for (String name : names) {
            String trimmed = name.trim();
            if (!trimmed.isEmpty()) {
                students.offer(trimmed);
            }
        }
 
        System.out.println("\nQueue: " + students);
        System.out.println("\n--- Serving Students ---");
 
        // Step 3 & 4: Repeatedly poll() until empty, displaying each served student
        boolean challengeDone = false;
        while (!students.isEmpty()) {
            String served = students.poll();
            System.out.println("Serving: " + served);
 
            // Challenge: add a new student after the first student is served,
            // then display the updated service order
            if (!challengeDone) {
                challengeDone = true;
                System.out.print("\nA new student arrives. Enter name: ");
                String newStudent = sc.nextLine().trim();
                if (!newStudent.isEmpty()) {
                    students.offer(newStudent);
                }
                System.out.println("Updated service order: " + students + "\n");
            }
        }
 
        System.out.println("\nQueue is empty. All students have been served.");
        sc.close();
    }
}
 