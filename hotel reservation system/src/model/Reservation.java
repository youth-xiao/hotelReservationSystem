package model;

import java.util.Date;

public class Reservation {
    /*
    getInstance() method: singleton design pattern - provides a global point of access of it.
    Makes it impossible to instantiate that class except for a single time
    There will only ever be one instance of that particular class
    Only one instance of that class will be created and others will get reference of that class.
    Singleton pattern is often used when dealing with things e.g. data managers and hardware interfaces.
    Make constructor public and singleton private. Use static method to initially create (return) a singleton
    by using the public constructor.

    Do: Singleton.getInstance()
    Instead of: Singleton s = new Singleton()   s.getInstance()
     */

    private Customer customer;
    private IRoom room;
    private Date checkInDate, checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {return customer;}
    public IRoom getRoom() {return room;}
    public Date getCheckInDate() {return checkInDate;}
    public Date getCheckOutDate() {return checkOutDate;}

    @Override
    public String toString() {
        return "Customer: " + customer + " Room: " + room + " Check in date: " + checkInDate
                + " Check out date: " + checkOutDate;
    }


}
