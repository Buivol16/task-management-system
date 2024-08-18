package pl.denys.taskmanager.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import pl.denys.util.displaynamegeneration.CamelCaseDisplayNameGenerator;
import pl.denys.taskmanager.model.user.User;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.NONE)
@DisplayNameGeneration(CamelCaseDisplayNameGenerator.class)
public class UserMapperTest {

  @Test
  public void shouldMapUserToAppUserDetails() {
    var mapper = Mappers.getMapper(UserMapper.class);
    // given
    var user =
        User.builder().id(1L).username("username").password("password").build();
    // when
    var result = mapper.userToAppUserDetails(user);
    // then
    String resultUsername = result.getUsername();
    String resultPassword = result.getPassword();

    assertNotNull(resultUsername);
    assertNotNull(resultPassword);
    assertFalse(resultUsername.isBlank());
    assertFalse(resultPassword.isBlank());
    assertEquals("password", resultPassword);
    assertEquals("username", resultUsername);
  }
}
