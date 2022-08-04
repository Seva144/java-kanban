package manager;

import model.*;
import java.util.*;

public class Manager {

    public static HashMap<Integer, Task> taskMap = new HashMap<>();
    public static HashMap<Integer, EpicTask> epicMap = new HashMap<>();
    public static HashMap<Integer, SubTask> subTaskMap = new HashMap<>();


    static int idGenerate;

    public static int generateId() {
        idGenerate++;
        return idGenerate;
    }

    //Получение всех типов задач

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public ArrayList<EpicTask> getAllEpicTasks() {
        return new ArrayList<>(epicMap.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskMap.values());
    }

    //Удаление всех типов задач

    public void removeAllTasks() {
        taskMap.clear();
    }

    public void removeAllEpicTasks() {
        epicMap.clear();
        subTaskMap.clear();
    }

    public void removeAllSubTasks() {
        subTaskMap.clear();
        for (Map.Entry<Integer, EpicTask> change : epicMap.entrySet()){
            change.getValue().setStatus(StatusTask.NEW);
        }
    }

    //Операции для задач Task

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void removeTask(int id) {
        taskMap.remove(id);
    }

    public void getTask(int id) {
        taskMap.get(id);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    //Операции для задач EpicTask

    public void addEpicTask(EpicTask task) {
        epicMap.put(task.getId(), task);
    }

    public void removeEpicTask(int id) {
        for (Map.Entry<Integer, EpicTask> del : epicMap.entrySet()) {
            if (del.getKey() == id) {
                ArrayList<Integer> idSubDel = del.getValue().getSubTaskId();
                for (Integer idDel : idSubDel) {
                    subTaskMap.remove(idDel);
                }
            }
        }
        epicMap.remove(id);
    }

    public void getEpicTask(int id) {
        epicMap.get(id);
    }

    public void updateEpicTask(EpicTask task) {
        taskMap.put(task.getId(), task);
    }

    //Операции для задач SubTask

    public void addSubTask(SubTask task) {
        subTaskMap.put(task.getId(), task);
    }

    public void removeSubTask(int id) {
        subTaskMap.remove(id);
        checkUpdate();
    }

    public void getSubTask(int id) {
        subTaskMap.get(id);
    }

    public void updateSubTask(SubTask task) {
        subTaskMap.put(task.getId(), task);
        checkUpdate();
    }

    //Получение всех подзадач эпика

    public ArrayList<SubTask> getAllSubTaskOfEpic(int id) {
        ArrayList<SubTask> subTasksOfEpic = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> getSub : epicMap.entrySet()) {
            if (getSub.getKey() == id) {
                ArrayList<Integer> idSub = getSub.getValue().getSubTaskId();
                for (Integer getId : idSub) {
                    subTasksOfEpic.add(subTaskMap.get(getId));
                }
            }
        }
        return subTasksOfEpic;
    }

    //Изменение статусов эпик-задач

    public void checkUpdate() {
        for (Map.Entry<Integer, EpicTask> checkEpic : epicMap.entrySet()) {
            ArrayList<Integer> addSub = checkEpic.getValue().getSubTaskId();
            ArrayList<StatusTask> statusList = new ArrayList<>();
            for (Integer sub : addSub) {
                if (subTaskMap.containsKey(sub)) {
                    statusList.add(subTaskMap.get(sub).getStatus());
                }
                if (statusList.contains(StatusTask.IN_PROCESS) || statusList.contains(StatusTask.DONE)
                        && statusList.contains(StatusTask.NEW)) {
                    checkEpic.getValue().setStatus(StatusTask.IN_PROCESS);
                } else if (statusList.contains(StatusTask.DONE) && !statusList.contains(StatusTask.NEW)
                        && !statusList.contains(StatusTask.IN_PROCESS)) {
                    checkEpic.getValue().setStatus(StatusTask.DONE);
                }
            }
        }
    }
}
