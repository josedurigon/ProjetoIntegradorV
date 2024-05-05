package com.example.demo.Service;

import com.example.demo.Entities.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List getAllUsers() {
        return userRepository.findAll();
    }

    public Optional getUserById(String id) {
        return userRepository.findById(id);
    }

    public boolean addUser(User user) {
        if (user != null) {
            userRepository.saveAndFlush(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateUser(String id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUserName(user.getUserName());
            // Update other fields if needed
            userRepository.save(existingUser);
            return true;
        } else {
            // Handle not found scenario
            return false;
        }
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
