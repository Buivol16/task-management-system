package pl.denys.taskmanager.config.security.authentication;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import pl.denys.taskmanager.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUsernamePasswordAuthentication implements Authentication {
  private String principal;
  private String credentials;
  private Role role;
  private boolean isAuthenticated;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(role);
  }

  @Override
  public String getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public String getPrincipal() {
    return this.principal;
  }

  @Override
  public boolean isAuthenticated() {
    return this.isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return null;
  }
}
