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
        checkSlotInFuture(slot);
        checkSlotNotTooLong(slot);
        checkRoomFree(roomId, slot);
        checkUserFree(userId, slot);
        Booking booking = new Booking(userId, roomId, slot);
        bookings.save(booking);
        return booking;
    }

    private void checkSlotInFuture(TimeSlot slot) {
        if (slot.getStart().isBefore(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("slot must be in the future");
        }
    }

    private void checkSlotNotTooLong(TimeSlot slot) {
        if (slot.duration().compareTo(MAX_SLOT) > 0) {
            throw new IllegalArgumentException("slot longer than " + MAX_SLOT);
        }
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
