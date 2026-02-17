package vn.edu.qnu.simplechat.client.application.action.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.application.action.Action;
import vn.edu.qnu.simplechat.client.application.action.ActionSignal;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateRoomRequest;

public class CreateRoomAction  implements Action {

    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        client.sendToServer(new CreateRoomRequest(input));
        return ActionSignal.NONE;
    }
}
