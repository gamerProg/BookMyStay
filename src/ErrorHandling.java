import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void registerRoomType(String type, int count) {
        if (count < 0) throw new IllegalArgumentException("Room count cannot be negative");
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
        int current = inventory.get(type);
        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }
        inventory.put(type, current - 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class Reservation3 {
    private final String guestName;
    private final String roomType;

    public Reservation3(String guestName, String roomType) {
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
        return "Reservation{guest='" + guestName + "', roomType='" + roomType + "'}";
    }
}

class BookingService {
    private final RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation3 reservation) {
        try {
            validateReservation(reservation);
            inventory.decrement(reservation.getRoomType());
            System.out.println("Booking confirmed: " + reservation);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private void validateReservation(Reservation3 reservation) throws InvalidBookingException {
        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }
        if (reservation.getRoomType() == null || reservation.getRoomType().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }
        if (!inventory.isAvailable(reservation.getRoomType())) {
            throw new InvalidBookingException("Requested room type not available: " + reservation.getRoomType());
        }
    }
}

public class ErrorHandling {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 1);
        inventory.registerRoomType("Double", 0);

        BookingService bookingService = new BookingService(inventory);

        bookingService.confirmBooking(new Reservation3("Alice", "Single")); // valid
        bookingService.confirmBooking(new Reservation3("Bob", "Double"));   // invalid, 0 availability
        bookingService.confirmBooking(new Reservation3("", "Single"));      // invalid, empty guest
        bookingService.confirmBooking(new Reservation3("Charlie", "Suite")); // invalid, room type doesn't exist
    }
}