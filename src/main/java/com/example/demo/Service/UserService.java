package com.example.demo.Service;

import com.example.demo.Entities.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List getAllUsers() {
        return userRepository.findAll();
    }

    public static List<User> userList = new ArrayList<>();


    public Optional getUserById(String id) {
        return userRepository.findById(id);
    }

    public boolean addUser(User user) {
        if (user != null) {
            userList.add(user);
            userRepository.saveAndFlush(user);
            return true;
        }else{return false;}

    }

    public User findByLogin(String userName){
//        userList.stream().filter(user -> user.getUsername()
//                        .equals(userName))
//                        .findFirst().orElse(null);

        return userRepository.findByUsername(userName);

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
