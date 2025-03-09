package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ConcurrentHashMap<String, ReentrantLock> locks;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.locks=new ConcurrentHashMap<>();
    }

    public synchronized void addUser(String userName) {
        ReentrantLock lock=locks.computeIfAbsent(userName,k->new ReentrantLock());
        lock.lock();
        try {
            User user = new User(userName);
            userRepository.saveUser(user);
        } catch (Exception e) {
            log.error("Error occurred while trying to add user ", e);
        }finally {
            lock.unlock();
            locks.remove(userName);
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
