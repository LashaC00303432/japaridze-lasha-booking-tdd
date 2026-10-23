import org.junit.jupiter.api.*;
import java.time.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// YOU WRITE THE TESTS (Part B steps 5-7). One test per test: commit.
//
// Step 5: uncomment the GIVEN first test below.
// Step 6: one cycle per rule, in this order:
//   R1 room must exist                       -> IllegalArgumentException  (book "R9")
//   R2 slot must be in the future (clock)    -> IllegalArgumentException  (book 08-09)
//   R3 at most MAX_SLOT (3 hours)            -> IllegalArgumentException  (book 10-14)
//   R4 no overlapping bookings in a room     -> IllegalStateException     (alice R1 10-12, bob R1 11-13)
//   R5 a user can't hold overlapping bookings-> IllegalStateException     (alice R1 10-11, then R2 10-11)
//   R6 cancel a missing booking              -> IllegalArgumentException; cancelling frees the slot
//   R7 bookingsFor is in start-time order    (book 14-15, then 10-11 -> list is 10-11, 14-15)
// Step 7 boundary tests: exactly 3 hours (allowed); touching 10-11 then 11-12 in the
//   same room (allowed); same user, same slot, two rooms (rejected by R5).
//   If one passes straight away, commit it as confirm: not test:.
//
// Exception example:
//   assertThrows(IllegalArgumentException.class, () -> service.book("alice", "R9", slot(10, 11)));
class BookingServiceTest {
    // "Now" is always 09:00 on 11 January 2027 in these tests.
    private static final Clock CLOCK =
        Clock.fixed(Instant.parse("2027-01-11T09:00:00Z"), ZoneOffset.UTC);

    private BookingService service;

    @BeforeEach
    void setUp() {
        MemoryRoomRepository rooms = new MemoryRoomRepository();
        rooms.save(new Room("R1", "Room 1"));
        rooms.save(new Room("R2", "Room 2"));
        service = new BookingService(rooms, new MemoryBookingRepository(), CLOCK);
    }

    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(LocalDateTime.of(2027, 1, 11, startHour, 0),
                            LocalDateTime.of(2027, 1, 11, endHour, 0));
    }

    @Test
    void bookingAFreeRoomReturnsTheBooking() {
        Booking booking = service.book("alice", "R1", slot(10, 11));
        assertEquals(new Booking("alice", "R1", slot(10, 11)), booking);
    }

    @Test
    void bookingAnUnknownRoomIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.book("alice", "R9", slot(10, 11)));
    }

    @Test
    void bookingASlotInThePastIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.book("alice", "R1", slot(8, 9)));
    }

    @Test
    void bookingLongerThanThreeHoursIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.book("alice", "R1", slot(10, 14)));
    }

    @Test
    void overlappingBookingInSameRoomIsRejected() {
        service.book("alice", "R1", slot(10, 12));
        assertThrows(IllegalStateException.class, () -> service.book("bob", "R1", slot(11, 13)));
    }
    
    @Test
    void userCannotHoldOverlappingBookingsInDifferentRooms() {
        service.book("alice", "R1", slot(10, 11));
        assertThrows(IllegalStateException.class, () -> service.book("alice", "R2", slot(10, 11)));
    }

    @Test
    void cancellingAMissingBookingIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.cancel("alice", "R1", slot(10, 11)));
    }

    @Test
    void cancellingFreesTheSlot() {
        service.book("alice", "R1", slot(10, 11));
        service.cancel("alice", "R1", slot(10, 11));
        Booking booking = service.book("bob", "R1", slot(10, 11));
        assertEquals(new Booking("bob", "R1", slot(10, 11)), booking);
    }

    @Test
    void bookingsForReturnsUsersBookingsInStartOrder() {
        service.book("alice", "R1", slot(14, 15));
        service.book("alice", "R1", slot(10, 11));
        service.book("bob", "R2", slot(12, 13));
        assertEquals(List.of(new Booking("alice", "R1", slot(10, 11)),
                            new Booking("alice", "R1", slot(14, 15))),
                    service.bookingsFor("alice"));
    }

    @Test
    void bookingExactlyThreeHoursIsAllowed() {
        Booking booking = service.book("alice", "R1", slot(10, 13));
        assertEquals(new Booking("alice", "R1", slot(10, 13)), booking);
    }
}
