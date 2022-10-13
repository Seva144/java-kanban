package interfaces;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public interface TaskManager {

    ArrayList<Task> getAllTasks();
    ArrayList<EpicTask> getAllEpicTasks();
    ArrayList<SubTask> getAllSubTasks();

    HashMap<Integer, Task> getAllTasksMap();
    HashMap<Integer, SubTask> getAllSubTasksMap();
    HashMap<Integer, EpicTask> getAllEpicTasksMap();

    List<Task> getPrioritizedTasks();
    void addToPrioritizedMap(Task task);

    void removeAllTasks();
    void removeAllEpicTasks();
    void removeAllSubTasks();

    void addTask(Task task) throws FileNotFoundException;
    void removeTask(int id);
    Task getTask(int id);
    void updateTask(int id, Task task);

    void addEpicTask(EpicTask task) throws FileNotFoundException;
    void removeEpicTask(int id);
    EpicTask getEpicTask(int id);

    void addSubTask(SubTask task) throws FileNotFoundException;
    void removeSubTask(int id);
    SubTask getSubTask(int id);
    void updateSubTask(int id, SubTask task);

    void setSubTaskForEpic(EpicTask task,int id) throws FileNotFoundException;
}

