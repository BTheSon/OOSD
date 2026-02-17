package vn.edu.qnu.simplechat.client.ui;

public class View {
    private static final Terminal terminal= Terminal.getInstance();

    public static void message(String sender, String msg, boolean isMe) {
        terminal.print("[" + sender + "]: " + msg, isMe ? Terminal.CYAN : Terminal.WHITE);
    }
    public static void serverError(String msg) {
        terminal.print("[Server]: " + msg, Terminal.RED);
    }
    public static void serverLog(String msg) {
        terminal.print("[Server]: " + msg, Terminal.GREEN);
    }
    public static void systemLog(String msg) {
        terminal.print("[System]: " + msg, Terminal.GREEN);
    }
}
