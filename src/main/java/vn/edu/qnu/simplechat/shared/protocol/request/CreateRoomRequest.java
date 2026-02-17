package vn.edu.qnu.simplechat.shared.protocol.request;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record CreateRoomRequest(
        String roomId
) implements Packet{ }
