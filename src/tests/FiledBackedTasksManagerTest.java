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
}
