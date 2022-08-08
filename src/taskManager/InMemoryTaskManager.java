package taskManager;

import interfaces.TaskManager;
import model.*;
import java.util.*;

import static taskManager.InMemoryHistoryManager.history;
import static taskManager.Managers.getDefaultHistory;


public class InMemoryTaskManager implements TaskManager {

    int idGenerate;

    public int generateId() {
        idGenerate++;
        return idGenerate;
    }


    //Получение всех типов задач
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }
    @Override
    public ArrayList<EpicTask> getAllEpicTasks() {
        return new ArrayList<>(epicMap.values());
    }
    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskMap.values());
    }

    //Удаление всех типов задач
    @Override
    public void removeAllTasks() {
        taskMap.clear();
    }
    @Override
    public void removeAllEpicTasks() {
        epicMap.clear();
        subTaskMap.clear();
    }
    @Override
    public void removeAllSubTasks() {
        subTaskMap.clear();
        for (Map.Entry<Integer, EpicTask> change : epicMap.entrySet()){
            change.getValue().setStatus(StatusTask.NEW);
        }
    }

    //Операции для задач Task
    @Override
    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }
    @Override
    public void removeTask(int id) {
        taskMap.remove(id);
    }
    @Override
    public void getTask(int id) {
        taskMap.get(id);
        getDefaultHistory().add(taskMap.get(id));
    }
    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    //Операции для задач EpicTask
    @Override
    public void addEpicTask(EpicTask task) {
        epicMap.put(task.getId(), task);
    }
    @Override
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
    @Override
    public void getEpicTask(int id) {
        epicMap.get(id);
        getDefaultHistory().add(epicMap.get(id));
    }

    @Override
    public void updateEpicTask(EpicTask task) {
        taskMap.put(task.getId(), task);
    }

    //Операции для задач SubTask
    @Override
    public void addSubTask(SubTask task) {
        subTaskMap.put(task.getId(), task);
    }
    @Override
    public void removeSubTask(int id) {
        subTaskMap.remove(id);
        checkUpdate();
    }
    @Override
    public void getSubTask(int id) {
        subTaskMap.get(id);
        getDefaultHistory().add(subTaskMap.get(id));
    }
    @Override
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