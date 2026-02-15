package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.RoomConnectionRegistry;
import vn.edu.qnu.simplechat.server.data.repository.impl.InMemoryRoomRepository;
import vn.edu.qnu.simplechat.server.presentation.ServerCommand;
import vn.edu.qnu.simplechat.shared.protocol.request.JoinRoomRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.ErrorMessage;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class JoinRoomCommand implements ServerCommand<JoinRoomRequest> {
    private final RoomConnectionRegistry roomConnectionRegistry;
    private final InMemoryRoomRepository roomRepo;
    public JoinRoomCommand(RoomConnectionRegistry roomConnectionRegistry, InMemoryRoomRepository roomRepo) {
        this.roomConnectionRegistry = roomConnectionRegistry;
        this.roomRepo = roomRepo;
    }
    @Override
    public void execute(JoinRoomRequest packet, ConnectionToClient client) throws Exception {
        var roomId = packet.roomId();
        if (roomId.isBlank()) {
            client.sendToClient(new MessageFromServer("roomId parameter is missing."));
            return;
        }

        // kiểm tra đăng nhập
        var rawwUsername =  client.getInfo("username");
        var isLogin = rawwUsername != null;
        if (!isLogin) {
            client.sendToClient(new MessageFromServer("Not logged in, please log in to use the feature."));
            return;
        }

        // nếu còn trong room khác thì thoát room hiện tại trc
        String currRoomid = (String) client.getInfo("roomId");
        if (currRoomid != null)
            roomConnectionRegistry.leaveRoom(currRoomid, client);

        if (!roomConnectionRegistry.exists(roomId)) {
            client.sendToClient(new ErrorMessage("room [" + roomId + "] is not exist"));
            return;
        }

        roomConnectionRegistry.joinRoom(roomId, client);
//
//        try {
//            roomRepo.addMenber(roomId, rawwUsername.toString());
//        } catch (RuntimeException e) {
//            client.sendToClient(new ErrorMessage(""));
//        }

        client.setInfo("roomId", roomId);
        client.sendToClient("Successfully entered the room.");


    }
}
