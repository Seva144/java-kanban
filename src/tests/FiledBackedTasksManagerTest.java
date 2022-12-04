package tests;

import org.junit.jupiter.api.BeforeEach;
import taskManager.FileBackedTasksManager;

import java.io.File;
import java.io.IOException;

public class FiledBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{

    @BeforeEach
    public void Start_Test() throws IOException {
        taskManager = new FileBackedTasksManager(new File("src/resources/", "FiledBacked.csv"));

        TaskManagerSetUp();
    }
}
