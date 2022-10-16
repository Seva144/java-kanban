package tests;

import org.junit.jupiter.api.BeforeEach;
import taskManager.FiledBackedTasksManager;

import java.io.File;
import java.io.FileNotFoundException;

public class FiledBackedTasksManagerTest extends TaskManagerTest<FiledBackedTasksManager>{

    @BeforeEach
    public void Start_Test() throws FileNotFoundException {
        taskManager = new FiledBackedTasksManager(new File("src/resources/", "FiledBacked.csv"));

        TaskManagerSetUp();
    }
}
