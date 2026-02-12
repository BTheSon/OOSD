package vn.edu.qnu.simplechat.client.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.handler.Command;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateAccountRequest;

public class RegisterHandle implements Command<CreateAccountRequest> {
    private final AbstractClient client;

    public RegisterHandle(AbstractClient client) {
        this.client = client;
    }

    @Override
    public void execute(CreateAccountRequest packet) throws Exception {
        client.sendToServer(packet);
    }
}