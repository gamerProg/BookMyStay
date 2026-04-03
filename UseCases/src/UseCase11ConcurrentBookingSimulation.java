import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<BookingRequest> queue = new LinkedList<>();

    BookingSystem() {
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public synchronized void addRequest(BookingRequest req) {
        queue.add(req);
        System.out.println(req.guestName + " added request for " + req.roomType);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }

    public synchronized void processBooking(BookingRequest req) {
        int available = inventory.getOrDefault(req.roomType, 0);

        if (available > 0) {
            inventory.put(req.roomType, available - 1);
            System.out.println(Thread.currentThread().getName() +
                    " booked " + req.roomType + " for " + req.guestName);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED booking for " + req.guestName + " (No rooms)");
        }
    }

    public void printInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

class BookingWorker extends Thread {
    private BookingSystem system;

    BookingWorker(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {
        while (true) {
            BookingRequest req;

            synchronized (system) {
                req = system.getRequest();
            }

            if (req == null) break;

            system.processBooking(req);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();

        system.addRequest(new BookingRequest("Guest1", "Deluxe"));
        system.addRequest(new BookingRequest("Guest2", "Deluxe"));
        system.addRequest(new BookingRequest("Guest3", "Deluxe"));
        system.addRequest(new BookingRequest("Guest4", "Suite"));
        system.addRequest(new BookingRequest("Guest5", "Suite"));

        Thread t1 = new BookingWorker(system, "Thread-1");
        Thread t2 = new BookingWorker(system, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.printInventory();
    }
}