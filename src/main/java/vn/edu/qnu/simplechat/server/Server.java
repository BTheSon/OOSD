package vn.edu.qnu.simplechat.server;

import  ocsf.server.*;
import vn.edu.qnu.simplechat.server.data.repository.impl.InMemoryUserRepository;
import vn.edu.qnu.simplechat.shared.handler.CommandRegistry;
import vn.edu.qnu.simplechat.server.presentation.CreateAccountCommand;
import vn.edu.qnu.simplechat.server.presentation.LoginCommand;
import vn.edu.qnu.simplechat.shared.protocol.Packet;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateAccountRequest;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;

import java.io.IOException;

public class Server extends AbstractServer {

    private final CommandRegistry registry = new CommandRegistry();

    public Server(int port) {
        super(port);
        var userRepo = InMemoryUserRepository.getInstance();

        registry.register(LoginRequest.class, new LoginCommand(userRepo));
        registry.register(CreateAccountRequest.class, new CreateAccountCommand(userRepo));
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        try {
            if (msg instanceof String m) {
                client.sendToClient(m);
            } else if (msg instanceof Packet packet) {
                registry.dispatch(packet, client);
            } else {
                throw new Exception("khong xac dinh kieu cua goi tin");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    synchronized protected void clientConnected(ConnectionToClient client) {
        System.out.println("new client has connected");
    }
    @Override
    synchronized protected void clientDisconnected( ConnectionToClient client) {
        super.clientDisconnected(client);
        System.out.println("Disconection: " + client.toString());
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        super.clientException(client, exception);
        System.out.println("Disconection: " + client.toString());
    }

    public static void main(String[] args) {
        final int port = 2357;
        AbstractServer server = new Server(port);
        try {
            server.listen();
            System.out.println("server is running...");
            int a = System.in.read();
            server.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
