package vn.edu.qnu.simplechat.client.application.action.impl;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.application.action.Action;
import vn.edu.qnu.simplechat.client.application.action.ActionSignal;

public class ExitAction implements Action {

    @Override
    public ActionSignal execute(String input, AbstractClient client) throws Exception {
        return ActionSignal.EXIT;
    }
}
