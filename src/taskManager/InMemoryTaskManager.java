package taskManager;

import Exceptions.TaskException;
import interfaces.TaskManager;
import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Comparator.comparing;
import static taskManager.Managers.getDefaultHistory;


public class InMemoryTaskManager implements TaskManager {

    private int idGenerate = 1;

    protected static HashMap<Integer, Task> taskMap = new HashMap<>();
    protected static HashMap<Integer, EpicTask> epicMap = new HashMap<>();
    protected static HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    //Создание и получение списка в порядке приоритета

    Comparator<Task> comparator = comparing(Task::getStartTime);

    Set<Task> prioritizedTask = new TreeSet<>(comparator);

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTask);
    }

    public void addToPrioritizedMap(Task task) {
        try {
            checkIntersections();
        } catch (TaskException e) {
            System.err.println(e.getMessage());
        }
        prioritizedTask.removeIf(del -> del.getId() == task.getId());
        prioritizedTask.add(task);
    }

    public void checkIntersections() throws TaskException {
        for (int i = 0; i < getPrioritizedTasks().size() - 2 + 1; i++) {
            if ((getPrioritizedTasks().get(i).getEndTime().isAfter(getPrioritizedTasks().get(i + 1).getStartTime()))) {
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
            prioritizedTask.remove(delTask.getValue());
        }
        taskMap.clear();
    }

    @Override
    public void removeAllEpicTasks() {
        epicMap.clear();
        for (Map.Entry<Integer, SubTask> delTask : subTaskMap.entrySet()) {
            prioritizedTask.remove(delTask.getValue());
        }
        subTaskMap.clear();
    }

    @Override
    public void removeAllSubTasks() {
        for (Map.Entry<Integer, SubTask> delTask : subTaskMap.entrySet()) {
            prioritizedTask.remove(delTask.getValue());
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
        prioritizedTask.remove(taskMap.get(id));
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
//        prioritizedTask.remove(taskMap.get(id));
        addToPrioritizedMap(task);
    }

    //Операции для задач EpicTask
    @Override
    public void addEpicTask(EpicTask task) {
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
            prioritizedTask.remove(subTaskMap.get(subId));
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
        updateTimeEpics();
    }

    @Override
    public void removeSubTask(int id) {
        int epic = returnEpicId(id);

        //1 - очистка истории
        if (getDefaultHistory().getHistory().contains(subTaskMap.get(id))) {
            getDefaultHistory().remove(id);
        }
        //2 - обновление idSubTask эпика
        if(epic>0) {
            ArrayList<Integer> newId = epicMap.get(epic).getSubTaskId();
            newId.remove(Integer.valueOf(id));
            epicMap.get(epic).removeAllSubTaskId();
            for (Integer idSub : newId) {
                setSubTaskForEpic(epicMap.get(epic), idSub);
            }
        }
        //3 - удаление из prioritizedTask
        prioritizedTask.remove(subTaskMap.get(id));
        //4 - удаление из коллекции субТасков
        subTaskMap.remove(id);
        //5 - обновление статуса эпика
        updateStatusEpics();
        //6 - обновление времени эпика
        updateTimeEpics();
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
        prioritizedTask.remove(task);
        addToPrioritizedMap(task);
        updateStatusEpics();
        setSubTaskForEpic(epicMap.get(returnEpicId(id)), id);
    }

    //Изменение статусов эпик-задач, времени и добавление idSubTask

    public void updateStatusEpics() {
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

    public void updateTimeEpics(){
        for(Map.Entry<Integer, EpicTask> taskEpic : epicMap.entrySet()) {
            ArrayList<Integer> subTaskEpic = taskEpic.getValue().getSubTaskId();
            if(!subTaskEpic.isEmpty()) {
                // 1 - обновление startTime
                LocalDateTime startTime = subTaskMap.get(subTaskEpic.get(0)).getStartTime();
                for (Integer idSub : subTaskEpic) {
                    LocalDateTime newTime = subTaskMap.get(subTaskEpic.get(0)).getStartTime();
                    if (startTime.isAfter(newTime)) startTime = newTime;
                }
                taskEpic.getValue().setStartTime(startTime);
                //2 - обновление duration
                long min = 0;
                for (Integer idSub : subTaskEpic) {
                    min = min + subTaskMap.get(idSub).getDuration().toMinutes();
                }
                taskEpic.getValue().setDuration(Duration.ofMinutes(min));
                //3 - обновление endTime
                LocalDateTime endTime = subTaskMap.get(subTaskEpic.get(0)).getStartTime();
                for (Integer idSub : subTaskEpic) {
                    LocalDateTime newTime = subTaskMap.get(idSub).getStartTime();
                    if (endTime.isBefore(newTime)) endTime = newTime;
                }
                taskEpic.getValue().setEndTime(endTime.plus(taskEpic.getValue().getDuration()));
            }
        }
    }


    public int returnEpicId(int id){
        int epic = 0;
        for (Map.Entry<Integer, EpicTask> task : epicMap.entrySet()){
            if(task.getValue().getSubTaskId().contains(id)){
                epic=task.getKey();
            }
        }
        return epic;
    }

    @Override
    public void setSubTaskForEpic(EpicTask task, int id) {
        task.setSubTaskId(id);
        updateTimeEpics();
    }
}