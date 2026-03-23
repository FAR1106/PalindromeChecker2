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

    void display() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType + ", Room ID: " + roomId);
    }
}

class BookingHistory {

    private List<Reservation> history;

    BookingHistory() {
        history = new ArrayList<>();
    }

    void add(Reservation reservation) {
        history.add(reservation);
    }

    List<Reservation> getAll() {
        return history;
    }
}

class BookingReportService {

    void displayAll(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            r.display();
        }
    }

    void summary(List<Reservation> reservations) {
        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : reservations) {
            countMap.put(r.roomType, countMap.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("Booking Summary:");
        for (String type : countMap.keySet()) {
            System.out.println(type + ": " + countMap.get(type));
        }
    }
}

public class Rept {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.add(new Reservation("Alice", "Single Room", "SingleRoom-1"));
        history.add(new Reservation("Bob", "Double Room", "DoubleRoom-2"));
        history.add(new Reservation("Charlie", "Suite Room", "SuiteRoom-3"));
        history.add(new Reservation("David", "Single Room", "SingleRoom-4"));

        BookingReportService reportService = new BookingReportService();

        System.out.println("===== Book My Stay App v8.0 =====");

        System.out.println("All Bookings:");
        reportService.displayAll(history.getAll());

        System.out.println();
        reportService.summary(history.getAll());
    }
}