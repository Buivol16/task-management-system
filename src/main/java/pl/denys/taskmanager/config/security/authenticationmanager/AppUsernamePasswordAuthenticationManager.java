package pl.denys.taskmanager.config.security.authenticationmanager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import pl.denys.taskmanager.config.security.authentication.AppUsernamePasswordAuthentication;
import pl.denys.taskmanager.facade.user.UserFacade;

@RequiredArgsConstructor
public class AppUsernamePasswordAuthenticationManager implements AuthenticationManager {
  private final UserFacade userFacade;

  @Override
  public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
    String principal = (String) authentication.getPrincipal();
    String credentials = (String) authentication.getCredentials();
    userFacade.findByUsernameAndMatchPassword(principal, credentials);
    return new AppUsernamePasswordAuthentication(principal, credentials, true);
  }
}
