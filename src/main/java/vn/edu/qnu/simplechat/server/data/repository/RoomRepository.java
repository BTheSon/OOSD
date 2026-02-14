package vn.edu.qnu.simplechat.server.data.repository;

import vn.edu.qnu.simplechat.server.data.entity.Room;

public interface RoomRepository extends CrudRepository<String, Room> {
    void addMenber(String roomId, String userId);
}
