package interfaces;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
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
