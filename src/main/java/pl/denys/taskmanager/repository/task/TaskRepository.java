package pl.denys.taskmanager.repository.task;

import org.springframework.data.repository.Repository;
import pl.denys.taskmanager.model.task.Task;

public interface TaskRepository extends Repository<Task, Long> {

}
