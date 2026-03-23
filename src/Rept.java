import java.util.*;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    int getAvailability(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        return inventory.get(roomType);
    }

    void decrement(String roomType) throws InvalidBookingException {
        int available = getAvailability(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No availability for: " + roomType);
        }
        inventory.put(roomType, available - 1);
    }
}

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingService {

    private RoomInventory inventory;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void book(Reservation r) throws InvalidBookingException {

        if (r.guestName == null || r.guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        int available = inventory.getAvailability(r.roomType);

        if (available <= 0) {
            throw new InvalidBookingException("Rooms not available for " + r.roomType);
        }

        inventory.decrement(r.roomType);

        System.out.println("Booking confirmed for " + r.guestName + " (" + r.roomType + ")");
    }
}

public class Rept {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        List<Reservation> requests = new ArrayList<>();
        requests.add(new Reservation("Alice", "Single Room"));
        requests.add(new Reservation("", "Double Room"));
        requests.add(new Reservation("Charlie", "Suite Room"));
        requests.add(new Reservation("David", "Single Room"));

        System.out.println("===== Book My Stay App v9.0 =====");

        for (Reservation r : requests) {
            try {
                service.book(r);
            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}