package vn.edu.qnu.simplechat.client.handler.impl;

import vn.edu.qnu.simplechat.client.handler.Command;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class MessageServerHandle implements Command<MessageFromServer> {
    @Override
    public void execute(MessageFromServer packet) throws Exception {
    }
}
