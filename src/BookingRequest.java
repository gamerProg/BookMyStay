import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    private final String guestName;
    private final String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation{" + "guest='" + guestName + '\'' + ", roomType='" + roomType + '\'' + '}';
    }
}

class BookingRequestQueue {
    private final Queue<Reservation> queue = new LinkedList<>();

    public void submitRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation processNextRequest() {
        return queue.poll(); // returns null if empty
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayQueue() {
        System.out.println("Current Booking Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.submitRequest(new Reservation("Alice", "Single"));
        bookingQueue.submitRequest(new Reservation("Bob", "Suite"));
        bookingQueue.submitRequest(new Reservation("Charlie", "Double"));

        bookingQueue.displayQueue();

        System.out.println("\nProcessing requests in order:");
        while (!bookingQueue.isEmpty()) {
            Reservation next = bookingQueue.processNextRequest();
            System.out.println("Processing: " + next);
        }
    }
}
