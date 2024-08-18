package pl.denys.taskmanager.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
  USER {
    @Override
    public String getAuthority() {
      return "USER";
    }
  },
  ADMIN {
    @Override
    public String getAuthority() {
      return "ADMIN";
    }
  }
}
