package vn.edu.qnu.simplechat.client;

import vn.edu.qnu.simplechat.client.ui.ActionSignal;
import vn.edu.qnu.simplechat.client.ui.InputRouter;
import vn.edu.qnu.simplechat.client.ui.impl.*;
import vn.edu.qnu.simplechat.client.utils.InputParser;
import vn.edu.qnu.simplechat.client.utils.Terminal;

import java.io.IOException;

public class Main {
    public static void logHelp(Terminal terminal) {
        terminal.print("""
==================== AVAILABLE COMMANDS ====================

login <username>
    -> dang nhap he thong

new-acc <username>
    -> tao acc moi

create <room-name>
    -> tao phong chat moi

join <room-id>
    -> tham gia phong chat theo id

exit
    -> thoat app

============================================================
""");
    }
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 2357; // Example port, change if your server uses a different one

        Client client = new Client(host, port);
        Terminal terminal = Terminal.getInstance();
        InputRouter inputRouter = new InputRouter();

        // đăng kí hành dộng theo input
        inputRouter.register("send-msg", new SendMessageAction());
        inputRouter.register("login", new LoginAction());
        inputRouter.register("exit", new ExitAction());
        inputRouter.register("new-acc", new CreateAccAction());
        inputRouter.register("create", new CreateRoomAction());
        inputRouter.register("join", new JoinRoomAction());
        logHelp(terminal);
        boolean isRunning = true;
        try {
            client.openConnection();
            while (isRunning) {
                String input = terminal.readLine("You: ");
                var inputParserResult = InputParser.parse(input);

                // nếu k phải lệnh thì chuỷen sang gửi tn
                if (!inputParserResult.isCommand()) {
                    var msg = inputParserResult.getMessage();
                    ActionSignal result = inputRouter.route("send-msg", msg, client);
                    continue;
                }

                var command = inputParserResult.getCommand();
                var agrs = inputParserResult.getArgs();
                ActionSignal result = inputRouter.route(command, agrs, client);

                if (result == ActionSignal.EXIT) {
                    isRunning = false;
                }

            }
        } catch (Exception e) { // Changed to generic Exception to catch dispatch exceptions
            System.err.println("Client failed to connect or communicate: " + e.getMessage());
        }
        finally {
            client.closeConnection();
        }
    }
}
