package api;


/*
Resource classes: provide an intermediary between UI components and services & used for defining
the Application Programming Interface (API)
APIs: a best practice & used to separate backend software from fronted software & provides a clean separation
in behavior and responsibilities for software components.
 */

/*
HotelResource:
1) intended for public usage
2) little to no behavior contained inside the class
3) should make use of the Service classes to implement its method
 */

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource;
    private HotelResource() {}
    public static HotelResource getInstance() {
        if (hotelResource == null) {hotelResource = new HotelResource();}
        return hotelResource;
    }

    private static CustomerService customerService = CustomerService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomerReservation(customerService.getCustomer(customerEmail));
    }
}
