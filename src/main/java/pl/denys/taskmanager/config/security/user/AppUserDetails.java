package pl.denys.taskmanager.config.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.denys.taskmanager.enums.RoleEnum;

import java.util.Collection;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AppUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
