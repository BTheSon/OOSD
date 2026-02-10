package vn.edu.qnu.simplechat.server.presentation;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.shared.protocol.Packet;

public interface Command <T extends Packet> {
    void execute(T packet, ConnectionToClient client);
}
