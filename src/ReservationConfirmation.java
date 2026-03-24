import java.util.*;

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
}

class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void registerRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingRequestQueue {
    private final Queue<Reservation> queue = new LinkedList<>();

    public void submitRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation nextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingService {
    private final RoomInventory inventory;
    private final Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    private String generateRoomID(String roomType) {
        return roomType + "-" + (roomCounter++);
    }

    public void processBookingQueue(BookingRequestQueue requestQueue) {
        while (!requestQueue.isEmpty()) {
            Reservation r = requestQueue.nextRequest();
            String type = r.getRoomType();
            if (inventory.isAvailable(type)) {
                String roomID = generateRoomID(type);
                allocatedRooms.putIfAbsent(type, new HashSet<>());
                Set<String> rooms = allocatedRooms.get(type);
                if (!rooms.contains(roomID)) {
                    rooms.add(roomID);
                    inventory.decrement(type);
                    System.out.println("Booking confirmed: " + r.getGuestName() +
                            " assigned " + type + " Room ID: " + roomID);
                }
            } else {
                System.out.println("Booking failed for " + r.getGuestName() + ": " + type + " not available.");
            }
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

public class ReservationConfirmation {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 2);
        inventory.registerRoomType("Double", 1);

        BookingRequestQueue requestQueue = new BookingRequestQueue();
        requestQueue.submitRequest(new Reservation("Alice", "Single"));
        requestQueue.submitRequest(new Reservation("Bob", "Double"));
        requestQueue.submitRequest(new Reservation("Charlie", "Single"));
        requestQueue.submitRequest(new Reservation("David", "Single")); // Should fail

        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookingQueue(requestQueue);
        bookingService.displayAllocatedRooms();
    }
}