import java.util.Comparator;
import java.util.PriorityQueue;

class Ticket {
    private static int counter = 0;   

    private final String name;
    private final int urgency;        
    private final int arrival;        
    public Ticket(String name, int urgency) {
        this.name = name;
        this.urgency = urgency;
        this.arrival = counter++;
    }

    public String getName()   { return name; }
    public int getUrgency()   { return urgency; }
    public int getArrival()   { return arrival; }

    @Override
    public String toString() {
        return name + " (urgency " + urgency + ")";
    }
}

public class HelpDeskQueue1 {
    public static void main(String[] args) {
 
        Comparator<Ticket> byUrgencyThenArrival =
                Comparator.comparingInt(Ticket::getUrgency)
                          .thenComparingInt(Ticket::getArrival);

        PriorityQueue<Ticket> queue = new PriorityQueue<>(byUrgencyThenArrival);

    
        queue.offer(new Ticket("Printer", 3));
        queue.offer(new Ticket("Network", 1));
        queue.offer(new Ticket("Login", 2));

       
        queue.offer(new Ticket("Email", 2));

       
        System.out.println("Processing tickets:");
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}