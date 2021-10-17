package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

    /*
    Attributions:
    https://knowledge.udacity.com/questions/543728
    */

public class ReservationService {
    Collection<Reservation> reservations = new HashSet<>();
    private static Collection<IRoom> rooms = new HashSet<>();

    private static ReservationService reservationService;

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (reservationService == null) {reservationService = new ReservationService();}
        return reservationService;
    }

    public void addRoom(IRoom room) {
        if (!rooms.add(room)) {System.out.println("Ops! The room provided already exists in the system.");}
        else {System.out.println("Yay! The room " + room +  " has been successfully added to the system.");}
    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {return room;}
        }
        System.out.println("Sorry, Room " + roomId + " is not in our system.");
        return null;
    }

    public Collection<IRoom> getRooms() {
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = findRooms(checkInDate, checkOutDate);
        if (availableRooms.contains(room)) {
            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
            if (!reservations.add(reservation)) {
                System.out.println("The reservation cannot be made, since it is not available!");
                return null;
            }
            else {
                System.out.println("The reservation has been successfully booked!");
                System.out.println(reservation);
                return reservation;
            }
        }
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        // initialize a hashset inside this method - specific to these parameter dates
        Collection<IRoom> availableRooms = new HashSet<>();
        if (reservations.size() == 0) { // if no rooms have been reserved, then all rooms are available
            availableRooms = rooms; // just assign all rooms to available room set
            System.out.println(availableRooms);
            return availableRooms;
        }
        else {
            for (Reservation reservation : reservations) { // iterate all reservations and all rooms in the system
                for (IRoom room : rooms) { // and compare room numbers and dates
                    // first, both objects should have the same room number
                    // then, input dates should all before or after the reserved period
                    if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber()) && checkInDate.before(checkOutDate) &&
                       (checkInDate.before(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckInDate())) ||
                       (checkInDate.after(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckOutDate()))) {
                            availableRooms.add(room);
                    }
                    // in this case, the same room gets date conflicts
                    else if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                            availableRooms.remove(room);
                    }
                }
            }
        }
        System.out.println(availableRooms);
        return availableRooms;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        // initialize a hashset inside this method - specific to the parameter 'customer'
        Collection<Reservation> thisCustomerReservation = new HashSet<>();
        for (Reservation reservation : reservations) { // iterate through all the booked reservations
            // to see if the reservation has a matched 'customer'
            if (reservation.getCustomer().equals(customer)) {
                thisCustomerReservation.add(reservation); // if so, then add the 'reservation' to the specific hashset.
            }
        }
        System.out.println(thisCustomerReservation);
        return thisCustomerReservation;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.print(reservation);
        }
    }
}