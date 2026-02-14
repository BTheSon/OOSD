package vn.edu.qnu.simplechat.shared.protocol.request;

import vn.edu.qnu.simplechat.server.presentation.impl.CreateRoomCommand;
import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record SendMessageRequest(
        String msg
) implements Packet {
}
