import java.util.*;

// GIVEN (Appendix). Do not change.
interface IRoomRepository {
    Room findById(String id);          // null if no such room
    void save(Room room);
    List<Room> findAll();
}
