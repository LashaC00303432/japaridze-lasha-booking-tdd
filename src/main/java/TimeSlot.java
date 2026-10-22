import java.time.*;

// YOU WRITE THIS (Part A), test first.
// Stubs only: they compile but do nothing, so every new test starts red.
//
// A1 start and end not null, end strictly after start, else IllegalArgumentException
// A2 duration() is the time from start to end
// A3 a slot includes its start but NOT its end (10-11 and 11-12 do not overlap)
// A4 a.overlaps(b) == b.overlaps(a)
// A5 same start and end -> equal and same hashCode
public final class TimeSlot {

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end must not be null");
        }
        if(!end.isAfter(start)){
            throw new IllegalArgumentException("End must be after start");
        }
    }

    public LocalDateTime getStart() {
        return null; // TODO
    }

    public LocalDateTime getEnd() {
        return null; // TODO
    }

    public Duration duration() {
        return null; // TODO
    }

    public boolean overlaps(TimeSlot other) {
        return false; // TODO
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o); // TODO
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // TODO
    }

    @Override
    public String toString() {
        return super.toString(); // TODO
    }
}
