package vn.edu.qnu.simplechat.shared.protocol.response;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record ChatMessageResponse (
        String sender,
        String msg,
        boolean isMe
) implements Packet {
}
