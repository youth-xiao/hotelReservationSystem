import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Driver {
    public static void main(String[] args) throws ParseException {

        Customer customer = new Customer("Xiaoyang", "Zou", "zou.xia@northeastern.edu");
//        System.out.println(customer);
//        IRoom room = new Room("902", 88, RoomType.SINGLE, false);
//        System.out.print(room);

        CustomerService customerService = CustomerService.getInstance();
//        customerService.addCustomer("xyzzz@gmail.com", "Sheep", "Liu");
//        customerService.getCustomer("xyzzz@gmail.com");
//        customerService.addCustomer("abc@gmail.com", "Lion", "Hu");
//        customerService.getAllCustomers();

        ReservationService reservationService = ReservationService.getInstance();
        Room room = new Room("902", 88, RoomType.SINGLE, false);
        reservationService.addRoom(room);
        reservationService.addRoom(new Room("888", 108.8, RoomType.DOUBLE, false));
        reservationService.getARoom("888");
        reservationService.getARoom("205");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateInStr = "22/03/2022";
        Date dateIn = format.parse(dateInStr);
        String dateOutStr = "30/03/2022";
        Date dateOut = format.parse(dateOutStr);
        Customer tony = new Customer("Tony", "Zhang", "niub@yahoo.com");
        reservationService.reserveARoom(tony, room, dateIn, dateOut);
        reservationService.getCustomerReservation(tony);
        reservationService.printAllReservation();
    }
}