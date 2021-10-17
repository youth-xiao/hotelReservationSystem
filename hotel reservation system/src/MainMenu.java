
/*
Create different command line menus --- users interact with the application from command line
Use Scanner class to read in user's responses
 */

import com.sun.tools.javac.Main;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Attributions:
https://screenrec.com/share/tB7pkQf1h3
https://www.baeldung.com/java-string-valid-date
https://knowledge.udacity.com/questions/574406
 */

public class MainMenu {
    public static CustomerService customerService = CustomerService.getInstance();
    public static ReservationService reservationService = ReservationService.getInstance();
    private static final String format = "MM/dd/yyyy";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//    private static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
//            .parseStrict().appendPattern(format).toFormatter().withResolverStyle(ResolverStyle.STRICT);


    public static void main(String[] args) {
        System.out.println("Welcome to Wonderful Hotel's Reservation Page!");
        MainMenu mainMenu = new MainMenu();
        mainMenu.startMainMenu();
    }

    public static void startMainMenu() {
        boolean keepRunning = true;
        while (keepRunning) {
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            int selection = Integer.parseInt(scanner.next());
            switch (selection) {
                case 1:
                    System.out.println("You've requested to find and reserve a room.");
                    findAndReserveARoom(scanner);
                    break;
                case 2:
                    System.out.println("You've requested to see your reservations.");
                    seeMyReservations(scanner);
                    break;
                case 3:
                    System.out.println("You've requested to create an account.");
                    createAccount(scanner);
                    break;
                case 4:
                    System.out.println("You've requested to go to the Administration menu.");
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.startAdminMenu();
                    break;
                case 5:
                    keepRunning = false;
            }
        }
    }

    public static void displayMenu() {
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }

    // ------------ 1. Find and reserve a room ------------
    public static void findAndReserveARoom(Scanner scanner) {
        Date checkInDate = getValidCheckInDate(scanner);
        Date checkOutDate = getValidCheckOutDate(scanner, checkInDate);
        Customer customer = checkCustomerAccount(scanner);
        Collection<IRoom> availableRooms = findAvailableRooms(checkInDate, checkOutDate);
        if (availableRooms != null) {
            if (readyReserveRoom(scanner)) {
                System.out.println("Which room would you like to reserve?");
                String roomChoice = scanner.nextLine();
                IRoom room = reservationService.getARoom(roomChoice);
                reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
            }
        }
        else {
            Date newCheckInDate = getRecommendedDates(checkInDate, -7);
            Date newCheckOutDate = getRecommendedDates(checkOutDate, 7);
            Collection<IRoom> recommendRooms = reservationService.findRooms(newCheckInDate, newCheckOutDate);
            if (recommendRooms != null) {
                if (readyReserveRoom(scanner)) {
                    System.out.println("Which room would you like to reserve?");
                    String roomChoice = scanner.nextLine();
                    IRoom room = reservationService.getARoom(roomChoice);
                    reservationService.reserveARoom(customer, room, newCheckInDate, newCheckOutDate);
                    System.out.println("Your room " + room + " has been successfully reserved.");
                }
            }
            else {
                System.out.println("There are no rooms available either, given the alternative dates.");
            }
        }
    }

    public static boolean readyReserveRoom(Scanner scanner) {
        boolean readyToBook = false;
        System.out.println("Are you ready to reserve any of the available rooms? (yes/no)");
        String option = scanner.next();
        if (option.equalsIgnoreCase("yes")) {readyToBook = true;}
        else if (option.equalsIgnoreCase("no")) {readyToBook = false;}
        return readyToBook;
    }

    public static Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = reservationService.findRooms(checkInDate, checkOutDate);
        if (availableRooms == null) {
            System.out.println("Sorry, there are no room available for your check in and check out dates.");
        }
        else {
            System.out.println("Yay, the room(s) are available as shown below: ");
            System.out.println(availableRooms); return availableRooms;
        }
        return null;
    }

    public static Date getRecommendedDates(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        System.out.println(cal.getTime());
        return cal.getTime();
    }

    public static Customer checkCustomerAccount(Scanner scanner) {
        System.out.println("Do you have an account associated with our hotel? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Please enter your email address.");
            String email = scanner.nextLine();
            if (customerService.getCustomer(email) == null) {
                System.out.println("Sorry, the account with this email does not exist.");
            }
            else {return customerService.getCustomer(email);}
        }
        else if (answer.equalsIgnoreCase("no")) {
            System.out.println("Would you like to set up an account? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                Customer newCustomer = createAccount(scanner);
                return newCustomer;
            }
        }
        return null;
    }

    public static boolean checkValidDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {dateFormat.parse(date);
            System.out.println("valid date format");
            return true;} catch (ParseException e) {return false;}
    }

    public static Date getDate(String date, SimpleDateFormat simpleDateFormat) {
        try {return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("The date can't be properly parsed.");
        }
        return null;
    }

    public static Date getValidCheckInDate(Scanner scanner) {
        boolean validCheckInDate = false;
        while (!validCheckInDate) {
            try {
                System.out.println("Please enter check in date \"MM/dd/yyyy\".");
                String dateInput = scanner.nextLine();
                validCheckInDate = checkValidDate(dateInput);
                if (validCheckInDate) {
                    return getDate(dateInput, simpleDateFormat);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format.");
            }
        }
        return null;
    }

    public static Date getValidCheckOutDate(Scanner scanner, Date checkInDateOutput) {
        boolean validCheckOutDate = false;
        while (!validCheckOutDate) {
            try {
                System.out.println("Please enter check out date \"MM/dd/yyyy\".");
                String dateInput = scanner.nextLine();
                if (checkValidDate(dateInput)) {
                    Date checkOutDateOutput = getDate(dateInput, simpleDateFormat);
                    if (checkOutDateOutput.after(checkInDateOutput)) {
                        validCheckOutDate = true;
                        System.out.println(checkOutDateOutput);
                        return checkOutDateOutput;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format.");
            }
        }
        return null;
    }

    // ------------------ 2. See my reservations ------------------
    public static Collection<Reservation> seeMyReservations(Scanner scanner) {
        System.out.println("Please enter your email (hello@world.com)");
        String email = scanner.nextLine();
        Customer customer = customerService.getCustomer(email);
        if (customer == null) {
            System.out.println("Sorry, there is not such an account associated with your email.");
        }
        else {
            Collection<Reservation> yourReservations = reservationService.getCustomerReservation(customer);
            if (yourReservations == null) {
                System.out.println("There are no reservations under your account.");
            }
            else {
                System.out.println(yourReservations);
                return yourReservations;
            }
        }
        return null;
    }

    // -------------------- 3. Create an account --------------------
    public static Customer createAccount(Scanner scanner) {
        System.out.println("Please enter your email. (hello@world.domain)");
        String newEmail = scanner.nextLine();
        System.out.println("Please enter your first name.");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name.");
        String lastName = scanner.nextLine();
        customerService.addCustomer(newEmail, firstName, lastName);
        return customerService.getCustomer(newEmail);
    }
}
