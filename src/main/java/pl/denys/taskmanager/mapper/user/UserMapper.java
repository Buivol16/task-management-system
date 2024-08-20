package pl.denys.taskmanager.mapper.user;

import org.mapstruct.Mapper;
import pl.denys.taskmanager.config.security.userdetails.AppUserDetails;
import pl.denys.taskmanager.dto.user.UserDTO;
import pl.denys.taskmanager.model.user.User;

@Mapper
public interface UserMapper {
  AppUserDetails userToAppUserDetails(User user);
  UserDTO userToUserDTO(User user);
}
