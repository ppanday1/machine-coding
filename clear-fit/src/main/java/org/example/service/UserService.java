package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String userName) {
        try {
            User user = new User(userName);
            userRepository.saveUser(user);
        } catch (Exception e) {
            log.error("Error occurred while trying to add user ", e);
        }
    }

    public User getUser(String name) {
        try {
            return userRepository.getUserByName(name);
        } catch (Exception e) {
            log.error("Error occurred while trying to find user ", e);
        }
        return null;
    }
}
