import java.io.*;
import java.util.*;

class Booking2 implements Serializable {
    String bookingId;
    String roomType;

    Booking2(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }

    public String toString() {
        return bookingId + " -> " + roomType;
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Booking2> bookings;

    SystemState(Map<String, Integer> inventory, List<Booking2> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public static void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    public static SystemState load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("State loaded successfully.");
            return (SystemState) in.readObject();
        } catch (Exception e) {
            System.out.println("Corrupted file. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {

        Map<String, Integer> inventory = new HashMap<>();
        List<Booking2> bookings = new ArrayList<>();

        SystemState loadedState = PersistenceService.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
            System.out.println("Recovered State:");
        } else {
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

            bookings.add(new Booking2("B1", "Deluxe"));
            inventory.put("Deluxe", inventory.get("Deluxe") - 1);

            System.out.println("Fresh State Created:");
        }

        System.out.println("Inventory: " + inventory);
        System.out.println("Bookings: " + bookings);

        SystemState currentState = new SystemState(inventory, bookings);
        PersistenceService.save(currentState);
    }
}