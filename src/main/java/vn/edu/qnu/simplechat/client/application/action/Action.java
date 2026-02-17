package vn.edu.qnu.simplechat.client.application.action;

import ocsf.client.AbstractClient;

public interface Action {
    ActionSignal execute(String input, AbstractClient client) throws  Exception;
}
