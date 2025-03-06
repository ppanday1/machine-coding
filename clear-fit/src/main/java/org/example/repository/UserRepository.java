package org.example.repository;

import org.example.exception.EntityAlreadyFoundException;
import org.example.exception.EntityDoesNotExistException;
import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
    }

    public synchronized void saveUser(User user) throws EntityAlreadyFoundException {
        if (users.containsKey(user.getName())) {
            throw new EntityAlreadyFoundException("User with userName already exists " + user.getName());
        }
        users.put(user.getName(), user);
    }

    public synchronized User getUserByName(String name) throws EntityDoesNotExistException {
        if (!users.containsKey(name)) {
            throw new EntityDoesNotExistException("User with userName " + name + " doesn't exists");
        }
        return users.get(name);
    }
}
