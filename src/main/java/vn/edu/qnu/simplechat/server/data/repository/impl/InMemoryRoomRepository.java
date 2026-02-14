package vn.edu.qnu.simplechat.server.data.repository.impl;

import vn.edu.qnu.simplechat.server.data.entity.Room;
import vn.edu.qnu.simplechat.server.data.repository.RoomRepository;

public class InMemoryRoomRepository extends InMemoryCrudRepository<String, Room> implements RoomRepository {
    private static final InMemoryRoomRepository instance = new InMemoryRoomRepository();

    public static InMemoryRoomRepository getInstance() {
        return instance;
    }

    @Override
    protected String getId(Room entity) {
        return entity.roomId();
    }

    @Override
    public void addMenber(String roomId, String userId) {
        
    }
}
