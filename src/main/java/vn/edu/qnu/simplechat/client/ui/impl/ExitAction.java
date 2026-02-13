package vn.edu.qnu.simplechat.client.ui.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.ui.Action;
import vn.edu.qnu.simplechat.client.ui.ActionSignal;

public class ExitAction implements Action {

    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        return ActionSignal.EXIT;
    }
}
