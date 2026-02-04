# Hướng dẫn sử dụng OCSF Framework

Tài liệu này hướng dẫn các bước cơ bản để xây dựng một ứng dụng chat đơn giản bằng OCSF, bao gồm một server và nhiều client.

## 1. Thiết lập dự án

Trước tiên, hãy đảm bảo bạn đã thêm các file mã nguồn của OCSF (các gói `server` và `client`) vào trong dự án Java của bạn.

## 2. Xây dựng Server

Server có nhiệm vụ lắng nghe các kết nối từ client và chuyển tiếp tin nhắn nhận được từ một client đến tất cả các client khác.

### Bước 1: Tạo lớp Server

Tạo một lớp mới, ví dụ `SimpleChatServer`, kế thừa từ `AbstractServer`.

```java
import ocsf.server.*;

public class SimpleChatServer extends AbstractServer {
    /**
     * Constructor khởi tạo server trên một port cụ thể.
     */
    public SimpleChatServer(int port) {
        super(port);
    }

    /**
     * Đây là phương thức quan trọng nhất. Nó được gọi mỗi khi server
     * nhận được một tin nhắn từ bất kỳ client nào.
     *
     * @param msg    Đối tượng tin nhắn nhận được.
     * @param client Kết nối đến client đã gửi tin nhắn.
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        // Kiểm tra xem tin nhắn có phải là một chuỗi không
        if (msg instanceof String) {
            String messageText = (String) msg;
            System.out.println("Message received: " + messageText + " from " + client);
            // Gửi tin nhắn này đến tất cả các client đang kết nối
            this.sendToAllClients(client.getInfo("loginID") + ": " + messageText);
        }
    }

    /**
     * Ghi đè phương thức này để xử lý sự kiện khi một client kết nối.
     */
    @Override
    protected void clientConnected(ConnectionToClient client) {
        System.out.println("A new client is connected: " + client);
        // Gửi tin nhắn chào mừng đến tất cả mọi người
        this.sendToAllClients("SYSTEM: " + client + " has joined.");
    }

    /**
     * Ghi đè phương thức này để xử lý sự kiện khi một client ngắt kết nối.
     */
    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
        System.out.println(client + " has disconnected.");
        this.sendToAllClients("SYSTEM: " + client + " has left.");
    }
    
    /**
     * Ghi đè phương thức này để thông báo server đã khởi động.
     */
    @Override
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }
}
```

### Bước 2: Tạo lớp Main để chạy Server

Tạo một lớp `ServerConsole` để khởi động `SimpleChatServer`.

```java
import java.io.IOException;

public class ServerConsole {
    public static void main(String[] args) {
        int port = 5555; // Port mặc định

        try {
            // Lấy port từ command line arguments nếu có
            port = Integer.parseInt(args[0]);
        } catch (Throwable t) {
            // Bỏ qua và dùng port mặc định
        }

        SimpleChatServer server = new SimpleChatServer(port);

        try {
            server.listen(); // Bắt đầu lắng nghe kết nối
            System.out.println("Server started. Press any key to stop.");
            // Giữ server chạy cho đến khi người dùng nhấn Enter
            System.in.read();
            server.close();
        } catch (IOException e) {
            System.err.println("ERROR - Could not listen for clients!");
        }
    }
}
```

## 3. Xây dựng Client

Client sẽ kết nối đến server, gửi tin nhắn từ người dùng và hiển thị tin nhắn nhận được từ server.

### Bước 1: Tạo giao diện người dùng (hoặc giao diện dòng lệnh)

Để đơn giản, chúng ta sẽ tạo một client có giao diện dòng lệnh (console). Chúng ta cần một cách để client có thể "lắng nghe" các tin nhắn từ server mà không bị chặn khi đang chờ người dùng nhập liệu. `ObservableClient` là lựa chọn lý tưởng cho việc này.

Đầu tiên, tạo một lớp `ChatClientUI` triển khai `java.util.Observer` để nhận thông báo từ `ObservableClient`.

```java
import java.util.Observer;
import java.util.Observable;

// Lớp này đóng vai trò là View, hiển thị dữ liệu cho người dùng
public class ChatClientUI implements Observer {

    @Override
    public void update(Observable obs, Object arg) {
        // Kiểm tra xem arg có phải là tin nhắn từ server không
        if (arg instanceof String) {
            String message = (String) arg;
            // Hiển thị tin nhắn
            System.out.println(">>> " + message);
        }
    }
}
```

### Bước 2: Tạo lớp Client chính

Tạo một lớp `SimpleChatClient` để quản lý logic kết nối và gửi tin nhắn.

```java
import ocsf.client.*;
import java.io.IOException;

public class SimpleChatClient {

    private ObservableClient client;
    private ChatClientUI clientUI;

    public SimpleChatClient(String host, int port) throws IOException {
        client = new ObservableClient(host, port);
        clientUI = new ChatClientUI();
        client.addObserver(clientUI); // Thêm UI làm Observer
        client.openConnection(); // Mở kết nối
    }

    public void send(String message) {
        try {
            client.sendToServer(message);
        } catch (IOException e) {
            System.err.println("Error sending message to server: " + e.getMessage());
        }
    }

    public void close() throws IOException {
        client.closeConnection();
    }
    
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5555;

        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (Throwable t) {
            // Dùng giá trị mặc định
        }

        try {
            SimpleChatClient chatClient = new SimpleChatClient(host, port);
            System.out.println("Connected to server. Type 'quit' to exit.");

            // Vòng lặp đọc input từ người dùng
            java.io.BufferedReader fromConsole = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            String message;

            while ((message = fromConsole.readLine()) != null) {
                if (message.equalsIgnoreCase("quit")) {
                    chatClient.close();
                    break;
                }
                chatClient.send(message);
            }
        } catch (IOException e) {
            System.err.println("Cannot open connection. Awaiting command.");
        }
    }
}
```
**Giải thích:**
1.  `SimpleChatClient` tạo một `ObservableClient` và thêm `ChatClientUI` làm `Observer` của nó.
2.  Khi `ObservableClient` nhận được tin nhắn từ server, nó sẽ gọi `handleMessageFromServer`. Phương thức này trong `ObservableClient` sẽ tự động gọi `notifyObservers(message)`.
3.  `ChatClientUI`, vì đã đăng ký làm `Observer`, sẽ nhận được thông báo qua phương thức `update()` của nó, và hiển thị tin nhắn ra console.
4.  Luồng `main` của client có thể tập trung vào việc đọc input của người dùng và gửi đi mà không bị block.

## 4. Chạy ứng dụng

1.  **Biên dịch tất cả các file Java.**
    ```bash
    javac *.java ocsf/client/*.java ocsf/server/*.java
    ```
2.  **Chạy Server:**
    Mở một cửa sổ terminal và chạy:
    ```bash
    java ServerConsole 5555
    ```
    Server sẽ khởi động và chờ kết nối trên port 5555.

3.  **Chạy Client:**
    Mở một (hoặc nhiều) cửa sổ terminal khác và chạy:
    ```bash
    java SimpleChatClient localhost 5555
    ```
    Mỗi cửa sổ sẽ là một client. Bây giờ bạn có thể gõ tin nhắn vào bất kỳ cửa sổ client nào, nhấn Enter, và tin nhắn đó sẽ xuất hiện trên tất cả các client khác và cả trên console của server.
