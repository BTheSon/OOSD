package vn.edu.qnu.simplechat.server.data.repository.impl;

import vn.edu.qnu.simplechat.server.data.entity.Message;
import vn.edu.qnu.simplechat.server.data.entity.Room;
import vn.edu.qnu.simplechat.server.data.repository.RoomRepository;

public class InMemoryRoomRepository
        extends InMemoryCrudRepository<String, Room>
        implements RoomRepository {

    private static final InMemoryRoomRepository instance =
            new InMemoryRoomRepository();

    private InMemoryRoomRepository(){}

    public static InMemoryRoomRepository getInstance() {
        return instance;
    }

    @Override
    protected String getId(Room entity) {
        return entity.roomId();
    }

    @Override
    public void addMember(String roomId, String username) {

        Room room = this.findById(roomId)
                .orElseThrow(() ->
                        new RuntimeException("Room không tồn tại"));

        room.addMember(username);
    }

    @Override
    public void addNewMsg(String roomId, String username, String msg) {

        Room room = this.findById(roomId)
                .orElseThrow(() ->
                        new RuntimeException("Room không tồn tại"));

        Message newMsg = new Message(roomId, username, msg);

        room.addMessage(newMsg);    // mutate trực tiếp
    }
}
