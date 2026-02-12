package vn.edu.qnu.simplechat.client;

import vn.edu.qnu.simplechat.client.handler.impl.LoginHandle;
import vn.edu.qnu.simplechat.client.handler.impl.RegisterHandle;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateAccountRequest;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 2357; // Example port, change if your server uses a different one

        Client client = new Client(host, port);
        client.clientCommandRegistry.register(LoginRequest.class, new LoginHandle(client));
        // Register CreateAccountRequest with a RegisterCommand. The RegisterCommand will handle sending the request.
        client.clientCommandRegistry.register(CreateAccountRequest.class, new RegisterHandle(client));


        var terminal = client.terminal;
        boolean isRunning = true;
        try {
            client.openConnection();
            while (isRunning) {
                String input = terminal.readLine("You: ");

                if (input != null) {
                    if (input.equalsIgnoreCase("/exit")) {
                        terminal.print("Đang thoát...", cli.Terminal.RED);
                        isRunning = false;
                    } else if (input.startsWith("/login ")) {
                        String username = input.substring("/login ".length());
                        LoginRequest loginRequest = new LoginRequest(username);
                        client.clientCommandRegistry.dispatch(loginRequest);
                    } else if (input.startsWith("/register ")) {
                        String username = input.substring("/register ".length());
                        CreateAccountRequest createAccountRequest = new CreateAccountRequest(username);
                        client.clientCommandRegistry.dispatch(createAccountRequest);
                    } else {
                        client.sendToServer(input);
                    }
                }
            }
        } catch (Exception e) { // Changed to generic Exception to catch dispatch exceptions
            System.err.println("Client failed to connect or communicate: " + e.getMessage());
        }
        finally {
            client.closeConnection();
        }
    }
}
