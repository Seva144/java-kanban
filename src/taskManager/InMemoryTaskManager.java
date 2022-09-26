package taskManager;

import interfaces.TaskManager;
import model.*;

import java.util.*;

import static taskManager.Managers.getDefaultHistory;


public class InMemoryTaskManager implements TaskManager {

    private int idGenerate = 1;

    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, EpicTask> epicMap = new HashMap<>();
    public HashMap<Integer, SubTask> subTaskMap = new HashMap<>();


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
        for (Map.Entry<Integer, EpicTask> change : epicMap.entrySet()) {
            change.getValue().setStatus(StatusTask.NEW);
        }
    }

    //Операции для задач Task
    @Override
    public void addTask(Task task) {
        if (task.getId() < idGenerate) {
            task.setId(idGenerate);
            idGenerate++;
        }
        taskMap.put(task.getId(), task);
    }

    @Override
    public void removeTask(int id) {
        if(getDefaultHistory().getHistory().contains(taskMap.get(id))){
            getDefaultHistory().remove(id);
        }
        taskMap.remove(id);
    }

    @Override
    public void getTask(int id) {
        taskMap.get(id);
        getDefaultHistory().add(taskMap.get(id));
    }

    @Override
    public void updateTask(int id, Task task) {
        if(taskMap.containsKey(id)){
            taskMap.replace(id,task);
        }
    }

    //Операции для задач EpicTask
    @Override
    public void addEpicTask(EpicTask task) {
        if (task.getId() < idGenerate) {
            task.setId(idGenerate);
            idGenerate++;
        }
        epicMap.put(task.getId(), task);
    }

    @Override
    public void removeEpicTask(int id) {
        if(getDefaultHistory().getHistory().contains(epicMap.get(id))){
            getDefaultHistory().remove(id);
        }
        for (Map.Entry<Integer, EpicTask> del : epicMap.entrySet()) {
            if (del.getKey() == id) {
                ArrayList<Integer> idSubDel = del.getValue().getSubTaskId();
                for(Integer idSubHistory: idSubDel){
                    if(getDefaultHistory().getHistory().contains(subTaskMap.get(idSubHistory))){
                        getDefaultHistory().remove(idSubHistory);
                    }
                }
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
    public void updateEpicTask(int id, EpicTask task) {
        if(epicMap.containsKey(id)){
            taskMap.replace(id, task);
        }

    }

    //Операции для задач SubTask
    @Override
    public void addSubTask(SubTask task) {
        if (task.getId() < idGenerate) {
            task.setId(idGenerate);
            idGenerate++;
        }
        subTaskMap.put(task.getId(), task);
    }

    @Override
    public void removeSubTask(int id) {
        if(getDefaultHistory().getHistory().contains(subTaskMap.get(id))){
            getDefaultHistory().remove(id);
        }
        subTaskMap.remove(id);
        checkUpdate();
    }

    @Override
    public void getSubTask(int id) {
        subTaskMap.get(id);
        getDefaultHistory().add(subTaskMap.get(id));
    }

    @Override
    public void updateSubTask(int id, SubTask task) {
        if(subTaskMap.containsKey(id)){
            subTaskMap.replace(id, task);
        }

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