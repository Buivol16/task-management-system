package pl.denys.taskmanager.facade.user;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.denys.taskmanager.mapper.UserMapper;
import pl.denys.taskmanager.model.user.User;
import pl.denys.taskmanager.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserFacade {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  public User findByUsernameAndMatchPassword(String username, String password)
      throws AuthenticationException {
    var badCredentialsException =
        new BadCredentialsException("The username or password is incorrect.");

    return userRepository
        .findByUsername(username)
        .filter(user -> passwordEncoder.matches(password, user.getPassword()))
        .orElseThrow(() -> badCredentialsException);
  }

  public HttpEntity<?> registerUser(String username, String password)
      throws IllegalArgumentException {
    if (checkForNullOrBlankOrThrowException(username)
        && checkForNullOrBlankOrThrowException(password)) {
      password = passwordEncoder.encode(password);
      var user = User.builder().username(username).password(password).build();
      user = userRepository.save(user);
      var userDTO = userMapper.userToUserDTO(user);
      return ResponseEntity.ofNullable(userDTO);
    }
    return ResponseEntity.EMPTY;
  }

  private boolean checkForNullOrBlankOrThrowException(String text) throws IllegalArgumentException {
    if (text == null || text.isBlank()) throw new IllegalArgumentException();
    return true;
  }
}
