package interfaces;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    int generateId();

    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, EpicTask> epicMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    ArrayList<Task> getAllTasks();
    ArrayList<EpicTask> getAllEpicTasks();
    ArrayList<SubTask> getAllSubTasks();

    void removeAllTasks();
    void removeAllEpicTasks();
    void removeAllSubTasks();

    void addTask(Task task);
    void removeTask(int id);
    void getTask(int id);
    void updateTask(Task task);

    void addEpicTask(EpicTask task);
    void removeEpicTask(int id);
    void getEpicTask(int id);
    void updateEpicTask(EpicTask task);

    void addSubTask(SubTask task);
    void removeSubTask(int id);
    void getSubTask(int id);
    void updateSubTask(SubTask task);

}
