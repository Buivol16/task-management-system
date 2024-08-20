package pl.denys.taskmanager.config.security.userdetails;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.denys.taskmanager.enums.Role;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AppUserDetails implements UserDetails {
  private Long id;
  private String username;
  private String password;
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(this.role);
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }
}
