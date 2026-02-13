package vn.edu.qnu.simplechat.client;

public class ClientSession {
    private static  boolean isLoggedIn;

    public static void setIsLoggedIn(boolean isLoggedIn) {
        ClientSession.isLoggedIn = isLoggedIn;
    }
    public  static boolean getIsLoggedIn() {
        return isLoggedIn;
    }
}
