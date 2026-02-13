package vn.edu.qnu.simplechat.client.ui.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ui.Action;
import vn.edu.qnu.simplechat.client.ui.ActionSignal;

public class SendMessageHandle implements Action {
    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        client.sendToServer(input);
        return ActionSignal.SUCCESS;
    }
}
