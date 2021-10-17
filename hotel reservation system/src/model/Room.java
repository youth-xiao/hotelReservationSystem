package model;

public class Room implements IRoom {
    public String roomNumber;
    public Double roomPrice;
    public RoomType enumeration;
    public boolean checkFree;

    public Room(String roomNumber, double roomPrice, RoomType enumeration, boolean checkFree) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.enumeration = enumeration;
        this.checkFree = checkFree;
    }

    public String getRoomNumber() {return roomNumber;}

    public Double getRoomPrice() {return roomPrice;}

    public RoomType getRoomType() {return enumeration;}

    public boolean checkFreeRoom() {
        return false;
    }

    public boolean checkFree() { return roomPrice == 0.0;}

    @Override
    public String toString() {
        return "Room number: " + roomNumber + " Room price: " + roomPrice +
                " Room type: " +  enumeration + " Free room or not: " + checkFree;
    }
}
