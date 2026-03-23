import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    void displayInventory() {
        for (String key : inventory.keySet()) {
            System.out.println(key + " Available: " + inventory.get(key));
        }
    }
}

public class Rept {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("===== Book My Stay App v3.1 =====");

        inventory.displayInventory();

        System.out.println();

        inventory.updateAvailability("Single Room", 4);

        System.out.println("After Update:");
        inventory.displayInventory();
    }
}