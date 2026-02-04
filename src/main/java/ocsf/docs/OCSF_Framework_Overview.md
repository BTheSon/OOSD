# Tài liệu tổng quan OCSF (Object Client-Server Framework)

## 1. Giới thiệu

OCSF (Object Client-Server Framework) là một framework Java được thiết kế để đơn giản hóa việc phát triển các ứng dụng client-server. Nó cung cấp một nền tảng cơ bản để xử lý các kết nối mạng, cho phép các nhà phát triển tập trung vào logic của ứng dụng thay vì các chi tiết phức tạp của lập trình socket. Framework cho phép client và server giao tiếp với nhau bằng cách trao đổi các đối tượng (Object) trong Java, giúp việc truyền dữ liệu phức tạp trở nên dễ dàng.

OCSF được xây dựng dựa trên một số mẫu thiết kế phần mềm quan trọng, bao gồm **Template Method** và **Observer**, giúp mã nguồn có tính tái sử dụng và mở rộng cao.

## 2. Các thành phần chính

Framework được chia thành hai gói chính: `client` và `server`.

### 2.1. Gói `server`

Gói này chứa các lớp để xây dựng phía server của một ứng dụng.

#### `AbstractServer`
- **Mục đích:** Là lớp trừu tượng trung tâm cho tất cả các server. Nó quản lý một luồng (thread) để lắng nghe các kết nối từ client.
- **Hoạt động:** Khi một client kết nối, `AbstractServer` tạo ra một luồng `ConnectionToClient` riêng để xử lý giao tiếp với client đó.
- **Tùy chỉnh:** Lớp này sử dụng mẫu **Template Method**. Các nhà phát triển phải kế thừa từ lớp này và triển khai phương thức trừu tượng `handleMessageFromClient(Object msg, ConnectionToClient client)` để định nghĩa cách server xử lý các tin nhắn nhận được.
- **Các "Hook" Method:** Cung cấp các phương thức có thể được ghi đè (override) để xử lý các sự kiện trong vòng đời của server, chẳng hạn như `serverStarted()`, `serverStopped()`, `clientConnected(ConnectionToClient client)`, `clientDisconnected(ConnectionToClient client)`.

#### `ConnectionToClient`
- **Mục đích:** Là một luồng được tạo cho mỗi client kết nối thành công đến server.
- **Hoạt động:** Lớp này quản lý `Socket`, `ObjectInputStream` và `ObjectOutputStream` cho một client cụ thể. Nó liên tục lắng nghe các đối tượng được gửi từ client và chuyển chúng đến phương thức `handleMessageFromClient` của `AbstractServer`. Lớp này cũng chịu trách nhiệm gửi dữ liệu từ server đến client.

#### `ObservableServer`
- **Mục đích:** Cung cấp một kiến trúc dựa trên mẫu thiết kế **Observer**. Nó cho phép các phần khác của ứng dụng (ví dụ: giao diện người dùng) "quan sát" các sự kiện của server mà không cần ghép nối chặt chẽ.
- **Hoạt động:** Lớp này kế thừa từ `java.util.Observable`. Khi một sự kiện xảy ra (ví dụ: client kết nối, nhận được tin nhắn), nó sẽ thông báo cho tất cả các `Observer` đã đăng ký.
- **Các thông báo:** Gửi các chuỗi thông báo tĩnh như `CLIENT_CONNECTED`, `CLIENT_DISCONNECTED`, `SERVER_STARTED`.

#### `ObservableOriginatorServer`
- **Mục đích:** Là một phiên bản nâng cao của `ObservableServer`.
- **Hoạt động:** Khi thông báo cho các `Observer`, thay vì chỉ gửi một tin nhắn, nó gửi một đối tượng `OriginatorMessage`. Điều này rất hữu ích vì `OriginatorMessage` chứa cả tin nhắn và thông tin về `ConnectionToClient` (nguồn gốc) đã gửi tin nhắn đó. Điều này cho phép các `Observer` biết được client nào đã gây ra sự kiện.

#### `OriginatorMessage`
- **Mục đích:** Một lớp chứa đơn giản để đóng gói một tin nhắn (`Object`) và người gửi (`ConnectionToClient`).

### 2.2. Gói `client`

Gói này chứa các lớp để xây dựng phía client của một ứng dụng.

#### `AbstractClient`
- **Mục đích:** Là lớp trừu tượng cơ sở cho tất cả các client. Nó quản lý kết nối đến server.
- **Hoạt động:** Lớp này xử lý việc thiết lập `Socket` và các luồng `ObjectInputStream`, `ObjectOutputStream`. Nó chạy một luồng riêng để lắng nghe các tin nhắn từ server.
- **Tùy chỉnh:** Tương tự `AbstractServer`, lớp này yêu cầu nhà phát triển phải kế thừa và triển khai phương thức `handleMessageFromServer(Object msg)` để xử lý các tin nhắn nhận được.
- **Các "Hook" Method:** Cung cấp các phương thức như `connectionEstablished()`, `connectionClosed()` để xử lý các sự kiện kết nối.

#### `ObservableClient`
- **Mục đích:** Cung cấp chức năng **Observer** cho phía client.
- **Hoạt động:** Kế thừa `java.util.Observable` và thông báo cho các `Observer` về các sự kiện của client như `CONNECTION_ESTABLISHED`, `CONNECTION_CLOSED`, hoặc khi nhận được tin nhắn. Điều này rất hữu ích để cập nhật giao diện người dùng dựa trên trạng thái kết nối hoặc dữ liệu nhận được.

### 2.3. Các lớp `Adaptable`

Các lớp `AdaptableClient` và `AdaptableServer` được sử dụng như một cầu nối (Adapter) để cho phép các lớp `Observable` (như `ObservableClient`, `ObservableServer`) hoạt động như một `AbstractClient` hoặc `AbstractServer`. Đây là một giải pháp kỹ thuật để khắc phục việc Java không hỗ trợ đa kế thừa, cho phép kết hợp chức năng của lớp `Observable` và các lớp `Abstract` của framework.

## 3. Luồng hoạt động chung

1.  **Server khởi động:**
    - Một lớp server tùy chỉnh kế thừa từ `AbstractServer` được khởi tạo.
    - Gọi phương thức `listen()` để bắt đầu lắng nghe kết nối.
    - Server chờ client ở một port đã định.

2.  **Client kết nối:**
    - Một lớp client tùy chỉnh kế thừa từ `AbstractClient` được khởi tạo với host và port của server.
    - Gọi phương thức `openConnection()` để yêu cầu kết nối.
    - Server chấp nhận kết nối và tạo một luồng `ConnectionToClient` mới cho client này.

3.  **Giao tiếp:**
    - **Client gửi tin nhắn:** Client gọi `sendToServer(Object msg)`. Đối tượng `msg` được gửi qua `ObjectOutputStream`.
    - **Server nhận tin nhắn:** Luồng `ConnectionToClient` đọc đối tượng từ `ObjectInputStream`, sau đó gọi `handleMessageFromClient(msg, client)` của server để xử lý.
    - **Server gửi tin nhắn:** Server gọi `sendToAllClients(Object msg)` hoặc `client.sendToClient(Object msg)` (trong đó `client` là một `ConnectionToClient` cụ thể).
    - **Client nhận tin nhắn:** Luồng của `AbstractClient` đọc đối tượng và gọi `handleMessageFromServer(msg)` để xử lý.

4.  **Đóng kết nối:**
    - Client gọi `closeConnection()` hoặc server đóng kết nối từ phía nó.
    - Các phương thức `connectionClosed()` ở client và `clientDisconnected()` ở server được gọi.

## 4. Các mẫu thiết kế sử dụng

-   **Template Method:** `AbstractServer` và `AbstractClient` định nghĩa các bước chính của một thuật toán (quản lý kết nối và luồng) và để các lớp con định nghĩa lại một số bước (xử lý tin nhắn).
-   **Observer:** `ObservableServer` và `ObservableClient` cho phép các đối tượng khác theo dõi trạng thái và sự kiện mà không tạo ra sự phụ thuộc chặt chẽ.
-   **Adapter:** `AdaptableServer` và `AdaptableClient` cho phép các lớp `Observable` được "thích ứng" để hoạt động như các lớp `Abstract` của framework.
