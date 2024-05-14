package com.example.demo.Controller;

import com.example.demo.DTO.RegistrationDto;
import com.example.demo.Entities.User;
import com.example.demo.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerUser(RegistrationDto dto, Model model) {
        // Mapping RegistrationDto to User entity
        User user = new User();
        user.setUsername(dto.getUsername());  // Assuming getUserName() is a method in RegistrationDto
        user.setPassword(dto.getPassword());  // Assuming getPassword() is a method in RegistrationDto

        // Add the user using the user service
        boolean isRegistered = userService.addUser(user);

        if (isRegistered) {
            model.addAttribute("message", "User registered successfully");
            return "success";  // Assuming there is a success.html template
        } else {
            model.addAttribute("message", "Error registering user");
            return "registration";  // Redirect back to registration in case of error
        }
    }
}
