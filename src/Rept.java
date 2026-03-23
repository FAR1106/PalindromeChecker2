import java.util.*;

class AddOnService {
    String name;
    double price;

    AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class AddOnServiceManager {

    private HashMap<String, List<AddOnService>> serviceMap;

    AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    double calculateTotal(String reservationId) {
        double total = 0;
        List<AddOnService> services = serviceMap.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) {
                total += s.price;
            }
        }
        return total;
    }

    void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) {
                System.out.println(s.name + " - " + s.price);
            }
        }
    }
}

public class Rept {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId1 = "SingleRoom-1";
        String reservationId2 = "SuiteRoom-3";

        manager.addService(reservationId1, new AddOnService("Breakfast", 200));
        manager.addService(reservationId1, new AddOnService("WiFi", 100));

        manager.addService(reservationId2, new AddOnService("Airport Pickup", 500));
        manager.addService(reservationId2, new AddOnService("Dinner", 300));

        System.out.println("===== Book My Stay App v7.0 =====");

        System.out.println("Services for " + reservationId1 + ":");
        manager.displayServices(reservationId1);
        System.out.println("Total Cost: " + manager.calculateTotal(reservationId1));

        System.out.println();

        System.out.println("Services for " + reservationId2 + ":");
        manager.displayServices(reservationId2);
        System.out.println("Total Cost: " + manager.calculateTotal(reservationId2));
    }
}