package interfaces;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface TaskManager {

    int generateId();

    ArrayList<Task> getAllTasks();
    ArrayList<EpicTask> getAllEpicTasks();
    ArrayList<SubTask> getAllSubTasks();

    void removeAllTasks();
    void removeAllEpicTasks();
    void removeAllSubTasks();

    void addTask(Task task) throws FileNotFoundException;
    void removeTask(int id);
    void getTask(int id);
    void updateTask(Task task);

    void addEpicTask(EpicTask task) throws FileNotFoundException;
    void removeEpicTask(int id);
    void getEpicTask(int id);
    void updateEpicTask(EpicTask task);

    void addSubTask(SubTask task) throws FileNotFoundException;
    void removeSubTask(int id);
    void getSubTask(int id);
    void updateSubTask(SubTask task);

}
