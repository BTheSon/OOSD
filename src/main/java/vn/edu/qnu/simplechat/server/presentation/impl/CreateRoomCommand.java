package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.data.entity.Room;
import vn.edu.qnu.simplechat.server.data.repository.RoomRepository;
import vn.edu.qnu.simplechat.server.presentation.ServerCommand;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateRoomRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

import java.util.HashSet;

public class CreateRoomCommand implements ServerCommand<CreateRoomRequest> {

    private final RoomRepository roomRepository;
    public  CreateRoomCommand(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public void execute(CreateRoomRequest packet, ConnectionToClient client) throws Exception {
        var roomId = packet.roomId();
        try {
            if(roomId.isBlank()) {
                client.sendToClient(new MessageFromServer("The roomId must contain a character."));
                return;
            }

            var isLogin = client.getInfo("username") != null;
            if (!isLogin) {
                client.sendToClient(new MessageFromServer("Not logged in, please log in to use the feature."));
                return;
            }

            var newRoom = new Room(packet.roomId(), new HashSet<>());

            roomRepository.save(newRoom);
            client.sendToClient("room id [" + newRoom.roomId() + "] has been create.");

        } catch (IllegalStateException e) {
            client.sendToClient(new MessageFromServer("room id [" + roomId + "] is exist!"));
        }
    }
}
