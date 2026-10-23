// R5: a user cannot hold two overlapping bookings, in any rooms.
class UserMustBeFree implements BookingRule {
    private final IBookingRepository bookings;

    UserMustBeFree(IBookingRepository bookings) {
        this.bookings = bookings;
    }

    public void check(String userId, String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(userId + " already has a booking for " + slot);
            }
        }
    }
}