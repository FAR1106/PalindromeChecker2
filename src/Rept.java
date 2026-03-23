import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
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

    void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    void display() {
        for (String key : inventory.keySet()) {
            System.out.println(key + " Available: " + inventory.get(key));
        }
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    void add(Reservation r) {
        queue.add(r);
    }

    Reservation poll() {
        return queue.poll();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingService {

    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms;
    private int counter = 1;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    void process(BookingRequestQueue queue) {
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();

            if (inventory.getAvailability(r.roomType) > 0) {
                String roomId = r.roomType.replace(" ", "") + "-" + counter++;
                allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());
                allocatedRooms.get(r.roomType).add(roomId);
                inventory.decrement(r.roomType);

                System.out.println("Confirmed: " + r.guestName + " -> " + roomId);
            } else {
                System.out.println("Failed: " + r.guestName + " -> No availability");
            }
        }
    }

    void displayAllocations() {
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " Allocated Rooms: " + allocatedRooms.get(type));
        }
    }
}

public class Rept {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Single Room"));
        queue.add(new Reservation("Charlie", "Single Room"));
        queue.add(new Reservation("David", "Suite Room"));

        BookingService service = new BookingService(inventory);

        System.out.println("===== Book My Stay App v6.0 =====");

        service.process(queue);

        System.out.println();
        System.out.println("Final Allocations:");
        service.displayAllocations();

        System.out.println();
        System.out.println("Remaining Inventory:");
        inventory.display();
    }
}