package vn.edu.qnu.simplechat.server.presentation.impl;

import ocsf.server.ConnectionToClient;
import vn.edu.qnu.simplechat.server.data.entity.User;
import vn.edu.qnu.simplechat.server.data.repository.UserRepository;
import vn.edu.qnu.simplechat.server.presentation.Command;
import vn.edu.qnu.simplechat.shared.protocol.request.CreateAccountRequest;
import vn.edu.qnu.simplechat.shared.protocol.response.MessageFromServer;

public class CreateAccountCommand implements Command<CreateAccountRequest> {
    private UserRepository userRepo;
    public CreateAccountCommand(UserRepository repository) {
        this.userRepo = repository;
    }
    @Override
    public void execute(CreateAccountRequest packet, ConnectionToClient client) throws Exception {
        var username = packet.username();
        var newUser = new User(username);
        try {
            var result = userRepo.save(newUser);
            client.sendToClient(new MessageFromServer("User created successfully, please log in."));
        } catch (IllegalStateException e){
            client.sendToClient(new MessageFromServer("The username already exists, try a different one."));
        }
    }
}
