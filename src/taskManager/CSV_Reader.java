package taskManager;

import interfaces.HistoryManager;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class CSV_Reader {

    //заголовок

    static public String getHeader() {
        return "id,type,name,status,description,SubTask";
    }

    // Ввод/вывод задач

    static public String intoString(Task task) {
        String nameTask = (String.valueOf(task.getClass()).substring(String.valueOf(task.getClass())
                .indexOf(".") + 1)).toUpperCase();
        return task.getId() + "," + nameTask + "," + task.getName() + "," + task.getStatus()
                + "," + task.getDescription();
    }

    static public  <T extends Task> T fromString(String value) {
        T putTask = null;
        try {
            String[] str = value.split(",");
            int id = Integer.parseInt(str[0]);
            TaskType taskType = TaskType.valueOf(str[1]);
            String name = str[2];
            StatusTask status = StatusTask.valueOf(str[3]);
            String description = str[4];

            switch (taskType) {
                case TASK:
                    Task task = new Task(name, description, id, status);
                    putTask = (T) task;
                    break;
                case SUBTASK:
                    SubTask subTask = new SubTask(name, description, id, status);
                    putTask = (T) subTask;
                    break;
                case EPICTASK:
                    EpicTask epic = new EpicTask(name, description, id, status);
                    for (int i = 5; i < str.length; i++) {
                        int idSub = Integer.parseInt(str[i]);
                        epic.setSubTaskId(idSub);
                    }
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
