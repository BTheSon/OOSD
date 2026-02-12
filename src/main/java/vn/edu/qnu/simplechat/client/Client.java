package vn.edu.qnu.simplechat.client;

import ocsf.client.*;
import vn.edu.qnu.simplechat.client.handler.ClientCommandRegistry;
import vn.edu.qnu.simplechat.client.handler.impl.LoginHandle;
import vn.edu.qnu.simplechat.client.handler.impl.RegisterHandle;
import vn.edu.qnu.simplechat.client.handler.HandleLogin;
import vn.edu.qnu.simplechat.client.handler.HandleServerMessage;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.Packet;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateAccountRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class Client extends AbstractClient {

    public final ClientCommandRegistry clientCommandRegistry = new ClientCommandRegistry();
    public final Terminal terminal = Terminal.getInstance();
    public Client(String host, int port) {

        super(host, port);
        clientCommandRegistry.register(LoginResponse.class, new LoginHandle(terminal));
        clientCommandRegistry.register(CreateAccountRequest.class, new RegisterHandle(this));
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        try {
            if (msg instanceof String m) {
                terminal.print("[Server] : " + m);
            } else if (msg instanceof Packet packet) {
                clientCommandRegistry.dispatch(packet);
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
