package pl.denys.taskmanager.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pl.denys.taskmanager.model.SuperEntity;

@Table(name = "users")
@Entity
public class User extends SuperEntity {
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
}
