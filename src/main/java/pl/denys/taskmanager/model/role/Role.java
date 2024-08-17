package pl.denys.taskmanager.model.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import pl.denys.taskmanager.enums.RoleEnum;
import pl.denys.taskmanager.model.user.User;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "role")
@Builder
public class Role{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated
    @Column(name = "role_name")
    private RoleEnum role;
}
