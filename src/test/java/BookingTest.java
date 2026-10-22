import org.junit.jupiter.api.Test;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;

// YOU WRITE THE TESTS (Part B step 2). One test per test: commit.
// e.g. getters return what was passed in; same user+room+slot are equal
// with the same hashCode; a different user (or room, or slot) is not equal.
class BookingTest {

    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(LocalDateTime.of(2027, 1, 11, startHour, 0),
                            LocalDateTime.of(2027, 1, 11, endHour, 0));
    }

    @Test
    void gettersReturnUserRoomAndSlot() {
        Booking booking = new Booking("alice", "R1", slot(10, 11));
        assertEquals("alice", booking.getUserId());
        assertEquals("R1", booking.getRoomId());
        assertEquals(slot(10, 11), booking.getSlot());
    }

    @Test
    void sameUserRoomAndSlotAreEqualWithSameHashCode() {
        Booking a = new Booking("alice", "R1", slot(10, 11));
        assertEquals(a, new Booking("alice", "R1", slot(10, 11)));
        assertEquals(a.hashCode(), new Booking("alice", "R1", slot(10, 11)).hashCode());
        assertNotEquals(a, new Booking("bob", "R1", slot(10, 11)));     // different user
        assertNotEquals(a, new Booking("alice", "R2", slot(10, 11)));   // different room
        assertNotEquals(a, new Booking("alice", "R1", slot(10, 12)));   // different slot
    }

}
