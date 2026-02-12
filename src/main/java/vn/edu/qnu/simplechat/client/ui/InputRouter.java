package vn.edu.qnu.simplechat.client.ui;

import java.util.HashMap;
import java.util.Map;

public class InputRouter {
    private final Map<String, Action> routes = new HashMap<>();

    public void register(String key, Action action) {
        routes.put(key, action);
    }

    public void route(String input) throws Exception {
        String key = input.split(" ")[0];

        Action action = routes.get(key);
        if (action == null) {
            throw new IllegalArgumentException("Unknown action");
        }

        action.execute(input);
    }
}
