package vn.edu.qnu.simplechat.client;

import ocsf.client.*;
import vn.edu.qnu.simplechat.client.inbound.handler.ResponseRegistry;
import vn.edu.qnu.simplechat.client.inbound.handler.impl.ChatMessageHandle;
import vn.edu.qnu.simplechat.client.inbound.handler.impl.ErrorMessageHandle;
import vn.edu.qnu.simplechat.client.inbound.handler.impl.LoginHandle;
import vn.edu.qnu.simplechat.client.inbound.handler.impl.MessageServerHandle;
import vn.edu.qnu.simplechat.client.ui.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.Packet;
import vn.edu.qnu.simplechat.shared.protocol.response.ChatMessageResponse;
import vn.edu.qnu.simplechat.shared.protocol.response.ErrorMessage;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class Client extends AbstractClient {

    private final ResponseRegistry clientCommandRegistry = new ResponseRegistry();
    private final Terminal terminal;
    public Client(String host, int port) {
        super(host, port);
        terminal = Terminal.getInstance();
        clientCommandRegistry.register(LoginResponse.class, new LoginHandle());
        clientCommandRegistry.register(MessageFromServer.class, new MessageServerHandle());
        clientCommandRegistry.register(ChatMessageResponse.class, new ChatMessageHandle());
        clientCommandRegistry.register(ErrorMessage.class, new ErrorMessageHandle());
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        try {
            if (msg instanceof String m) {
                terminal.print("[Server] : " + m);
            } else if (msg instanceof Packet packet) {
                clientCommandRegistry.dispatch(packet, this);
            } else {
                throw new Exception("khong xac dinh kieu cua goi tin");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void connectionClosed() {
        System.out.println("Connection to server closed. Goodbye!");
    }

    @Override
    protected void connectionException(Exception exception) {
        System.err.println("Connection exception: " + exception.getMessage());
        // Optionally, attempt to reconnect or inform the user
    }

    @Override
    protected void connectionEstablished() {
        System.out.println("Connection to server established.");
    }
}
