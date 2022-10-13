package tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.InMemoryTaskManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InMemoryHistoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void Start_Test() throws FileNotFoundException {
        taskManager = new InMemoryTaskManager();
        TaskManagerSetUp();
    }

    @Test
    public void Get_All_SubTasks_Of_Epic() {
        ArrayList<Integer> expected = taskManager.getEpicTask(epicTask1.getId()).getSubTaskId();
        ArrayList<Integer> actual = new ArrayList<>();
        actual.add(6);
        actual.add(7);
        actual.add(8);
        Assertions.assertEquals(expected, actual);
    }

}
