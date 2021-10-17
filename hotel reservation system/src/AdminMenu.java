import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;


/*
Attributions:
https://knowledge.udacity.com/questions/531069
https://knowledge.udacity.com/questions/574406
 */

public class AdminMenu {
    private static CustomerService customerService = CustomerService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();
    private static AdminResource adminResource = AdminResource.getInstance();
    public static void main(String[] args) {
        System.out.println("Welcome to the Administration Menu!");
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.startAdminMenu();
    }

    public static void startAdminMenu() {
        boolean keepRunning = true;
        while (keepRunning) {
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            int selection = Integer.parseInt(scanner.next());
            switch (selection) {
                case 1:
                    System.out.println("You've requested to see all the customers.");
                    seeAllCustomers();
                    break;
                case 2:
                    System.out.println("Action 2");
                    seeAllRooms();
                    break;
                case 3:
                    System.out.println("You've requested to see all the reservations.");
                    reservationService.printAllReservation();
                    break;
                case 4:
                    System.out.println("You've requested to add a room.");
                    addARoom(scanner);
                    break;
                case 5:
                    System.out.println("You've requested to go back to the Main Menu.");
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.startMainMenu();
                    keepRunning = false;
                    break;
            }
        }
    }

    public static void displayMenu() {
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
    }

    // --------------------- 1. SEE ALL CUSTOMERS ---------------------
    public static void seeAllCustomers() {
        Collection<Customer> allCustomer = customerService.getAllCustomers();
        for (Customer customer : allCustomer) {System.out.println(customer);}
    }

    // ----------------------- 2. SEE ALL ROOMS -----------------------
    public static void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAddedRooms();
        for (IRoom room : allRooms) {System.out.println(room);}
    }

    // ------------------------- 4. ADD A ROOM ------------------------
    public static void addARoom(Scanner scanner) {
        HashSet<IRoom> newRooms = new HashSet<>();
        boolean keepAdding = true;
        while (keepAdding) {
            System.out.println("Do you want to add another room? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Please enter the room number.");
                String roomNumber = scanner.nextLine();
                System.out.println("Please enter the price for the room per night.");
                double roomPrice = scanner.nextDouble();
                boolean checkFree = roomPrice == 0;
                String enumeration = "";
                boolean validRoomType = false;
                while (!validRoomType) {
                    System.out.println("Please enter the room type (SINGLE/DOUBLE)");
                    try {
                        enumeration = scanner.nextLine().toUpperCase();
                        if (singleDouble(enumeration)) {
                            validRoomType = true;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
                RoomType roomType = RoomType.valueOf(enumeration);
                Room newRoom = new Room(roomNumber, roomPrice, roomType, roomPrice == 0);
            }
            else if (response.equalsIgnoreCase("no")){
                keepAdding = false;
            }
        }
        adminResource.addRoom(newRooms);
    }

    public static boolean singleDouble(String enumeration) {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.name().equals(enumeration)) {return true;}
        } return false;
    }
}
