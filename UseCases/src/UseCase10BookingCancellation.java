import java.util.*;

class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

class CancellationService {
    Map<String, Booking> bookings;
    Map<String, Integer> inventory;
    Stack<String> rollbackStack;

    CancellationService() {
        bookings = new HashMap<>();
        inventory = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    void addInventory(String roomType, int count) {
        inventory.put(roomType, count);
    }

    void addBooking(String bookingId, String roomType, String roomId) {
        bookings.put(bookingId, new Booking(bookingId, roomType, roomId));
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    void cancelBooking(String bookingId) {
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Cancellation failed: Booking does not exist");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Cancellation failed: Already cancelled");
            return;
        }

        rollbackStack.push(booking.roomId);

        inventory.put(booking.roomType, inventory.get(booking.roomType) + 1);

        booking.isCancelled = true;

        System.out.println("Booking cancelled: " + bookingId);
        System.out.println("Room released: " + booking.roomId);
    }

    void printState() {
        System.out.println("Inventory: " + inventory);
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        CancellationService service = new CancellationService();

        service.addInventory("Deluxe", 5);
        service.addInventory("Suite", 3);

        service.addBooking("B1", "Deluxe", "R101");
        service.addBooking("B2", "Suite", "R201");

        service.printState();

        service.cancelBooking("B1");

        service.printState();

        service.cancelBooking("B1");

        service.cancelBooking("B3");
    }
}