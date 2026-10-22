import java.time.*;
import java.util.Objects;

// YOU WRITE THIS (Part A), test first.
// Stubs only: they compile but do nothing, so every new test starts red.
//
// A1 start and end not null, end strictly after start, else IllegalArgumentException
// A2 duration() is the time from start to end
// A3 a slot includes its start but NOT its end (10-11 and 11-12 do not overlap)
// A4 a.overlaps(b) == b.overlaps(a)
// A5 same start and end -> equal and same hashCode
public final class TimeSlot {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end must not be null");
        }
        if(!end.isAfter(start)){
            throw new IllegalArgumentException("End must be after start");
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end; 
    }

    public Duration duration() {
        return Duration.between(start, end);
    }

    public boolean overlaps(TimeSlot other) {
        return  start.isBefore(other.end) && other.start.isBefore(end);
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot other = (TimeSlot) o;
        return start.equals(other.start) && end.equals(other.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return super.toString(); // TODO
    }
}
