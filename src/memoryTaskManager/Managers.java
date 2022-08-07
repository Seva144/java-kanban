package memoryTaskManager;

import interfaces.HistoryManager;
import interfaces.TaskManager;

public class Managers {

    static TaskManager taskManager = new InMemoryTaskManager();
    static HistoryManager historyManager = new InMemoryHistoryManager();

    public static TaskManager getDefault(){
        return taskManager;
    }

    public static HistoryManager getDefaultHistory(){
        return historyManager;
    }
}

