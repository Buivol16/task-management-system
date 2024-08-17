package pl.denys.taskmanager.config.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pl.denys.taskmanager.model.role.Role;
import pl.denys.taskmanager.model.user.User;
import pl.denys.taskmanager.repository.role.RoleRepository;
import pl.denys.taskmanager.repository.user.UserRepository;

import static pl.denys.taskmanager.enums.RoleEnum.USER;

@RequiredArgsConstructor
public class AppUserDetailsManager implements UserDetailsManager {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public void createUser(UserDetails userDetails) {
    var user =
        User.builder()
            .username(userDetails.getUsername())
            .password(userDetails.getPassword())
            .build();
    user = userRepository.save(user);
    var userRole = Role.builder().user(user).role(USER).build();
    roleRepository.save(userRole);
  }

  @Override
  public void updateUser(UserDetails userDetails) {
    throw new UnsupportedOperationException("Update is not implemented");
  }

  @Override
  public void deleteUser(String username) {
    userRepository.deleteByUsername(username);
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    userRepository.updatePassword(oldPassword, newPassword);
  }

  @Override
  public boolean userExists(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        "User by username " + username + " is not found"));
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(user.getRoleSet().toString())
        .build();
  }
}
