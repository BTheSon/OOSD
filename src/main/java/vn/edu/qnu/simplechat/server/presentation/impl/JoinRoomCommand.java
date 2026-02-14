package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.RoomConnectionRegistry;
import vn.edu.qnu.simplechat.server.presentation.ServerCommand;
import vn.edu.qnu.simplechat.shared.protocol.request.JoinRoomRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class JoinRoomCommand implements ServerCommand<JoinRoomRequest> {
    private final RoomConnectionRegistry roomConnectionRegistry;

    public JoinRoomCommand(RoomConnectionRegistry roomConnectionRegistry) {
        this.roomConnectionRegistry = roomConnectionRegistry;
    }
    @Override
    public void execute(JoinRoomRequest packet, ConnectionToClient client) throws Exception {
        var roomId = packet.roomId();
        if (roomId.isBlank()) {
            client.sendToClient(new MessageFromServer("roomId parameter is missing."));
            return;
        }

        // kiểm tra đăng nhập
        var isLogin = client.getInfo("username") != null;
        if (!isLogin) {
            client.sendToClient(new MessageFromServer("Not logged in, please log in to use the feature."));
            return;
        }

        // nếu còn trong room khác thì thoát room hiện tại trc
        String currRoomid = (String) client.getInfo("roomId");
        if (currRoomid != null)
            roomConnectionRegistry.leaveRoom(currRoomid, client);

        roomConnectionRegistry.joinRoom(roomId, client);
        client.setInfo("roomId", roomId);
        client.sendToClient("Successfully entered the room.");
    }
}
