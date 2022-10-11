package taskManager;

import Exceptions.TaskException;
import interfaces.TaskManager;
import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;

import static taskManager.Managers.getDefaultHistory;


public class InMemoryTaskManager implements TaskManager {

    private int idGenerate = 1;

    protected static HashMap<Integer, Task> taskMap = new HashMap<>();
    protected static HashMap<Integer, EpicTask> epicMap = new HashMap<>();
    protected static HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    //Создание и получение списка в порядке приоритета

    private static final Map<LocalDateTime, Task> prioritizedTask = new TreeMap<>();

    public Map<LocalDateTime, Task> getPrioritizedTasks() {
        return prioritizedTask;
    }

    public void addToPrioritizedMap(Task task) {
        prioritizedTask.put(task.getStartTime(), task);
        try {
            checkIntersections();
        } catch (TaskException e) {
            System.err.println(e.getMessage());
        }
    }

    Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return task1.getStartTime().compareTo(task2.getStartTime());
        }
    };

    public void checkIntersections() throws TaskException {
        List<Task> taskList = new ArrayList<>(prioritizedTask.values());
        taskList.sort(comparator);
        for (int i = 0; i < taskList.size() - 2 + 1; i++) {
            if ((taskList.get(i).getEndTime().isAfter(taskList.get(i + 1).getStartTime()))) {
                throw new TaskException("Пересечение по времени");
            }
        }
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

    //получение всех типов задач в коллекции Hashmap

    @Override
    public HashMap<Integer, Task> getAllTasksMap() {
        return taskMap;
    }

    @Override
    public HashMap<Integer, SubTask> getAllSubTasksMap() {
        return subTaskMap;
    }

    @Override
    public HashMap<Integer, EpicTask> getAllEpicTasksMap() {
        return epicMap;
    }

    //Удаление всех типов задач
    @Override
    public void removeAllTasks() {
        for (Map.Entry<Integer, Task> delTask : taskMap.entrySet()) {
            prioritizedTask.remove(delTask.getValue().getStartTime());
        }
        taskMap.clear();
    }

    @Override
    public void removeAllEpicTasks() {
        epicMap.clear();
        for (Map.Entry<Integer, SubTask> delTask : subTaskMap.entrySet()) {
            prioritizedTask.remove(delTask.getValue().getStartTime());
        }
        subTaskMap.clear();
    }

    @Override
    public void removeAllSubTasks() {
        for (Map.Entry<Integer, SubTask> delTask : subTaskMap.entrySet()) {
            prioritizedTask.remove(delTask.getValue().getStartTime());
        }
        subTaskMap.clear();
        for (Map.Entry<Integer, EpicTask> change : epicMap.entrySet()) {
            change.getValue().setStatus(StatusTask.NEW);
            change.getValue().removeAllSubTaskId();
        }
    }

    //Операции для задач Task
    @Override
    public void addTask(Task task) {
        if (task.getId() == 0) {
            if (task.getId() < idGenerate) {
                task.setId(idGenerate);
                idGenerate++;
            }
        }
        taskMap.put(task.getId(), task);
        addToPrioritizedMap(task);
    }

    @Override
    public void removeTask(int id) {
        if (getDefaultHistory().getHistory().contains(taskMap.get(id))) {
            getDefaultHistory().remove(id);
        }
        prioritizedTask.remove(taskMap.get(id).getStartTime());
        taskMap.remove(id);
    }

    @Override
    public Task getTask(int id) {
        getDefaultHistory().add(taskMap.get(id));
        return taskMap.get(id);
    }

    @Override
    public void updateTask(int id, Task task) {
        task.setId(id);
        if (taskMap.containsKey(id)) {
            taskMap.replace(id, task);
        }
        prioritizedTask.remove(taskMap.get(id).getStartTime());
        addToPrioritizedMap(task);
    }

    //Операции для задач EpicTask
    @Override
    public void addEpicTask(EpicTask task) {
        task.setStatus(StatusTask.NEW);
        if (task.getId() == 0) {
            if (task.getId() < idGenerate) {
                task.setId(idGenerate);
                idGenerate++;
            }
        }
        epicMap.put(task.getId(), task);
    }

    @Override
    public void removeEpicTask(int id) {
        for (Integer subId : epicMap.get(id).getSubTaskId()) {
            prioritizedTask.remove(subTaskMap.get(subId).getStartTime());
        }

        if (getDefaultHistory().getHistory().contains(epicMap.get(id))) {
            for (Integer subId : epicMap.get(id).getSubTaskId()) {
                if (getDefaultHistory().getHistory().contains(subTaskMap.get(subId))) {
                    getDefaultHistory().remove(subId);
                }
            }
            getDefaultHistory().remove(id);
        }

        for (Integer subId : epicMap.get(id).getSubTaskId()) {
            subTaskMap.remove(subId);
        }

        epicMap.remove(id);
    }

    @Override
    public EpicTask getEpicTask(int id) {
        getDefaultHistory().add(epicMap.get(id));
        return epicMap.get(id);
    }

    //Операции для задач SubTask
    @Override
    public void addSubTask(SubTask task) {
        if (task.getId() == 0) {
            if (task.getId() < idGenerate) {
                task.setId(idGenerate);
                idGenerate++;
            }
        }
        subTaskMap.put(task.getId(), task);
        addToPrioritizedMap(task);
    }

    @Override
    public void removeSubTask(int id) {
        int epic = 0;
        ArrayList<Integer> newId = new ArrayList<>();

        if (getDefaultHistory().getHistory().contains(subTaskMap.get(id))) {
            getDefaultHistory().remove(id);
        }

        for (Map.Entry<Integer, EpicTask> task : epicMap.entrySet()){
            if(task.getValue().getSubTaskId().contains(id)){
                newId.addAll(task.getValue().getSubTaskId());
                epic=task.getKey();
            }
        }
        if(epic>0) {
            newId.remove(Integer.valueOf(id));
            epicMap.get(epic).removeAllSubTaskId();
            for (Integer idSub : newId) {
                setSubTaskForEpic(epicMap.get(epic), idSub);
            }
        }
        prioritizedTask.remove(subTaskMap.get(id).getStartTime());
        subTaskMap.remove(id);
        checkUpdate();
    }


    @Override
    public SubTask getSubTask(int id) {
        getDefaultHistory().add(subTaskMap.get(id));
        return subTaskMap.get(id);
    }

    @Override
    public void updateSubTask(int id, SubTask task) {
        task.setId(id);
        if (subTaskMap.containsKey(id)) {
            subTaskMap.replace(id, task);
        }
        prioritizedTask.remove(subTaskMap.get(id).getStartTime());
        addToPrioritizedMap(task);
        checkUpdate();
    }

    @Override
    public void setSubTaskForEpic(EpicTask task, int id) {
        task.setSubTaskId(id);
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