package taskManager;

import model.*;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static taskManager.CSVFormatter.*;
import static taskManager.Managers.getDefaultHistory;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static File file;

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

    public FileBackedTasksManager(){

    }

    public static FileBackedTasksManager load(Path path) throws IOException {
        FileBackedTasksManager loadManager = new FileBackedTasksManager(path.toFile());
        int idManager = 0;
        List<String> data = new LinkedList<>();
        FileReader reader = new FileReader(path.toFile());
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String line = br.readLine();
            data.add(line);
        }
        br.close();

        if(!Objects.equals(data.get(1), "")) {
            for (int i = 1; i < data.size() - 2; i++) {
                String[] line = data.get(i).split(",");
                TaskType taskType = TaskType.valueOf(line[1]);
                switch (taskType) {
                    case TASK:
                        loadManager.addTask(fromString(data.get(i)));
                        break;
                    case SUBTASK:
                        loadManager.addSubTask(fromString(data.get(i)));
                        break;
                    case EPICTASK:
                        loadManager.addEpicTask(fromString(data.get(i)));
                        break;
                }
            }
        }
        if(!Objects.equals(data.get(data.size() - 1), "")) {
            List<Integer> history = historyFromString(data.get(data.size() - 1));
            for (Integer id : history) {
                if (loadManager.getAllTasksMap().containsKey(id)) {
                    getDefaultHistory().add(loadManager.getAllTasksMap().get(id));
                } else if (loadManager.getAllSubTasksMap().containsKey(id)) {
                    getDefaultHistory().add(loadManager.getAllSubTasksMap().get(id));
                } else if (loadManager.getAllEpicTasksMap().containsKey(id)) {
                    getDefaultHistory().add(loadManager.getAllEpicTasksMap().get(id));
                }
            }
        }
        for(Map.Entry<Integer, Task> task : loadManager.getAllTasksMap().entrySet()){
            int idTask = task.getKey();
            if(idTask>idManager) idManager = idTask;
        }
        for(Map.Entry<Integer, SubTask> task : loadManager.getAllSubTasksMap().entrySet()){
            int idTask = task.getKey();
            if(idTask>idManager) idManager = idTask;
        }
        for(Map.Entry<Integer, EpicTask> task : loadManager.getAllEpicTasksMap().entrySet()){
            int idTask = task.getKey();
            if(idTask>idManager) idManager = idTask;
        }
        idManager += 1;
        loadManager.setIdGenerate(idManager);

        return loadManager;
    }

    public void save() throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(getHeader());
            for (Map.Entry<Integer, EpicTask> task : getAllEpicTasksMap().entrySet()) {
                String getSubId = String.valueOf(task.getValue().getSubTaskId()).replaceAll("\\s", "");
                printWriter.println(intoString(task.getValue()) + "," + getSubId.substring
                        (getSubId.indexOf("[") + 1, getSubId.lastIndexOf("]")));
            }
            for (Map.Entry<Integer, Task> task : getAllTasksMap().entrySet()) {
                printWriter.println(intoString(task.getValue()));
            }
            for (Map.Entry<Integer, SubTask> task : getAllSubTasksMap().entrySet()) {
                printWriter.println(intoString(task.getValue()));
            }
            printWriter.println(" ");
            printWriter.println(historyToString(getDefaultHistory()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addTask(Task task) {
        super.addTask(task);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEpicTask(EpicTask task) {
        super.addEpicTask(task);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSubTask(SubTask task) {
        super.addSubTask(task);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSubTask(int id) {
        super.removeSubTask(id);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEpicTask(int id) {
        super.removeEpicTask(id);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getTask(int id) {
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return super.getTask(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return super.getSubTask(id);
    }

    @Override
    public EpicTask getEpicTask(int id) {
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return super.getEpicTask(id);
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllEpicTasks() {
        super.removeAllEpicTasks();
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(int id, Task task) {
        super.updateTask(id, task);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSubTask(int id, SubTask task) {
        super.updateSubTask(id, task);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSubTaskForEpic(int idEpic, int idSub) {
        super.setSubTaskForEpic(idEpic, idSub);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public void print() throws IOException {
//        ArrayList<String> print = new ArrayList<>();
//        FileReader reader = new FileReader(file);
//        BufferedReader br = new BufferedReader(reader);
//        while (br.ready()) {
//            String line = br.readLine();
//            print.add(line);
//        }
//        br.close();
//
//        for (String line : print) {
//            System.out.println(line);
//        }
//    }
}
