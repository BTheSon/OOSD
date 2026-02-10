package vn.edu.qnu.simplechat.client;

import java.io.IOException;
import  ocsf.client.*;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;

public class Client extends AbstractClient {

    public Client(String host, int port) {
        super(host, port);
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

        try {
            client.openConnection();
            // Simulate some activity then close
            Thread.sleep(2000); // Wait for 2 seconds
            client.sendToServer("Hello Server!");
            client.sendToServer(new LoginRequest("so"));
            Thread.sleep(1000); // Wait for 1 second
            client.closeConnection();
        } catch (IOException e) {
            System.err.println("Client failed to connect or communicate: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Client interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
