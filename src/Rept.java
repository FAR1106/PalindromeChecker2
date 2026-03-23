import java.util.*;

class BookingRequest {
    private String guestName;
    private int roomsRequested;

    public BookingRequest(String guestName, int roomsRequested) {
        this.guestName = guestName;
        this.roomsRequested = roomsRequested;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomsRequested() {
        return roomsRequested;
    }
}

class HotelInventory {
    private int availableRooms;

    public HotelInventory(int rooms) {
        this.availableRooms = rooms;
    }

    public synchronized boolean bookRooms(String guest, int rooms) {
        if (rooms <= availableRooms) {
            System.out.println(guest + " booking " + rooms + " room(s)...");
            availableRooms -= rooms;
            System.out.println("Booking confirmed for " + guest + " | Rooms left: " + availableRooms);
            return true;
        } else {
            System.out.println("Not enough rooms for " + guest + " | Requested: " + rooms + " | Available: " + availableRooms);
            return false;
        }
    }
}

class BookingProcessor implements Runnable {
    private Queue<BookingRequest> bookingQueue;
    private HotelInventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, HotelInventory inventory) {
        this.bookingQueue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                request = bookingQueue.poll();
            }

            if (request != null) {
                inventory.bookRooms(
                        request.getGuestName(),
                        request.getRoomsRequested()
                );
            }
        }
    }
}

public class Rept {

    public static void main(String[] args) {

        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        bookingQueue.add(new BookingRequest("Alice", 2));
        bookingQueue.add(new BookingRequest("Bob", 3));
        bookingQueue.add(new BookingRequest("Charlie", 4));
        bookingQueue.add(new BookingRequest("David", 1));
        bookingQueue.add(new BookingRequest("Eve", 2));

        HotelInventory inventory = new HotelInventory(7);

        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All bookings processed.");
    }
}