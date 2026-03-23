import java.util.*;

class Reservation {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    void decrement(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) - 1);
    }

    void display() {
        for (String key : inventory.keySet()) {
            System.out.println(key + " Available: " + inventory.get(key));
        }
    }
}

class BookingHistory {

    private List<Reservation> history;

    BookingHistory() {
        history = new ArrayList<>();
    }

    void add(Reservation r) {
        history.add(r);
    }

    boolean remove(String roomId) {
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).roomId.equals(roomId)) {
                history.remove(i);
                return true;
            }
        }
        return false;
    }

    void display() {
        for (Reservation r : history) {
            System.out.println("Guest: " + r.guestName + ", Room Type: " + r.roomType + ", Room ID: " + r.roomId);
        }
    }
}

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> releasedRooms;

    CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        releasedRooms = new Stack<>();
    }

    void cancel(Reservation r) {
        boolean exists = history.remove(r.roomId);
        if (exists) {
            inventory.increment(r.roomType);
            releasedRooms.push(r.roomId);
            System.out.println("Cancellation successful for " + r.guestName + " (" + r.roomId + ")");
        } else {
            System.out.println("Cancellation failed: reservation not found for " + r.guestName + " (" + r.roomId + ")");
        }
    }

    void displayReleasedRooms() {
        System.out.println("Recently Released Rooms: " + releasedRooms);
    }
}

public class Rept {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("Alice", "Single Room", "SingleRoom-1");
        Reservation r2 = new Reservation("Bob", "Double Room", "DoubleRoom-2");
        Reservation r3 = new Reservation("Charlie", "Suite Room", "SuiteRoom-3");

        history.add(r1);
        history.add(r2);
        history.add(r3);

        CancellationService cancellationService = new CancellationService(inventory, history);

        System.out.println("===== Book My Stay App v10.0 =====");

        cancellationService.cancel(r2);
        cancellationService.cancel(new Reservation("David", "Single Room", "SingleRoom-4"));
        cancellationService.cancel(r1);

        System.out.println();
        System.out.println("Updated Booking History:");
        history.display();

        System.out.println();
        System.out.println("Updated Inventory:");
        inventory.display();

        System.out.println();
        cancellationService.displayReleasedRooms();
    }
}