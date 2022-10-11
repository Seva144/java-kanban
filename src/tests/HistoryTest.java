package tests;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskManager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static taskManager.Managers.getDefaultHistory;

public class HistoryTest {

    Task task1;
    EpicTask epicTask1;
    SubTask subTask1;
    SubTask subTask2;
    Task task2;

    @Test
    public void Empty_History(){

        InMemoryTaskManager taskManagerHistory1 = new InMemoryTaskManager();

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        task2 = new Task("Обед", "Пообедать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 12, 0));
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        taskManagerHistory1.addTask(task1);
        taskManagerHistory1.addTask(task2);
        taskManagerHistory1.addSubTask(subTask1);
        taskManagerHistory1.addSubTask(subTask2);
        taskManagerHistory1.addEpicTask(epicTask1);

        List<Task> actual = new ArrayList<>();

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);
    }

    @Test
    public void Duplication_History(){

        InMemoryTaskManager taskManagerHistory = new InMemoryTaskManager();

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        task2 = new Task("Обед", "Пообедать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 12, 0));
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        taskManagerHistory.addTask(task1);
        taskManagerHistory.addTask(task2);
        taskManagerHistory.addSubTask(subTask1);
        taskManagerHistory.addSubTask(subTask2);
        taskManagerHistory.addEpicTask(epicTask1);

        taskManagerHistory.getTask(task1.getId());
        taskManagerHistory.getTask(task1.getId());
        taskManagerHistory.getSubTask(subTask1.getId());
        taskManagerHistory.getSubTask(subTask1.getId());
        taskManagerHistory.getEpicTask(epicTask1.getId());
        taskManagerHistory.getEpicTask(epicTask1.getId());

        List<Task> actual = new ArrayList<>();

        actual.add(task1);
        actual.add(subTask1);
        actual.add(epicTask1);

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);
    }

    @Test
    public void Remove_From_History_Head(){
        InMemoryTaskManager taskManagerHistory = new InMemoryTaskManager();

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        task2 = new Task("Обед", "Пообедать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 12, 0));
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        taskManagerHistory.addTask(task1);
        taskManagerHistory.addTask(task2);
        taskManagerHistory.addSubTask(subTask1);
        taskManagerHistory.addSubTask(subTask2);
        taskManagerHistory.addEpicTask(epicTask1);

        taskManagerHistory.getTask(task1.getId());
        taskManagerHistory.getTask(task2.getId());
        taskManagerHistory.getSubTask(subTask1.getId());
        taskManagerHistory.getSubTask(subTask2.getId());
        taskManagerHistory.getEpicTask(epicTask1.getId());

        List<Task> actual = new ArrayList<>();

        actual.add(task1);
        actual.add(task2);
        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(epicTask1);

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);

        taskManagerHistory.removeTask(task1.getId());

        actual.remove(task1);

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);
    }

    @Test
    public void Remove_From_History_Tail(){
        InMemoryTaskManager taskManagerHistory = new InMemoryTaskManager();

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        task2 = new Task("Обед", "Пообедать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 12, 0));
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        taskManagerHistory.addTask(task1);
        taskManagerHistory.addTask(task2);
        taskManagerHistory.addSubTask(subTask1);
        taskManagerHistory.addSubTask(subTask2);
        taskManagerHistory.addEpicTask(epicTask1);

        taskManagerHistory.getTask(task1.getId());
        taskManagerHistory.getTask(task2.getId());
        taskManagerHistory.getSubTask(subTask1.getId());
        taskManagerHistory.getSubTask(subTask2.getId());
        taskManagerHistory.getEpicTask(epicTask1.getId());

        List<Task> actual = new ArrayList<>();

        actual.add(task1);
        actual.add(task2);
        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(epicTask1);

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);

        actual.remove(epicTask1);

        taskManagerHistory.removeEpicTask(epicTask1.getId());

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);
    }

    @Test
    public void Remove_From_History_Body(){
        InMemoryTaskManager taskManagerHistory = new InMemoryTaskManager();

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        task2 = new Task("Обед", "Пообедать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 12, 0));
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        taskManagerHistory.addTask(task1);
        taskManagerHistory.addTask(task2);
        taskManagerHistory.addSubTask(subTask1);
        taskManagerHistory.addSubTask(subTask2);
        taskManagerHistory.addEpicTask(epicTask1);

        taskManagerHistory.getTask(task1.getId());
        taskManagerHistory.getTask(task2.getId());
        taskManagerHistory.getSubTask(subTask1.getId());
        taskManagerHistory.getSubTask(subTask2.getId());
        taskManagerHistory.getEpicTask(epicTask1.getId());

        List<Task> actual = new ArrayList<>();

        actual.add(task1);
        actual.add(task2);
        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(epicTask1);

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);

        actual.remove(subTask1);

        taskManagerHistory.removeSubTask(subTask1.getId());

        Assertions.assertEquals(getDefaultHistory().getHistory(), actual);
    }
}
