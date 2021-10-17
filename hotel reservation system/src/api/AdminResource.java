package api;

import model.Customer;
import model.IRoom;
import model.Room;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.HashSet;

public class AdminResource {
    private static CustomerService customerService = CustomerService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();

    private static AdminResource adminResource;
    private AdminResource() {}
    public static AdminResource getInstance() {
        if (adminResource == null) {adminResource = new AdminResource();}
        return adminResource;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    private static HashSet<IRoom> roomAddedList = new HashSet<>();
    public Collection<IRoom> getAddedRooms() {return roomAddedList;}
    public void addRoom(HashSet<IRoom> roomsToAdd) {
        for (IRoom room : roomsToAdd) {
            roomAddedList.add(room);
            System.out.println("Room " + room + " has been added.");
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getRooms();
    }
}
