package vn.edu.qnu.simplechat.shared.protocol.request;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record JoinRoomRequest (
        String roomId,
        String username
) implements Packet { }
