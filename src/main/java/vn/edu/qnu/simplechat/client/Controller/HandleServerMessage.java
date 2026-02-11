package vn.edu.qnu.simplechat.client.Controller;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.shared.handler.Command;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class HandleServerMessage implements Command<MessageFromServer> {
    private Terminal terminal;
    public HandleServerMessage(Terminal terminal) {
        this.terminal = terminal;
    }
    @Override
    public void execute(MessageFromServer packet, ConnectionToClient client) throws Exception {
        terminal.print("[Server] : " + packet.msg(), Terminal.GREEN);
    }
}
