package vn.edu.qnu.simplechat.client.Controller;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.shared.handler.Command;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;

public class HandleLogin implements Command<LoginResponse> {

    @Override
    public void execute(LoginResponse packet, ConnectionToClient client) throws Exception {

    }
}
