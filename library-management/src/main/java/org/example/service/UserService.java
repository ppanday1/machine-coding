package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String emailId, String name, int days) {
        User user = new User(emailId, name, LocalDate.now(), LocalDate.now().plusDays((days)));
        if (!userRepository.saveUser(user)) {
            log.error("User with email id already exists");
        }
    }

    public User getUser(String userId) {
        return userRepository.getUser(userId);
    }

}
