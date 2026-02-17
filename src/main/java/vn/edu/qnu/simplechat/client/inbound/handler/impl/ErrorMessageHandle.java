package vn.edu.qnu.simplechat.client.inbound.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.inbound.handler.ResponseHandler;
import vn.edu.qnu.simplechat.client.ui.Terminal;
import vn.edu.qnu.simplechat.client.ui.View;
import vn.edu.qnu.simplechat.shared.protocol.response.ErrorMessage;

public class ErrorMessageHandle implements ResponseHandler<ErrorMessage> {
    @Override
    public void execute(ErrorMessage packet, AbstractClient client) throws Exception {
        View.serverError(packet.msg());
    }
}
