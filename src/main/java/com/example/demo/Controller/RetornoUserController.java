package com.example.demo.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RetornoUserController {

    @GetMapping("/retornoUser")
    public String getResultado(){
        return "retornoUser";
    }


}
