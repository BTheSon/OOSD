package vn.edu.qnu.simplechat.client.ui.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ui.Action;
import vn.edu.qnu.simplechat.client.ui.ActionSignal;
import vn.edu.qnu.simplechat.shared.protocol.request.JoinRoomRequest;

public class JoinRoomAction  implements Action {

    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        client.sendToServer(new JoinRoomRequest(input, "dd"));
        return ActionSignal.NONE;
    }
}
