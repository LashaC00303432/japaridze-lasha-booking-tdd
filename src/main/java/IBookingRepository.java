import java.util.*;

// GIVEN (Appendix). Do not change.
interface IBookingRepository {
    void save(Booking booking);
    void delete(Booking booking);      // uses Booking.equals
    List<Booking> findByRoom(String roomId);
    List<Booking> findByUser(String userId);
}
