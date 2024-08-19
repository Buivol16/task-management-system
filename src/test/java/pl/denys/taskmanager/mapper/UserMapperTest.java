package pl.denys.taskmanager.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.denys.taskmanager.model.user.User;
import pl.denys.util.displaynamegeneration.CamelCaseDisplayNameGenerator;

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
