package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.data.repository.UserRepository;
import vn.edu.qnu.simplechat.server.presentation.ServerCommand;
import vn.edu.qnu.simplechat.shared.protocol.request.LoginRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.ErrorMessage;
import vn.edu.qnu.simplechat.shared.protocol.response.LoginResponse;

import java.io.IOException;

public class LoginCommand implements ServerCommand<LoginRequest> {
    private final UserRepository userRepository;
    public LoginCommand(UserRepository repository) {
        this.userRepository = repository;
    }
    @Override
    public void execute(LoginRequest packet, ConnectionToClient client) throws IOException {
        var username = packet.username();
        var existUser = userRepository.existsById(username);

        if (existUser) {
            client.setInfo("username", username);
            client.sendToClient(new LoginResponse(true, "login success"));
        }else {
            client.sendToClient(new ErrorMessage("invalid username, pls create account"));
        }
    }
}
