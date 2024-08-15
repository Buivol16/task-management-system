package pl.denys.taskmanager.model.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.denys.taskmanager.model.SuperEntity;
import pl.denys.taskmanager.model.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task extends SuperEntity {
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "active_to")
    private LocalDateTime activeTo;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
