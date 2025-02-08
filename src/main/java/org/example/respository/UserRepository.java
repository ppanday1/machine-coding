package org.example.respository;

import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public boolean saveUser(User user) {
        if (users.containsKey(user.getEmailId())) {
            return false;
        }
        users.put(user.getEmailId(), user);
        return true;
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public User deleteUser(String id) {
        return users.remove(id);
    }
}
