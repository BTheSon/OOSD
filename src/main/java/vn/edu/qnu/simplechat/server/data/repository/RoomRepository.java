package vn.edu.qnu.simplechat.server.data.repository;

import vn.edu.qnu.simplechat.server.data.entity.Room;

public interface RoomRepository extends CrudRepository<String, Room> {
    void addMember(String roomId, String userId) throws RuntimeException;
    void addNewMsg(String roomId, String userId, String msg) throws RuntimeException;
}
