package pl.denys.taskmanager.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pl.denys.taskmanager.MockUsers.MOCK_USER_1;
import static pl.denys.taskmanager.MockUsers.getMockUser1WithPassword;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.denys.taskmanager.dto.user.UserDTO;
import pl.denys.taskmanager.facade.user.UserFacade;
import pl.denys.taskmanager.model.user.User;
import pl.denys.taskmanager.repository.user.UserRepository;
import pl.denys.util.displaynamegeneration.CamelCaseDisplayNameGenerator;

@SpringBootTest
@AutoConfigureTestDatabase(
    connection = EmbeddedDatabaseConnection.NONE,
    replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@DisplayNameGeneration(CamelCaseDisplayNameGenerator.class)
public class UserFacadeTest {
  @MockBean private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserFacade userFacade;

  @Test
  public void shouldReturnUser() {
    // given
    String username = MOCK_USER_1.getUsername();
    String password = MOCK_USER_1.getPassword();
    User userWithEncodedPassword = getMockUser1WithPassword(passwordEncoder.encode(password));
    Optional<User> mockUser = Optional.of(userWithEncodedPassword);
    when(userRepository.findByUsername(username)).thenReturn(mockUser);
    // when
    var found = userFacade.findByUsernameAndMatchPassword(username, password);
    // then
    assertNotNull(found);
    assertEquals(username, found.getUsername());
    assertNotEquals(password, found.getPassword());
    assertEquals(1L, found.getId());
  }

  @Test
  public void shouldThrowBadCredentialExceptionWhenUserIsNotExists() {
    // given
    String username = MOCK_USER_1.getUsername();
    String password = MOCK_USER_1.getPassword();
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    // when-then
    assertThrows(
        BadCredentialsException.class,
        () -> userFacade.findByUsernameAndMatchPassword(username, password));
  }

  @Test
  public void shouldThrowBadCredentialExceptionWhenPasswordIsIncorrect() {
    // given
    String username = MOCK_USER_1.getUsername();
    var encodedPass = passwordEncoder.encode(MOCK_USER_1.getPassword());
    var mockUser = Optional.of(getMockUser1WithPassword(encodedPass));
    when(userRepository.findByUsername(username)).thenReturn(mockUser);
    // when-then
    assertThrows(
        BadCredentialsException.class,
        () -> userFacade.findByUsernameAndMatchPassword(username, "somewrongpass"));
  }

  @Test
  public void shouldRegisterAndReturnUser() {
    // given
    var username = MOCK_USER_1.getUsername();
    var password = MOCK_USER_1.getPassword();
    when(userRepository.save(any(User.class))).thenReturn(MOCK_USER_1);
    // when
    var response = (UserDTO) userFacade.registerUser(username, password).getBody();
    // then
    assertNotNull(response);
    assertEquals(1, response.getId());
    assertEquals(username, response.getUsername());
  }

  @Test
  public void shouldNotRegisterAndReturnUser() {
    // when-then
    assertThrows(IllegalArgumentException.class, () -> userFacade.registerUser("", ""));
    assertThrows(IllegalArgumentException.class, () -> userFacade.registerUser(null, null));
  }
}
