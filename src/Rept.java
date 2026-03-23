import java.io.*;
import java.util.*;

class Booking implements Serializable {
    private String guestName;
    private int roomsBooked;

    public Booking(String guestName, int roomsBooked) {
        this.guestName = guestName;
        this.roomsBooked = roomsBooked;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomsBooked() {
        return roomsBooked;
    }

    public String toString() {
        return guestName + " booked " + roomsBooked + " room(s)";
    }
}

class HotelData implements Serializable {
    int availableRooms;
    List<Booking> bookings;

    public HotelData(int rooms, List<Booking> bookings) {
        this.availableRooms = rooms;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "hotel_data.ser";

    public static void save(HotelData data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
            System.out.println("Data saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    public static HotelData load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("Data loaded successfully.");
            return (HotelData) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return new HotelData(10, new ArrayList<>());
        }
    }
}

public class Rept {

    public static void main(String[] args) {

        HotelData data = PersistenceService.load();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Book Room\n2. View Bookings\n3. Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter guest name: ");
                String name = sc.next();
                System.out.print("Enter rooms required: ");
                int rooms = sc.nextInt();

                if (rooms <= data.availableRooms) {
                    data.availableRooms -= rooms;
                    data.bookings.add(new Booking(name, rooms));
                    System.out.println("Booking successful. Rooms left: " + data.availableRooms);
                } else {
                    System.out.println("Not enough rooms available.");
                }

            } else if (choice == 2) {
                if (data.bookings.isEmpty()) {
                    System.out.println("No bookings found.");
                } else {
                    for (Booking b : data.bookings) {
                        System.out.println(b);
                    }
                }

            } else if (choice == 3) {
                PersistenceService.save(data);
                System.out.println("Exiting system...");
                break;
            }
        }

        sc.close();
    }
}