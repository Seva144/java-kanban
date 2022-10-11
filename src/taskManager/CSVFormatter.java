package taskManager;

import interfaces.HistoryManager;
import model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVFormatter {

    //заголовок

    static public String getHeader() {
        return "id,type,name,status,description,Duration,Year,Month,Day,Hours,Minutes,SubTask(Epics)";
    }

    // Ввод/вывод задач

    static public String intoString(Task task) {
        String nameTask = (String.valueOf(task.getClass()).substring(String.valueOf(task.getClass())
                .indexOf(".") + 1)).toUpperCase();
        String duration = String.valueOf(task.getDuration().toMinutes());
        String year = String.valueOf(task.getStartTime().getYear());
        String month = String.valueOf(task.getStartTime().getMonthValue());
        String day = String.valueOf(task.getStartTime().getDayOfMonth());
        String hours = String.valueOf(task.getStartTime().getHour());
        String minutes = String.valueOf(task.getStartTime().getMinute());

        return task.getId() + "," + nameTask + "," + task.getName() + "," + task.getStatus()
                + "," + task.getDescription() + "," + duration + "," + year + "," + month + "," + day + "," +
                hours + "," + minutes;
    }

    static public <T extends Task> T fromString(String value) {
        T putTask = null;
        try {
            String[] str = value.split(",");
            int id = Integer.parseInt(str[0]);
            TaskType taskType = TaskType.valueOf(str[1]);
            String name = str[2];
            StatusTask status = StatusTask.valueOf(str[3]);
            String description = str[4];
            int duration = Integer.parseInt(str[5]);
            int year = Integer.parseInt(str[6]);
            int month = Integer.parseInt(str[7]);
            int day = Integer.parseInt(str[8]);
            int hours = Integer.parseInt(str[9]);
            int minutes = Integer.parseInt(str[10]);

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
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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