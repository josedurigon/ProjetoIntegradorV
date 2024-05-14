package com.example.demo.Service;

import com.example.demo.Entities.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
            existingUser.setUsername(user.getUsername());
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

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName);
    }

}
