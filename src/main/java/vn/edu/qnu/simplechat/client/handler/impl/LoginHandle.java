package vn.edu.qnu.simplechat.client.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.handler.Command;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;

public class LoginHandle implements Command<LoginRequest> {
    private final AbstractClient client;

    public LoginHandle(AbstractClient client) {
        this.client = client;
    }

    @Override
    public void execute(LoginRequest packet) throws Exception {
        client.sendToServer(packet);
    }
}
