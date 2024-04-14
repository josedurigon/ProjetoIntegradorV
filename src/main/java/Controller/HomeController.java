package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @PostMapping("/resource")
    public String createResource(@RequestBody String requestData) {


        System.out.println("Received POST request with data: " + requestData);
        return "Resource created successfully";
    }
}
