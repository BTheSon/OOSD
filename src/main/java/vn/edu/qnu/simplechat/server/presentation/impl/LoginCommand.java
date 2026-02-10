package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.presentation.Command;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;

public class LoginCommand implements Command<LoginRequest> {
    @Override
    public void execute(LoginRequest packet, ConnectionToClient client) {
//        try {
//            var repo = StorageContext.getInstance();
//            synchronized (repo.users) {
//                var isExistUser = repo.users
//                        .stream()
//                        .filter(u -> u.username().equals(packet.username()))
//                        .findFirst()
//                        .orElse(null) != null;
//                if (!isExistUser) {
//                    repo.users.add(new User(packet.username()));
//                    client.sendToClient(new LoginResponse(true, "tạo người dùng thành công"));
//                } else {
//                    client.sendToClient(new LoginResponse(false, "người dùng đã tồn tại, vui lòng đăng kí"));
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
