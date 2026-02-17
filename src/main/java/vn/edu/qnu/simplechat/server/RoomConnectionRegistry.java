package vn.edu.qnu.simplechat.server;

import ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RoomConnectionRegistry {

    // roomId -> set of clients
    private final ConcurrentHashMap<String, Set<ConnectionToClient>> rooms = new ConcurrentHashMap<>();

    /**
     * Thêm client vào room
     */
    public void joinRoom(String roomId, ConnectionToClient client) {
        rooms
            .computeIfAbsent(roomId, id -> ConcurrentHashMap.newKeySet())
            .add(client);

    }

    /**
     * Rời khỏi room
     */
    public void leaveRoom(String roomId, ConnectionToClient client) {
        Set<ConnectionToClient> clients = rooms.get(roomId);
        if (clients != null) {
            clients.remove(client);

            // Nếu room rỗng thì xóa luôn
            if (clients.isEmpty()) {
                rooms.remove(roomId);
            }
        }

        client.setInfo("roomId", null);
    }

    /**
     * Broadcast message trong room
     */
    public void broadcast(String roomId, Object message) throws IOException{
        Set<ConnectionToClient> clients = rooms.get(roomId);
        if (clients == null) return;

        for (ConnectionToClient client : clients) {
            client.sendToClient(message);
        }
    }

    /**
     * Remove client khỏi mọi room (khi disconnect)
     */
    public void removeClientFromAllRooms(ConnectionToClient client) {
        for (String roomId : rooms.keySet()) {
            leaveRoom(roomId, client);
        }
    }

    /**
     * Lấy room hiện tại của client
     */
    public String getRoomOf(ConnectionToClient client) {
        return (String) client.getInfo("roomId");
    }

    public Set<ConnectionToClient> getConnectionByRoomId(String roomId) {
        return rooms.get(roomId);
    }

    public boolean exists(String roomId) {
        return rooms.containsKey(roomId);
    }
}
