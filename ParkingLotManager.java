import java.util.*;

public class ParkingLotManager {

    static Scanner sc = new Scanner(System.in);

    static Stack<Integer> availableSlots = new Stack<>();
    static Queue<String> waitingQueue = new LinkedList<>();
    static String[] parkingArea;
    static HashMap<String, Integer> vehicleMap = new HashMap<>();

    static int capacity;

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       PARKING LOT MANAGER");
        System.out.println("================================");

        System.out.print("Enter total parking slots: ");
        capacity = readNumber();

        while (capacity <= 0) {
            System.out.print("Enter a valid capacity: ");
            capacity = readNumber();
        }

        parkingArea = new String[capacity];

        for (int i = capacity; i >= 1; i--) {
            availableSlots.push(i);
        }

        int choice;

        do {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Park a Vehicle");
            System.out.println("2. Remove a Vehicle");
            System.out.println("3. Display Parking Status");
            System.out.println("4. Display Waiting Queue");
            System.out.println("5. Search Vehicle");
            System.out.println("6. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            choice = readNumber();

            switch (choice) {

                case 1:
                    parkVehicle();
                    break;

                case 2:
                    removeVehicle();
                    break;

                case 3:
                    displayParking();
                    break;

                case 4:
                    displayWaitingQueue();
                    break;

                case 5:
                    searchVehicle();
                    break;

                case 6:
                    System.out.println("\nThank you for using Parking Lot Manager!");
                    System.out.println("Have a nice day!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }

    static int readNumber() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }

        int number = sc.nextInt();
        sc.nextLine();
        return number;
    }

    static void parkVehicle() {

        System.out.print("Enter vehicle number: ");
        String vehicle = sc.nextLine().trim().toUpperCase();

        if (vehicle.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        if (vehicleMap.containsKey(vehicle) ||
                waitingQueue.contains(vehicle)) {
            System.out.println("Vehicle is already registered!");
            return;
        }

        if (!availableSlots.isEmpty()) {

            int slot = availableSlots.pop();

            parkingArea[slot - 1] = vehicle;
            vehicleMap.put(vehicle, slot);

            System.out.println("Vehicle parked successfully!");
            System.out.println("Assigned Parking Slot: " + slot);

        } else {

            waitingQueue.add(vehicle);

            System.out.println("Parking is full!");
            System.out.println("Vehicle added to waiting queue.");
        }
    }

    static void removeVehicle() {

        System.out.print("Enter vehicle number to remove: ");
        String vehicle = sc.nextLine().trim().toUpperCase();

        if (!vehicleMap.containsKey(vehicle)) {
            System.out.println("Vehicle not found in parking area.");
            return;
        }

        int slot = vehicleMap.get(vehicle);

        parkingArea[slot - 1] = null;
        vehicleMap.remove(vehicle);

        System.out.println("Vehicle removed successfully!");
        System.out.println("Released Parking Slot: " + slot);

        if (!waitingQueue.isEmpty()) {

            String nextVehicle = waitingQueue.poll();

            parkingArea[slot - 1] = nextVehicle;
            vehicleMap.put(nextVehicle, slot);

            System.out.println("Next waiting vehicle parked!");
            System.out.println("Vehicle Number: " + nextVehicle);
            System.out.println("Assigned Slot: " + slot);

        } else {

            availableSlots.push(slot);
        }
    }

    static void displayParking() {

        System.out.println("\n======= PARKING STATUS =======");

        for (int i = 0; i < capacity; i++) {

            if (parkingArea[i] == null) {
                System.out.println("Slot " + (i + 1) + " : Empty");
            } else {
                System.out.println("Slot " + (i + 1) +
                        " : " + parkingArea[i]);
            }
        }

        System.out.println("------------------------------");
        System.out.println("Occupied Slots: " + vehicleMap.size());
        System.out.println("Available Slots: " + availableSlots.size());
        System.out.println("Waiting Vehicles: " + waitingQueue.size());
    }

    static void displayWaitingQueue() {

        System.out.println("\n======= WAITING QUEUE =======");

        if (waitingQueue.isEmpty()) {
            System.out.println("No vehicles in waiting queue.");
        } else {

            int position = 1;

            for (String vehicle : waitingQueue) {
                System.out.println(position + ". " + vehicle);
                position++;
            }
        }
    }

    static void searchVehicle() {

        System.out.print("Enter vehicle number to search: ");
        String vehicle = sc.nextLine().trim().toUpperCase();

        if (vehicleMap.containsKey(vehicle)) {

            int slot = vehicleMap.get(vehicle);

            System.out.println("Vehicle Found!");
            System.out.println("Vehicle Number: " + vehicle);
            System.out.println("Parking Slot: " + slot);

        } else if (waitingQueue.contains(vehicle)) {

            System.out.println("Vehicle is in the waiting queue.");

        } else {

            System.out.println("Vehicle not found.");
        }
    }
}