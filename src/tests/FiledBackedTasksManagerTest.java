package tests;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.FiledBackedTasksManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class FiledBackedTasksManagerTest extends TaskManagerTest<FiledBackedTasksManager>{


    private File file;

    @BeforeEach
    public void Start_Test() throws FileNotFoundException {
        file = new File("src/resources/", "FiledBacked.csv");
        taskManager = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked.csv"));

        TaskManagerSetUp();
    }

//    @Test
//    public void Restore_Empty_TaskManager() throws IOException {
//        File file = new File("src/resources/", "FiledBacked_test1.csv");
//
//        FiledBackedTasksManager tasksManager1 = new FiledBackedTasksManager();
//
//        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
//        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);
//        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
//        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
//        subTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 11, 0));
//
//
//
////       tasksManager1.loadFromFile();
//    }







}
