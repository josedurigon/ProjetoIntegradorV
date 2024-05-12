package com.example.demo.Controller;


import com.example.demo.DTO.RegistrationDto;
import com.example.demo.Entities.User;
import com.example.demo.Service.UserService;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(RegistrationDto dto){
        User user = new User();




    }


}
