package pl.denys.taskmanager.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.denys.taskmanager.dto.register.RegisterForm;
import pl.denys.taskmanager.facade.user.UserFacade;

@Controller
@RequiredArgsConstructor
public class UserRegistrationController {
  private final UserFacade userFacade;

  @GetMapping("/register")
  public String registerUser() {
    return "register";
  }

  @PostMapping("/register")
  public HttpEntity<?> doRegister(RegisterForm registerForm) {
    return userFacade.registerUser(registerForm.getUsername(), registerForm.getPassword());
  }
}
