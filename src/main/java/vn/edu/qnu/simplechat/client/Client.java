package vn.edu.qnu.simplechat.client;

import java.io.IOException;
import ocsf.client.*;
import vn.edu.qnu.simplechat.client.Controller.HandleServerMessage;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.handler.CommandRegistry;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class Client extends AbstractClient {

    private final CommandRegistry registry = new CommandRegistry();
    private final Terminal terminal = Terminal.getInstance();
    public Client(String host, int port) {
        super(host, port);
        registry.register(MessageFromServer.class, new HandleServerMessage(terminal));
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println("Message from server: " + msg);
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

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 2357; // Example port, change if your server uses a different one

        Client client = new Client(host, port);
        var terminal = client.terminal;
        boolean isRunning = true;
        try {
            client.openConnection();
            while (isRunning) {
                String input = terminal.readLine("You: ");

                if (input != null) {
                    // Kiểm tra lệnh thoát
                    if (input.equalsIgnoreCase("/exit")) {
                        terminal.print("Đang thoát...", cli.Terminal.RED);
                        isRunning = false;
                    }

                    // User dùng màu Xanh lá (GREEN) để phân biệt với Server
                    terminal.print("Me: " + input, cli.Terminal.GREEN);
                }
            }
        } catch (IOException e) {
            System.err.println("Client failed to connect or communicate: " + e.getMessage());
        }
        finally {
            client.closeConnection();
        }
    }

}
