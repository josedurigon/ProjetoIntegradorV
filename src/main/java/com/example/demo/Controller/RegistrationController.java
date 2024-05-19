package com.example.demo.Controller;

import com.example.demo.DTO.RegistrationDto;
import com.example.demo.Entities.User;
import com.example.demo.Service.UserService;
import com.example.demo.enumerations.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute User user) {



        // Add the user using the user service
        if (user != null){
            user.setRole(Roles.USER.toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        boolean isRegistered = userService.addUser(user);

        return isRegistered?"redirect:/login" : "redirect:/error";
    }
}
