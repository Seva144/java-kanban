package taskManager;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.EpicTask;
import model.SubTask;
import model.Task;
import service.KVTaskClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static taskManager.Managers.getDefaultHistory;

public class HttpTaskManager extends FileBackedTasksManager {

    KVTaskClient client;
    Gson gson;

    public HttpTaskManager() {
        gson = Managers.getGson();
        client = new KVTaskClient();
    }

    public KVTaskClient getClient() {
        return client;
    }

    @Override
    public void save() {

        String prioritizedTasks = gson.toJson(getPrioritizedTasks());
        client.save("tasks", prioritizedTasks);

        String history = gson.toJson(getDefaultHistory().getHistory());
        client.save("tasks/history", history);

        String tasks = gson.toJson(getAllTasksMap());
        client.save("tasks/task", tasks);

        String epics = gson.toJson(getAllEpicTasksMap());
        client.save("tasks/epic", epics);

        String subtasks = gson.toJson(getAllSubTasksMap());
        client.save("tasks/subtask", subtasks);
    }

    public void load() {

        String jsonPrioritizedTasks = getClient().load("tasks");
        Type prioritizedTaskType = new TypeToken<List<Task>>(){}.getType();
        List<Task> priorityTasks = gson.fromJson(jsonPrioritizedTasks, prioritizedTaskType);

        getPrioritizedTasks().addAll(priorityTasks);

        String gsonHistory = getClient().load("tasks/history");
        Type historyType = new TypeToken<List<Task>>(){}.getType();
        List<Task> history = gson.fromJson(gsonHistory, historyType);

        Managers.getDefaultHistory().getHistory().addAll(history);

        String jsonTasks = getClient().load("tasks/task");
        Type taskType = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(jsonTasks, taskType);

        getAllTasksMap().putAll(tasks);

        String jsonEpics = getClient().load("tasks/epic");
        Type epicType = new TypeToken<Map<Integer, EpicTask>>(){}.getType();
        Map<Integer, EpicTask> epics = gson.fromJson(jsonEpics, epicType);

        getAllEpicTasksMap().putAll(epics);

        String jsonSubtasks = getClient().load("tasks/subtask");
        Type subtaskType = new TypeToken<Map<Integer, SubTask>>(){}.getType();
        Map<Integer, SubTask> subtasks = gson.fromJson(jsonSubtasks, subtaskType);

        getAllSubTasksMap().putAll(subtasks);
    }




}
