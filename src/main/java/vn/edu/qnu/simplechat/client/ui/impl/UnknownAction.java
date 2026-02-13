package vn.edu.qnu.simplechat.client.ui.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ui.Action;
import vn.edu.qnu.simplechat.client.ui.ActionSignal;
import vn.edu.qnu.simplechat.client.utils.Terminal;

public class UnknownAction implements Action {
    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        Terminal.getInstance().print("[System]: Unknown command", Terminal.RED);
        return ActionSignal.NONE;
    }
}
