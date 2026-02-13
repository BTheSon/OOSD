package vn.edu.qnu.simplechat.client.ui.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ui.Action;
import vn.edu.qnu.simplechat.client.ui.ActionSignal;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;

public class LoginAction  implements Action {

    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        client.sendToServer(new LoginRequest(input));
        return ActionSignal.NONE;
    }
}
