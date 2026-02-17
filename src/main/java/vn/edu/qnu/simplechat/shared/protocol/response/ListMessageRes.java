package vn.edu.qnu.simplechat.shared.protocol.response;

import vn.edu.qnu.simplechat.server.data.entity.Message;
import vn.edu.qnu.simplechat.shared.protocol.Packet;

import java.util.Queue;

public record ListMessageRes(
        Queue<Message> messages
) implements Packet {
}
