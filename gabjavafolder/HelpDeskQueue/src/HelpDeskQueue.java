import java.util.Comparator;
import java.util.PriorityQueue;

public class HelpDeskQueue {

   
    static class Ticket {
        private final String name;
        private final int urgency;   
        private final int arrival;   

        Ticket(String name, int urgency, int arrival) {
            this.name = name;
            this.urgency = urgency;
            this.arrival = arrival;
        }

        String getName() { return name; }
        int getUrgency() { return urgency; }
        int getArrival() { return arrival; }

        @Override
        public String toString() {
            return name + " (urgency " + urgency + ")";
        }
    }

    public static void main(String[] args) {
  
        Comparator<Ticket> byPriority = Comparator
                .comparingInt(Ticket::getUrgency)
                .thenComparingInt(Ticket::getArrival);

        PriorityQueue<Ticket> tickets = new PriorityQueue<>(byPriority);
        int counter = 0;
        tickets.offer(new Ticket("Printer", 3, counter++));
        tickets.offer(new Ticket("Network", 1, counter++));
        tickets.offer(new Ticket("Login", 2, counter++));

        tickets.offer(new Ticket("Email", 1, counter++));
        tickets.offer(new Ticket("Wi-Fi", 1, counter++));

 
        System.out.println("--- Help Desk Poll Order ---");
        int position = 1;
        while (!tickets.isEmpty()) {
            Ticket t = tickets.poll();
            System.out.println(position++ + ". " + t);
        }
    }
}