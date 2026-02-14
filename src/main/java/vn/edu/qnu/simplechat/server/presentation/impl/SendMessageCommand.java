package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.RoomConnectionRegistry;
import vn.edu.qnu.simplechat.server.presentation.ServerCommand;
import vn.edu.qnu.simplechat.shared.protocol.request.SendMessageRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.ChatMessageResponse;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class SendMessageCommand implements ServerCommand<SendMessageRequest> {
    private final RoomConnectionRegistry roomConnectionRegistry;

    public SendMessageCommand(RoomConnectionRegistry roomConnectionRegistry) {
        this.roomConnectionRegistry = roomConnectionRegistry;
    }
    @Override
    public void execute(SendMessageRequest packet, ConnectionToClient client) throws Exception {

        String username = (String) client.getInfo("username");
        if (username == null) {
            client.sendToClient(new MessageFromServer("Not logged in. Please log in first."));
            return;
        }

        String roomId = (String) client.getInfo("roomId");
        if (roomId == null) {
            client.sendToClient(new MessageFromServer("You have not joined any room."));
            return;
        }

        var connections = roomConnectionRegistry.getConnectionByRoomId(roomId);
        if (connections == null || connections.isEmpty()) {
            client.sendToClient(new MessageFromServer("Room does not exist or has no members."));
            return;
        }

        String msg = packet.msg();
        if (msg == null || msg.isBlank()) {
            return; // ignore empty message
        }

        for (var connection : connections) {
            boolean isMe = connection.equals(client);
            connection.sendToClient(new ChatMessageResponse(username, msg, isMe));
        }
    }

}
