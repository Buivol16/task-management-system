package pl.denys.taskmanager.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.denys.taskmanager.model.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
