package vn.edu.qnu.simplechat.client.utils;

import java.util.Scanner;

public class Terminal {
    private static final Terminal instance = new Terminal();

    private Terminal() {
        setup();
    }

    public static Terminal getInstance() {
        return instance;
    }

    // --- CẤU HÌNH ANSI CƠ BẢN ---
    private static final String ESC = "\u001b";
    private static final String RESET = ESC + "[0m"; // Lệnh đặt lại màu mặc định

    // --- BẢNG MÀU TEXT (FOREGROUND) ---
    public static final String BLACK = ESC + "[30m";
    public static final String RED = ESC + "[31m";
    public static final String GREEN = ESC + "[32m";
    public static final String YELLOW = ESC + "[33m";
    public static final String BLUE = ESC + "[34m";
    public static final String MAGENTA = ESC + "[35m"; // Màu tím
    public static final String CYAN = ESC + "[36m";    // Màu xanh lơ
    public static final String WHITE = ESC + "[37m";

    // --- CẤU HÌNH VÙNG CUỘN ---
    private static final int SCROLL_TOP = 1;
    private static final int SCROLL_BOTTOM = 23;
    private static final int INPUT_ROW = 24;

    private static final String SAVE_CURSOR = ESC + "[s";
    private static final String RESTORE_CURSOR = ESC + "[u";
    private static final String CLEAR_SCREEN = ESC + "[2J";
    private static final String CLEAR_LINE = ESC + "[2K";

    private Scanner scanner;

    public void setup() {
        this.scanner = new Scanner(System.in);

        System.out.print(CLEAR_SCREEN);
        System.out.print(ESC + "[" + SCROLL_TOP + ";" + SCROLL_BOTTOM + "r");
        System.out.print(ESC + "[" + INPUT_ROW + ";1H");
        System.out.flush();
    }

    /**
     * In tin nhắn với màu mặc định
     */
    public synchronized void print(String msg) {
        print(msg, RESET);
    }

    /**
     * In tin nhắn CÓ MÀU (Overload)
     * @param msg Nội dung tin nhắn
     * @param colorCode Mã màu (dùng các hằng số Terminal.RED, Terminal.GREEN...)
     */
    public synchronized void print(String msg, String colorCode) {
        System.out.print(SAVE_CURSOR);                      // 1. Lưu vị trí
        System.out.print(ESC + "[" + SCROLL_BOTTOM + ";1H");// 2. Nhảy lên vùng chat

        // 3. In: [Đổi màu] + [Nội dung] + [Reset màu]
        System.out.println(colorCode + msg + RESET);

        System.out.print(RESTORE_CURSOR);                   // 4. Trả vị trí cũ
        System.out.flush();
    }

    public String readLine(String prompt) {
        // In prompt màu vàng cho nổi bật
        String coloredPrompt = YELLOW + prompt + RESET;

        System.out.print(ESC + "[" + INPUT_ROW + ";1H" + CLEAR_LINE + coloredPrompt);
        System.out.flush();
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }
}