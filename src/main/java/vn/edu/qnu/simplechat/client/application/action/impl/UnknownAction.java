package vn.edu.qnu.simplechat.client.application.action.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.application.action.Action;
import vn.edu.qnu.simplechat.client.application.action.ActionSignal;
import vn.edu.qnu.simplechat.client.ui.Terminal;

public class UnknownAction implements Action {
    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        Terminal.getInstance().print("[System]: Unknown command", Terminal.RED);
        return ActionSignal.NONE;
    }
}
