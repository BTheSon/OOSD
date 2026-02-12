package vn.edu.qnu.simplechat.server.presentation;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.shared.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

public class ServerCommandRegistry {

    private final Map<Class<?>, ServerCommand<?>> commands = new HashMap<>();

    public <T extends Packet> void register(
            Class<T> type,
            ServerCommand<T> command
    ) {
        commands.put(type, command);
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> void dispatch(
            T packet,
            ConnectionToClient client
    ) throws Exception {
        ServerCommand<T> cmd = (ServerCommand<T>) commands.get(packet.getClass());
        if (cmd != null) {
            cmd.execute(packet, client);
        }
    }
}
