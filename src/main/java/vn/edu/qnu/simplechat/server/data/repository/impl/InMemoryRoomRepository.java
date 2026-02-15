package vn.edu.qnu.simplechat.server.data.repository.impl;

import vn.edu.qnu.simplechat.server.data.entity.Room;
import vn.edu.qnu.simplechat.server.data.repository.RoomRepository;

public class InMemoryRoomRepository extends InMemoryCrudRepository<String, Room> implements RoomRepository {
    private static final InMemoryRoomRepository instance = new InMemoryRoomRepository();
    private InMemoryRoomRepository(){}

    public static InMemoryRoomRepository getInstance() {
        return instance;
    }

    @Override
    protected String getId(Room entity) {
        return entity.roomId();
    }

    @Override
    public void addMenber(String roomId, String username) {
        Room room = this.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room không tồn tại"));

        Room updated = room.addMember(username);

        this.update(roomId, updated); // ghi đè
    }
}
