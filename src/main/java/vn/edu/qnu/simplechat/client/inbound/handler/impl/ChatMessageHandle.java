package vn.edu.qnu.simplechat.client.inbound.handler.impl;


import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.inbound.handler.ResponseHandler;
import vn.edu.qnu.simplechat.client.ui.Terminal;
import vn.edu.qnu.simplechat.client.ui.View;
import vn.edu.qnu.simplechat.shared.protocol.response.ChatMessageResponse;

public class ChatMessageHandle implements ResponseHandler<ChatMessageResponse> {
    @Override
    public void execute(ChatMessageResponse packet, AbstractClient client) throws Exception {
        var msg = packet.msg();
        var sender = packet.sender();
        var isme = packet.isMe();
        View.message(sender, msg, isme);
    }
}
