package tests;

import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.KVServer;
import taskManager.HttpTaskManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static taskManager.Managers.getDefaultHistory;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    protected KVServer server;

    @BeforeEach
    public void Start_Test() throws IOException {
        server = new KVServer();
        server.start();
        taskManager = new HttpTaskManager();
        TaskManagerSetUp();
    }

    @AfterEach
    void serverStop() {
        server.stop();
    }

    @Test
    public void Load_From_Server(){

        taskManager.getTask(task1.getId());
        taskManager.getSubTask(subTask1.getId());

        taskManager.load();

        Map<Integer, Task> tasks = taskManager.getAllTasksMap();

        assertNotNull(tasks);
        assertEquals(3, tasks.size());

        List<Task> history = getDefaultHistory().getHistory();

        assertNotNull(history);
        assertEquals(2, history.size());
    }
}
