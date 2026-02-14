package vn.edu.qnu.simplechat.client.inbound.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ClientSession;
import vn.edu.qnu.simplechat.client.inbound.handler.ResponseHandler;
import vn.edu.qnu.simplechat.client.ui.Terminal;
import vn.edu.qnu.simplechat.client.ui.View;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;

public class LoginHandle implements ResponseHandler<LoginResponse> {
    private final Terminal terminal = Terminal.getInstance();

    @Override
    public void execute(LoginResponse packet, AbstractClient client) throws Exception {
        if (packet.isSuccess())
            ClientSession.setIsLoggedIn(true);
        View.serverLog(packet.message());
    }
}
