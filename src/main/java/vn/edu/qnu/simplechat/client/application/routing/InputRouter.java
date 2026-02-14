package vn.edu.qnu.simplechat.client.application.routing;

import ocsf.client.AbstractClient;
import vn.edu.qnu.simplechat.client.application.action.Action;
import vn.edu.qnu.simplechat.client.application.action.ActionSignal;
import vn.edu.qnu.simplechat.client.application.action.impl.UnknownAction;

import java.util.HashMap;
import java.util.Map;

public class InputRouter {
    private final Map<String, Action> routes = new HashMap<>();

    public void register(String key, Action action) {
        routes.put(key, action);
    }

    public ActionSignal route(String route, String input, AbstractClient client) throws  Exception {
        Action action = routes.getOrDefault(route, new UnknownAction());
        return action.execute(input, client);
    }
}
