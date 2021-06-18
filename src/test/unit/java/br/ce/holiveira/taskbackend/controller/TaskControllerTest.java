package br.ce.holiveira.taskbackend.controller;

import br.ce.holiveira.taskbackend.utils.ValidationException;
import br.ce.holiveira.taskbackend.model.Task;
import br.ce.holiveira.taskbackend.repo.TaskRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;
    private MockitoAnnotations MochitoAnnotations;

    @Before
    public void setup() {
        MochitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Não deve chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        Task todo = new Task();
        todo.setTask("Descricao");
        try {
            controller.save(todo);
            Assert.fail("Não deve chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        Task todo = new Task();
        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.of(2010,01,01));
        try {
            controller.save(todo);
            Assert.fail("Não deve chegar nesse ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }
}
