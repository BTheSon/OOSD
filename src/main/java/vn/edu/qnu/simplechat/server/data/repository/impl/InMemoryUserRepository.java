package vn.edu.qnu.simplechat.server.data.repository.impl;

import vn.edu.qnu.simplechat.server.data.entity.User;
import vn.edu.qnu.simplechat.server.data.repository.UserRepository;

public class InMemoryUserRepository extends InMemoryCrudRepository <String, User> implements UserRepository {
    private static final InMemoryUserRepository instance = new InMemoryUserRepository();

    public static InMemoryUserRepository getInstance() {
        return instance;
    }

    @Override
    protected String getId(User entity) {
        return  entity.username();
    }
}
