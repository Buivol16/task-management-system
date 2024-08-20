package pl.denys.taskmanager.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.denys.taskmanager.MockUsers.MOCK_USER_1;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.denys.taskmanager.mapper.user.UserMapper;
import pl.denys.util.displaynamegeneration.CamelCaseDisplayNameGenerator;

@DisplayNameGeneration(CamelCaseDisplayNameGenerator.class)
public class UserMapperTest {

  @Test
  public void shouldMapUserToAppUserDetails() {
    // given
    var mapper = Mappers.getMapper(UserMapper.class);
    // when
    var result = mapper.userToAppUserDetails(MOCK_USER_1);
    // then
    String resultUsername = result.getUsername();
    String resultPassword = result.getPassword();

    assertNotNull(resultUsername);
    assertNotNull(resultPassword);
    assertFalse(resultUsername.isBlank());
    assertFalse(resultPassword.isBlank());
    assertEquals(MOCK_USER_1.getPassword(), resultPassword);
    assertEquals(MOCK_USER_1.getUsername(), resultUsername);
  }

  @Test
  public void shouldMapUserToUserDTO() {
    // given
    var mapper = Mappers.getMapper(UserMapper.class);
    // when
    var result = mapper.userToUserDTO(MOCK_USER_1);
    // then
    var resultUsername = result.getUsername();
    var resultRole = result.getRole();
    var resultId = result.getId();

    assertNotNull(resultUsername);
    assertNotNull(resultRole);
    assertNotNull(resultId);
    assertFalse(resultUsername.isBlank());
    assertEquals(MOCK_USER_1.getUsername(), resultUsername);
    assertEquals(MOCK_USER_1.getRole(), resultRole);
    assertEquals(MOCK_USER_1.getId(), resultId);
  }
}
