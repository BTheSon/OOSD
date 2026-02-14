package vn.edu.qnu.simplechat.client.application.action.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.application.action.Action;
import vn.edu.qnu.simplechat.client.application.action.ActionSignal;
import vn.edu.qnu.simplechat.shared.protocol.request.SendMessageRequest;

public class SendMessageAction implements Action {
    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        client.sendToServer(new SendMessageRequest(input));
        return ActionSignal.SUCCESS;
    }
}
