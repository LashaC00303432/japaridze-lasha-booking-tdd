// YOU WRITE THIS (Part B step 2), test first.
// Two bookings with the same user, room and slot are equal.
// The booking repository needs equals to delete bookings.
public class Booking {
    private final String userId;
    private final String roomId;
    private final TimeSlot slot;

    public Booking(String userId, String roomId, TimeSlot slot) {
        this.userId = userId;
        this.roomId = roomId;
        this.slot = slot;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId; 
    }

    public TimeSlot getSlot() {
        return slot;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o); // TODO
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // TODO
    }
}
