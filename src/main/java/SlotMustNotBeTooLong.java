import java.time.*;
// R3: the slot may last at most max.
class SlotMustNotBeTooLong implements BookingRule {
    private final Duration max;

    SlotMustNotBeTooLong(Duration max) {
        this.max = max;
    }

    public void check(String userId, String roomId, TimeSlot slot) {
        if (slot.duration().compareTo(max) > 0) {
            throw new IllegalArgumentException("slot longer than " + max);
        }
    }
}