package vn.edu.qnu.simplechat.server.presentation;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.shared.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<Class<?>, Command<?>> commands = new HashMap<>();

    public <T extends Packet> void register(
            Class<T> type,
            Command<T> command
    ) {
        commands.put(type, command);
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> void dispatch(
            T packet,
            ConnectionToClient client
    ) throws Exception {
        Command<T> cmd = (Command<T>) commands.get(packet.getClass());
        if (cmd != null) {
            cmd.execute(packet, client);
        }
    }
}
