import java.util.*;

class Reservation {
    private final String reservationID;
    private final String guestName;
    private final String roomType;

    public Reservation(String reservationID, String guestName, String roomType) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "ID='" + reservationID + '\'' +
                ", Guest='" + guestName + '\'' +
                ", RoomType='" + roomType + '\'' +
                '}';
    }
}

class BookHistory {
    private final List<Reservation> confirmedBookings = new ArrayList<>();

    public void addBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    public List<Reservation> getAllBookings() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}

class BookingReportService {
    private final BookHistory history;

    public BookingReportService(BookHistory history) {
        this.history = history;
    }

    public void displayAllBookings() {
        System.out.println("All Confirmed Bookings:");
        for (Reservation r : history.getAllBookings()) {
            System.out.println(r);
        }
    }

    public void displaySummaryByRoomType() {
        System.out.println("\nBooking Summary by Room Type:");
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : history.getAllBookings()) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }
        summary.forEach((roomType, count) -> System.out.println(roomType + ": " + count + " bookings"));
    }
}

public class BookingHistory {
    public static void main(String[] args) {
        BookHistory history = new BookHistory();

        history.addBooking(new Reservation("Single-1", "Alice", "Single"));
        history.addBooking(new Reservation("Double-1", "Bob", "Double"));
        history.addBooking(new Reservation("Single-2", "Charlie", "Single"));

        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();
        reportService.displaySummaryByRoomType();
    }
}