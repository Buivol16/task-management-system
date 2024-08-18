package pl.denys.taskmanager.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.denys.taskmanager.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("DELETE FROM User u WHERE u.username = :username")
  void deleteByUsername(@Param("username") String username);
  @Modifying
  @Query("UPDATE User u SET u.password = :newPassword WHERE u.password = :oldPassword")
  void updatePassword(@Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
  boolean existsByUsername(String username);
  Optional<User> findByUsername(String username);
}
