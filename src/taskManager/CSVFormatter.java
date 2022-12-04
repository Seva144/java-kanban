package taskManager;

import interfaces.HistoryManager;
import model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class CSVFormatter {

    //заголовок

    static public String getHeader() {
        return "id,type,name,status,description,Duration,Year,Month,Day,Hours,Minutes,SubTask(Epics)";
    }

    // Ввод/вывод задач

    static public String intoString(Task task) {
        String nameTask = (String.valueOf(task.getClass()).substring(String.valueOf(task.getClass())
                .indexOf(".") + 1)).toUpperCase();
        String duration = null;
        if (task.getDuration() != null) {
            duration = String.valueOf(task.getDuration().toMinutes());
        }
        String year = null;
        String month = null;
        String day = null;
        String hours = null;
        String minutes = null;
        if (task.getStartTime() != null) {
            year = String.valueOf(task.getStartTime().getYear());
            month = String.valueOf(task.getStartTime().getMonthValue());
            day = String.valueOf(task.getStartTime().getDayOfMonth());
            hours = String.valueOf(task.getStartTime().getHour());
            minutes = String.valueOf(task.getStartTime().getMinute());
        }
        return task.getId() + "," + nameTask + "," + task.getName() + "," + task.getStatus()
                + "," + task.getDescription() + "," + duration + "," + year + "," + month + "," + day + "," +
                hours + "," + minutes;
    }

    static public <T extends Task> T fromString(String value) {
        T putTask = null;
        String[] str = value.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(str));
        int id = Integer.parseInt(list.get(0));
        TaskType taskType = TaskType.valueOf(list.get(1));
        String name = list.get(2);
        StatusTask status = StatusTask.valueOf(list.get(3));
        String description = list.get(4);
        int duration = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        int hours = 0;
        int minutes = 0;
        if (!Objects.equals(list.get(5), "null")) {
            duration = Integer.parseInt(list.get(5));
        }
        if (!Objects.equals(list.get(6), "null")) {
            year = Integer.parseInt(list.get(6));
        }
        if (!Objects.equals(list.get(7), "null")) {
            month = Integer.parseInt(list.get(7));
        }
        if(!Objects.equals(list.get(8), "null")){
            day = Integer.parseInt(list.get(8));
        }
        if(!Objects.equals(list.get(9), "null")){
            hours = Integer.parseInt(list.get(9));
        }
        if(!Objects.equals(list.get(10), "null")){
            minutes = Integer.parseInt(list.get(10));
        }

        switch (taskType) {
            case TASK:
                Task task = new Task(name, description, status, Duration.ofMinutes(duration), LocalDateTime.of(year, month, day, hours, minutes));
                task.setId(id);
                putTask = (T) task;
                break;
            case SUBTASK:
                SubTask subTask = new SubTask(name, description, status, Duration.ofMinutes(duration), LocalDateTime.of(year, month, day, hours, minutes));
                subTask.setId(id);
                putTask = (T) subTask;
                break;
            case EPICTASK:
                EpicTask epic = new EpicTask(name, description, status);
                for (int i = 11; i < str.length; i++) {
                    int idSub = Integer.parseInt(str[i]);
                    epic.setSubTaskId(idSub);
                }
                epic.setId(id);
                putTask = (T) epic;
                break;
        }
        return putTask;
    }

    //Ввод/вывод из истории просмотров

    static public String historyToString(HistoryManager manager) {
        ArrayList<Integer> idHistory = new ArrayList<>();
        for (Task id : manager.getHistory()) {
            idHistory.add(id.getId());
        }

        String id = String.valueOf(idHistory).replaceAll("\\s", "");
        return id.substring(id.indexOf("[") + 1, id.lastIndexOf("]"));
    }

    static public List<Integer> historyFromString(String value) {
        ArrayList<Integer> idHistory = new ArrayList<>();
        String[] str = value.split(",");
        for (String id : str) {
            idHistory.add(Integer.parseInt(id));
        }
        return idHistory;
    }
}