package pl.denys.taskmanager.config.security.userdetailsmanager;

import static pl.denys.taskmanager.enums.Role.USER;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import pl.denys.taskmanager.mapper.user.UserMapper;
import pl.denys.taskmanager.model.user.User;
import pl.denys.taskmanager.repository.user.UserRepository;

@RequiredArgsConstructor
public class AppUserDetailsManager implements UserDetailsManager {
  private final UserRepository userRepository;

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Override
  public void createUser(UserDetails userDetails) {
    var user =
        User.builder()
            .username(userDetails.getUsername())
            .password(userDetails.getPassword())
            .role(USER)
            .build();
    userRepository.save(user);
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
    return userMapper.userToAppUserDetails(user);
  }
}
