package pl.denys.taskmanager.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {
    @GetMapping("/ping")
    public String registerUser(){
        return "pong";
    }
}
