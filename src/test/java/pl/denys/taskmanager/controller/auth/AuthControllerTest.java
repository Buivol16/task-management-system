package pl.denys.taskmanager.controller.auth;

import static org.mockito.Mockito.when;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.denys.taskmanager.MockUsers.MOCK_USER_1;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.denys.util.displaynamegeneration.CamelCaseDisplayNameGenerator;
import pl.denys.taskmanager.config.security.authenticationmanager.AppUsernamePasswordAuthenticationManager;
import pl.denys.taskmanager.facade.user.UserFacade;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(replace = NONE, connection = EmbeddedDatabaseConnection.NONE)
@AutoConfigureMockMvc
@DisplayNameGeneration(CamelCaseDisplayNameGenerator.class)
public class AuthControllerTest {
  @MockBean private UserFacade userFacade;
  @MockBean private AppUsernamePasswordAuthenticationManager authenticationManager;
  @Autowired private MockMvc mockMvc;

  @SneakyThrows
  @Test
  public void shouldLoginToAccount() {
    // given
    String mockUser1Password = MOCK_USER_1.getPassword();
    String mockUser1Username = MOCK_USER_1.getUsername();
    when(userFacade.findByUsernameAndMatchPassword(mockUser1Username, mockUser1Password))
        .thenReturn(MOCK_USER_1);
    // when-then
    tryLogin(mockUser1Username, mockUser1Password)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/ping"));
  }

  @SneakyThrows
  @Test
  public void shouldNotLoginToAccount() {
    // given
    String wrongusername = "wrongusername";
    String wrongpassword = "wrongpassword";

    when(userFacade.findByUsernameAndMatchPassword(wrongusername, wrongpassword))
        .thenThrow(BadCredentialsException.class);
    // when-then
    tryLogin(wrongusername, wrongpassword)
        .andExpect(status().is(302))
        .andExpect(redirectedUrl("/login?error"));
  }

  private ResultActions tryLogin(String username, String password) throws Exception {
    return mockMvc.perform(
        post("/login")
            .with(csrf())
            .contentType(APPLICATION_FORM_URLENCODED)
            .formField("username", username)
            .formField("password", password));
  }
}
