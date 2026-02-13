package vn.edu.qnu.simplechat.client.ui;

import ocsf.client.AbstractClient;

public interface Action {
    ActionSignal execute(String input, AbstractClient client) throws  Exception;
}
