package vn.edu.qnu.simplechat.client.handler;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

public class ClientCommandRegistry {

    private final Map<Class<?>, Command<?>> commands = new HashMap<>();

    public <T extends Packet> void register(
            Class<T> type,
            Command<T> command
    ) {
        commands.put(type, command);
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> void dispatch(
            T packet
    ) throws Exception {
        Command<T> cmd = (Command<T>) commands.get(packet.getClass());
        if (cmd != null) {
            cmd.execute(packet);
        }
    }
}
