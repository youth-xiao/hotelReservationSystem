package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, Double roomPrice, RoomType enumeration, boolean checkFree) {
        super(roomNumber, 0.0, enumeration, checkFree);
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + " Room price: " + roomPrice +
                " Room type: " +  enumeration + " Free room or not: " + checkFree;
    }
}
