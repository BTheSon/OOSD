package vn.edu.qnu.simplechat.client.handler.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.handler.Command;
import vn.edu.qnu.simplechat.client.utils.Terminal;
import vn.edu.qnu.simplechat.shared.protocol.response.ErrorMessage;

public class ErrorMessageHandle implements Command<ErrorMessage> {
    @Override
    public void execute(ErrorMessage packet, AbstractClient client) throws Exception {
        Terminal.getInstance().print(packet.msg(), Terminal.RED);
    }
}
