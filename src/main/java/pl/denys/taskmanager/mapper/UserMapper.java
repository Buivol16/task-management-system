package pl.denys.taskmanager.mapper;

import org.mapstruct.Mapper;
import pl.denys.taskmanager.config.security.user.AppUserDetails;
import pl.denys.taskmanager.model.user.User;

@Mapper
public interface UserMapper {
  AppUserDetails userToAppUserDetails(User user);
}
