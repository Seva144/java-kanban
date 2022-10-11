package tests;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskManager.FiledBackedTasksManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class RestoreTaskManager {

    @Test
    public void Restore_Empty_TaskManager(){
        File file = new File("src/resources/", "FiledBacked_test1.csv");

        FiledBackedTasksManager tasksManager1 = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked_test.csv"));

        Task task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        EpicTask epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);
        SubTask subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        SubTask subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        SubTask subTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 11, 0));

        try {
            tasksManager1.loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Restore_EpicWithoutSubtasks(){

        FiledBackedTasksManager tasksManagerSave = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked_test.csv"));

        EpicTask epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        tasksManagerSave.addEpicTask(epicTask1);
        tasksManagerSave.getEpicTask(epicTask1.getId());

        FiledBackedTasksManager tasksManagerLoad = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked_test.csv"));

        try {
            tasksManagerSave.loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Integer, EpicTask> expected : tasksManagerSave.getAllEpicTasksMap().entrySet()) {
            if (tasksManagerLoad.getAllEpicTasksMap().containsKey(expected.getKey())) {
                String save = String.valueOf(expected.getValue());
                String load = String.valueOf(tasksManagerLoad.getAllEpicTasksMap().get(expected.getKey()));
                Assertions.assertEquals(save, load);
            } else {
                System.out.println(false);
            }
        }
    }

    @Test
    public void Restore_WithoutHistory(){

        FiledBackedTasksManager tasksManagerSave = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked_test.csv"));

        EpicTask epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);

        tasksManagerSave.addEpicTask(epicTask1);

        FiledBackedTasksManager tasksManagerLoad = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked_test.csv"));

        try {
            tasksManagerSave.loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Integer, EpicTask> expected : tasksManagerSave.getAllEpicTasksMap().entrySet()) {
            if (tasksManagerLoad.getAllEpicTasksMap().containsKey(expected.getKey())) {
                String save = String.valueOf(expected.getValue());
                String load = String.valueOf(tasksManagerLoad.getAllEpicTasksMap().get(expected.getKey()));
                Assertions.assertEquals(save, load);
            } else {
                System.out.println(false);
            }
        }
    }

}
