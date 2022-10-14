package tests;

import Exceptions.TaskException;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskManager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class PrioritizedTaskTest {


    @Test
    public void Test_List_Of_PrioritizedTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 8, 0));
        SubTask subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 9, 0));
        SubTask subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 10, 0));
        SubTask subTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 11, 0));

        taskManager.addTask(task1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        List<Task> tasks = taskManager.getPrioritizedTasks();

        Assertions.assertNotNull(tasks, "Задачи в порядке приоритета возвращаются");
        Assertions.assertEquals(4, tasks.size(), "Все задачи в порядке приоритета кроме эпика");
        Assertions.assertEquals(task1, tasks.get(0), "Первая - обычная задача");
        Assertions.assertEquals(subTask1, tasks.get(1), "Вторая - подзадача");

        Task updateTask = new Task("Завтрак", "Позавтракать", StatusTask.DONE, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 8, 0));
        updateTask.setStartTime(subTask1.getEndTime());
        taskManager.updateTask(task1.getId(),updateTask);
        tasks = taskManager.getPrioritizedTasks();
        Assertions.assertNotNull(tasks, "Задачи в порядке приоритета возвращаются");
        Assertions.assertEquals(4, tasks.size(), "Все задачи в порядке приоритета кроме эпика");
        Assertions.assertEquals(subTask1, tasks.get(0), "Первая - обычная задача");
        Assertions.assertEquals(updateTask, tasks.get(1), "Вторая - подзадача");
    }

    @Test
    public void Test_List_Of_PrioritizedTask_After_DeleteTask(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 8, 0));
        SubTask subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 9, 0));
        SubTask subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 10, 0));
        SubTask subTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 11, 0));

        taskManager.addTask(task1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.removeSubTask(subTask2.getId());

        LinkedList<Task> actual = new LinkedList<>();

        actual.add(task1);
        actual.add(subTask1);
        actual.add(subTask3);

        Assertions.assertEquals(taskManager.getPrioritizedTasks(),actual);
    }

    @Test
    public void Throw_Exception_After_Add(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 8, 0));
        SubTask subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 9, 0));
        SubTask subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 10, 0));
        SubTask subTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 11, 0));
        SubTask subTask4 = new SubTask("Уборка ч.4", "Протереть пыль-2", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 11, 0));

        taskManager.addTask(task1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.addSubTask(subTask4);
    }

}
