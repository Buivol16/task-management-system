package pl.denys.taskmanager.facade.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.denys.taskmanager.model.user.User;
import pl.denys.taskmanager.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserFacade {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User findByUsernameAndMatchPassword(String username, String password)
      throws AuthenticationException {
    var badCredentialsException =
        new BadCredentialsException("The username or password is incorrect.");
    var found =
        userRepository
            .findByUsername(username)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .orElseThrow(() -> badCredentialsException);
    return found;
  }
}
