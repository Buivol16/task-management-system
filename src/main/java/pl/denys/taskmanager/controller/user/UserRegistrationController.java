package pl.denys.taskmanager.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRegistrationController {
    @GetMapping("/register")
    public String registerUser(){
        return "register";
    }
}
