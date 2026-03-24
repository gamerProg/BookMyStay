import java.util.HashMap;
import java.util.Map;

class Room {
    private final String type;
    private final double price;
    private final String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    @Override
    public String toString() {
        return type + " - Price: $" + price + ", Amenities: " + amenities;
    }
}

class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void registerRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class SearchService {
    private final RoomInventory inventory;
    private final Map<String, Room> rooms;

    public SearchService(RoomInventory inventory, Map<String, Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms.values()) {
            int available = inventory.getAvailability(room.getType());
            if (available > 0) {
                System.out.println(room + " | Available: " + available);
            }
        }
    }
}

public class RoomSearch {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 10);
        inventory.registerRoomType("Double", 0);
        inventory.registerRoomType("Suite", 2);

        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single", new Room("Single", 100, "WiFi, TV"));
        rooms.put("Double", new Room("Double", 180, "WiFi, TV, AC"));
        rooms.put("Suite", new Room("Suite", 300, "WiFi, TV, AC, Mini Bar"));

        SearchService searchService = new SearchService(inventory, rooms);
        searchService.searchAvailableRooms();
    }
}