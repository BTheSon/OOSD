package vn.edu.qnu.simplechat.client.inbound.handler;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.shared.protocol.Packet;

import java.util.HashMap;
import java.util.Map;

public class ResponseRegistry {

    private final Map<Class<?>, ResponseHandler<?>> commands = new HashMap<>();

    public <T extends Packet> void register(
            Class<T> type,
            ResponseHandler<T> responseHandler
    ) {
        commands.put(type, responseHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> void dispatch(
            T packet,
            AbstractClient client
    ) throws Exception {
        ResponseHandler<T> cmd = (ResponseHandler<T>) commands.get(packet.getClass());
        if (cmd != null) {
            cmd.execute(packet, client);
        }
    }
}
