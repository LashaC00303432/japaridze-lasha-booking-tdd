import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// YOU WRITE THE TESTS (Part B step 1). One test per test: commit.
// e.g. new Room("R1", "Room 1") has id "R1" and name "Room 1".
class RoomTest {
    @Test
    void getIdReturnsTheId() {
        Room room = new Room("R1", "Room 1");
        assertEquals("R1", room.getId());
    }
}
