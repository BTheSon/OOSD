package vn.edu.qnu.simplechat.client.inbound.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.inbound.handler.ResponseHandler;
import vn.edu.qnu.simplechat.client.ui.Terminal;
import vn.edu.qnu.simplechat.client.ui.View;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class MessageServerHandle implements ResponseHandler<MessageFromServer> {
    @Override
    public void execute(MessageFromServer packet, AbstractClient client) throws Exception {
        View.serverLog(packet.msg());
    }
}
