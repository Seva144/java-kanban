package taskManager;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static taskManager.CSV_Reader.*;
import static taskManager.Managers.getDefaultHistory;


public class FiledBackedTasksManager extends InMemoryTaskManager {

    static File file;

    public FiledBackedTasksManager() {
        file = new File("src/resources/", "FiledBacked.csv");
    }

    public void print() throws IOException {
        ArrayList<String> print = new ArrayList<>();
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String line = br.readLine();
            print.add(line);
        }
        br.close();

        for (String line : print) {
            System.out.println(line);
        }
    }

    public void loadFromFile() throws IOException {
        List<String> data = new LinkedList<>();
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String line = br.readLine();
            data.add(line);
        }
        br.close();

        for (int i = 1; i < data.size() - 2; i++) {
            String[] line = data.get(i).split(",");
            TaskType taskType = TaskType.valueOf(line[1]);
            switch (taskType) {
                case TASK:
                    addTask(fromString(data.get(i)));
                    break;
                case SUBTASK:
                    addSubTask(fromString(data.get(i)));
                    break;
                case EPICTASK:
                    addEpicTask(fromString(data.get(i)));
                    break;
            }
        }

        List<Integer> history = historyFromString(data.get(data.size() - 1));
        for(Integer id: history){
            if(taskMap.containsKey(id)){
                getDefaultHistory().add(taskMap.get(id));
            } else if(subTaskMap.containsKey(id)){
                getDefaultHistory().add(subTaskMap.get(id));
            } else if(epicMap.containsKey(id)){
                getDefaultHistory().add(epicMap.get(id));
            }
        }
    }

    public void save() throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(getHeader());
            for (Map.Entry<Integer, Task> task : taskMap.entrySet()) {
                printWriter.println(intoString(task.getValue()));
            }
            for (Map.Entry<Integer, SubTask> task : subTaskMap.entrySet()) {
                printWriter.println(intoString(task.getValue()));
            }
            for (Map.Entry<Integer, EpicTask> task : epicMap.entrySet()) {
                String getSubId = String.valueOf(task.getValue().getSubTaskId()).replaceAll("\\s", "");
                printWriter.println(intoString(task.getValue()) + "," + getSubId.substring
                        (getSubId.indexOf("[") + 1, getSubId.lastIndexOf("]")));
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
    public void getTask(int id) {
        super.getTask(id);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSubTask(int id) {
        super.getSubTask(id);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getEpicTask(int id) {
        super.getEpicTask(id);
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
