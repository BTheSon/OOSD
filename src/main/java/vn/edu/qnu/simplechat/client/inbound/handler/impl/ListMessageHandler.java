package vn.edu.qnu.simplechat.client.inbound.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.inbound.handler.ResponseHandler;
import vn.edu.qnu.simplechat.client.ui.View;
import vn.edu.qnu.simplechat.shared.protocol.response.ListMessageRes;

public class ListMessageHandler implements ResponseHandler<ListMessageRes> {
    @Override
    public void execute(ListMessageRes packet, AbstractClient client) throws Exception {
        for (var mess : packet.messages()) {
            var username = mess.username();
            var msg = mess.message();
            View.message(username, msg, false);
        }
    }
}
