package br.ce.holiveira.taskbackend.repo;

import br.ce.holiveira.taskbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long>{

}

