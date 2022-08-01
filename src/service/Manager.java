package service;

import model.*;

import java.util.*;

public class Manager {

    public static HashMap<Integer, Task> taskList = new HashMap<>();
    public static HashMap<Integer, EpicTask> epicList = new HashMap<>();
    public static HashMap<Integer, SubTask> subTaskList = new HashMap<>();

    ArrayList<String> statusSubTask = new ArrayList<>();

    static int idGenerate;

    public static int generateId() {
        idGenerate++;
        return idGenerate;
    }

    //Получение всех типов задач

    public HashMap<Integer, Task> getAllTasks() {
        return taskList;
    }

    public HashMap<Integer, EpicTask> getAllEpicTasks() {
        return epicList;
    }

    public HashMap<Integer, SubTask> getAllSubTasks() {
        return subTaskList;
    }

    //Удаление всех типов задач

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeAllEpicTasks() {
        epicList.clear();
    }

    public void removeAllSubTasks() {
        subTaskList.clear();
    }

    //Операции для задач Task

    public void addTask(Task task) {
        taskList.put(task.id, task);
    }

    public void removeTask(int id) {
        taskList.remove(id);
    }

    public void getTask(int id) {
        taskList.get(id);
    }

    public void updateTask(Task task) {
        for (Map.Entry<Integer, Task> update : taskList.entrySet()) {
            if (update.getKey() == task.id) {
                update.setValue(task);
            }
        }
    }

    //Операции для задач EpicTask

    public void addEpicTask(EpicTask task) {
        epicList.put(task.id, task);
    }

    public void removeEpicTask(int id) {
        epicList.remove(id);
        HashMap<Integer, SubTask> subTaskNotDelete = new HashMap<>();
        for(Map.Entry<Integer, SubTask> del : subTaskList.entrySet()){
            if(del.getValue().idEpic!=id){
                subTaskNotDelete.put(del.getKey(),del.getValue());
            }
        }
        subTaskList=subTaskNotDelete;
    }

    public void getEpicTask(int id) {
        epicList.get(id);
    }

    public void updateEpicTask(EpicTask task) {
        for (Map.Entry<Integer, EpicTask> update : epicList.entrySet()) {
            if (update.getKey() == task.id) update.setValue(task);
        }
    }

    //Операции для задач SubTask

    public void addSubTask(SubTask task) {
        subTaskList.put(task.id, task);
    }

    public void removeSubTask(int id) {
        subTaskList.remove(id);
    }

    public void getSubTask(int id) {
        subTaskList.get(id);
    }

    public void updateSubTask(SubTask task) {
        for (Map.Entry<Integer, SubTask> update : subTaskList.entrySet()) {
            if (update.getKey() == task.id) {
                update.setValue(task);
            }
        }
        checkUpdate();
    }

    //Получение всех подзадач эпика

    public void getAllSubTasksOfEpic(int id) {
        ArrayList<SubTask> subTasksOfEpic = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> printEpic : epicList.entrySet()) {
            if (printEpic.getKey() == id) {
                System.out.println(printEpic);
                for (Map.Entry<Integer, SubTask> printSub : subTaskList.entrySet()) {
                    if (printSub.getValue().idEpic == id) {
                        subTasksOfEpic.add(printSub.getValue());
                    }
                }
            }
        }
        System.out.println(subTasksOfEpic);
    }

    //Изменение статусов эпик-задач

    public void checkUpdate() {
        for (Map.Entry<Integer, EpicTask> checkEpic : epicList.entrySet()) {
            for (Map.Entry<Integer, SubTask> checkSub : subTaskList.entrySet()) {
                if (checkSub.getValue().idEpic == checkEpic.getKey()) {
                    statusSubTask.add(String.valueOf(checkSub.getValue().status));
                }
            }
            changeEpicIdStatus(statusSubTask, checkEpic.getKey());
            statusSubTask.clear();
        }
    }

    public void changeEpicIdStatus(ArrayList<String> statuses, int idEpic) {
        if (statuses.contains("IN_PROCESS") || (statuses.contains("DONE") && statuses.contains("NEW"))) {
            for (Map.Entry<Integer, EpicTask> checkEpic : epicList.entrySet()) {
                if (checkEpic.getKey() == idEpic) {
                    checkEpic.getValue().status = StatusTask.IN_PROCESS;
                }
            }
        } else if (statuses.contains("DONE") && !statuses.contains("NEW") && !statuses.contains("IN_PROCESS")) {
            for (Map.Entry<Integer, EpicTask> checkEpic : epicList.entrySet()) {
                if (checkEpic.getKey() == idEpic) {
                    checkEpic.getValue().status = StatusTask.DONE;
                }
            }
        }
    }

}
