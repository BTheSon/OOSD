package vn.edu.qnu.simplechat.client.handler.impl;


import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.handler.Command;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.response.ChatMessageResponse;

public class ChatMessageHandle implements Command<ChatMessageResponse> {
    @Override
    public void execute(ChatMessageResponse packet, AbstractClient client) throws Exception {
        var msg = packet.msg();
        var sender = packet.sender();
        var isme = packet.isMe();
        Terminal.getInstance().print("[" + sender + "]: " + msg);
    }
}
