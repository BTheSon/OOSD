package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.RoomConnectionRegistry;
import vn.edu.qnu.simplechat.server.data.entity.Message;
import vn.edu.qnu.simplechat.server.data.repository.RoomRepository;
import vn.edu.qnu.simplechat.server.presentation.ServerCommand;
import vn.edu.qnu.simplechat.shared.protocol.request.SendMessageRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.ChatMessageResponse;
import vn.edu.qnu.simplechat.shared.protocol.response.ErrorMessage;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class SendMessageCommand implements ServerCommand<SendMessageRequest> {
    private final RoomConnectionRegistry roomConnectionRegistry;
    private final RoomRepository roomRepo;

    public SendMessageCommand(RoomConnectionRegistry roomConnectionRegistry, RoomRepository repo) {
        this.roomConnectionRegistry = roomConnectionRegistry;
        this.roomRepo = repo;
    }
    @Override
    public void execute(SendMessageRequest packet, ConnectionToClient client) throws Exception {

        String username = (String) client.getInfo("username");
        if (username == null) {
            client.sendToClient(new ErrorMessage("Not logged in. Please log in first."));
            return;
        }

        String roomId = (String) client.getInfo("roomId");
        if (roomId == null) {
            client.sendToClient(new MessageFromServer("You have not joined any room."));
            return;
        }

        var connections = roomConnectionRegistry.getConnectionByRoomId(roomId);
        if (connections == null || connections.isEmpty()) {
            client.sendToClient(new ErrorMessage("Room does not exist or has no members."));
            return;
        }

        String msg = packet.msg();
        if (msg == null || msg.isBlank()) {
            return; // ignore empty message
        }

        try {
            roomRepo.addNewMsg(roomId, username, msg);
        } catch (RuntimeException e) {
            client.sendToClient(e.getMessage());
            return;
        }

        for (var connection : connections) {
            // lí do tồn tại
            // vì cái handle disconect phế lòi kia mà có thể sẽ có client là null
            // tai sao debug connection là null nhưng nó lại chui vào dc :vvv
            if (connection.isAlive()) {
                boolean isMe = connection.equals(client);
                connection.sendToClient(new ChatMessageResponse(username, msg, isMe));
            }
        }
    }

}
