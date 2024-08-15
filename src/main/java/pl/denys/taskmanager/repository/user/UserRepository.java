package pl.denys.taskmanager.repository.user;

import org.springframework.data.repository.Repository;
import pl.denys.taskmanager.model.user.User;

public interface UserRepository extends Repository<User, Long> {

}
