package vn.edu.qnu.simplechat.client.handler;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public interface Command<T extends Packet> {
    void execute(T packet) throws Exception;
}
