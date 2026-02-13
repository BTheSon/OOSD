package vn.edu.qnu.simplechat.client.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ClientSession;
import vn.edu.qnu.simplechat.client.handler.Command;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;

public class LoginHandle implements Command<LoginResponse> {
    private final Terminal terminal = Terminal.getInstance();

    @Override
    public void execute(LoginResponse packet, AbstractClient client) throws Exception {
        if (packet.isSuccess())
            ClientSession.setIsLoggedIn(true);

        terminal.print("[sever]: " + packet.message(), packet.isSuccess() ? Terminal.GREEN : Terminal.RED);
    }
}
