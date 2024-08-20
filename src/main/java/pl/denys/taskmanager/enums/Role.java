package pl.denys.taskmanager.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  USER {
    @Override
    public String getAuthority() {
      return "ROLE_USER";
    }
  },
  ADMIN {
    @Override
    public String getAuthority() {
      return "ROLE_ADMIN";
    }
  }
}
