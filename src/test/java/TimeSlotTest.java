import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;

// YOU WRITE THE TESTS (Part A). One test per test: commit.
//
// Suggested order:
//   1. end before start is rejected        (GIVEN below, uncomment it)
//   2. end equal to start is rejected
//   3. null start is rejected, then null end (two cycles)
//   4. duration() of 10:00-11:30 is 90 minutes
//   5. the parameterised overlap test       (GIVEN below, add 4 rows)
//   6. equal slots are equal + same hashCode; different slots are not equal
class TimeSlotTest {

    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(LocalDateTime.of(2027, 1, 11, startHour, 0),
                            LocalDateTime.of(2027, 1, 11, endHour, 0));
    }

    // GIVEN: worked example (rule A1). Uncomment for your first test: commit.
    @Test
    void endBeforeStartIsRejected() {
         LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
         LocalDateTime nine = LocalDateTime.of(2027, 1, 11, 9, 0);
         assertThrows(IllegalArgumentException.class, () -> new TimeSlot(ten, nine));
     }
    @Test
    void endEqualToStartIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(ten, ten));
    }

    @Test
    void nullStartIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(null, ten));
    }

    @Test
    void nullEndIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(ten, null));
    }

    @Test
    void gettersReturnStartAndEnd() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        LocalDateTime eleven = LocalDateTime.of(2027, 1, 11, 11, 0);
        TimeSlot slot = new TimeSlot(ten, eleven);
        assertEquals(ten, slot.getStart());
        assertEquals(eleven, slot.getEnd());
    }

    @Test
    void durationOfTenToElevenThirtyIsNinetyMinutes() {
        TimeSlot slot = new TimeSlot(LocalDateTime.of(2027, 1, 11, 10, 0),LocalDateTime.of(2027, 1, 11, 11, 30));
        assertEquals(Duration.ofMinutes(90), slot.duration());
    }
    
    @ParameterizedTest(name = "{0}-{1} vs {2}-{3} overlaps: {4}")
    @CsvSource({
        "10, 11, 11, 12, false",   // touching
        "10, 12, 11, 13, true",    // partly overlapping
        "10, 11, 12, 13, false",   // a before b
        "11, 12, 10, 13, true",    // a inside b
        "10, 11, 10, 11, true",    // identical
        "12, 13, 10, 11, false"    // a after b
    })

    void overlapCases(int aStart, int aEnd, int bStart, int bEnd, boolean expected) {
        assertEquals(expected, slot(aStart, aEnd).overlaps(slot(bStart, bEnd)));
        assertEquals(expected, slot(bStart, bEnd).overlaps(slot(aStart, aEnd)));  // A4
    }

    @Test
    void sameStartAndEndAreEqualWithSameHashCode() {
        assertEquals(slot(10, 11), slot(10, 11));
        assertEquals(slot(10, 11).hashCode(), slot(10, 11).hashCode());
        assertNotEquals(slot(10, 11), slot(10, 12));   // different end
        assertNotEquals(slot(10, 11), slot(9, 11));    // different start
    }
    @Test
    void toStringShowsStartAndEnd() {
        String text = slot(10, 11).toString();
        assertTrue(text.contains("2027-01-11T10:00"));
        assertTrue(text.contains("2027-01-11T11:00"));
    }
}
