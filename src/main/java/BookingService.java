import java.time.*;
import java.util.List;

public class BookingService {
    public static final Duration MAX_SLOT = Duration.ofHours(3);

    private final IRoomRepository rooms;
    private final IBookingRepository bookings;
    private final Clock clock;

    public BookingService(IRoomRepository rooms, IBookingRepository bookings, Clock clock) {
        this.rooms = rooms;
        this.bookings = bookings;
        this.clock = clock;
    }

     /** Book a room for a user. Returns the new booking. */
    public Booking book(String userId, String roomId, TimeSlot slot) {
        new RoomMustExist(rooms).check(userId, roomId, slot);
        new SlotMustBeInFuture(clock).check(userId, roomId, slot);
        new SlotMustNotBeTooLong(MAX_SLOT).check(userId, roomId, slot);
        checkRoomFree(roomId, slot);
        checkUserFree(userId, slot);
        Booking booking = new Booking(userId, roomId, slot);
        bookings.save(booking);
        return booking;
    }

    private void checkRoomFree(String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByRoom(roomId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException("room already booked for " + slot);
            }
        }
    }

    private void checkUserFree(String userId, TimeSlot slot) {
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(userId + " already has a booking for " + slot);
            }
        }
    }

    public void cancel(String userId, String roomId, TimeSlot slot) {
        Booking booking = new Booking(userId, roomId, slot);
        if (!bookings.findByUser(userId).contains(booking)) {
            throw new IllegalArgumentException("no such booking");
        }
        bookings.delete(booking);
    }

    public List<Booking> bookingsFor(String userId) {
        return bookings.findByUser(userId).stream().sorted((a, b) -> a.getSlot().getStart().compareTo(b.getSlot().getStart())).toList();
    }
}
