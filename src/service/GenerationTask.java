package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.*;

public class GenerationTask {
    Scanner scanner = new Scanner(System.in);
    int id;

    public List<Task> tasksList = new ArrayList<>();
    public List<Epic> epicsList = new ArrayList<>();
    public HashMap<Integer, SubTask> subTaskToEpic = new HashMap<>();


    public int generateId() {
        id++;
        return id;
    }

    public void getSimpleTask() {
        Task simpleTask = new Task();
        System.out.println("Введите имя задачи");
        String name = scanner.next();
        simpleTask.setName(name);
        System.out.println("Введите описание задачи");
        String description = scanner.next();
        simpleTask.setDescription(description);
        simpleTask.setId(generateId());
        simpleTask.setStatus("NEW");
        tasksList.add(simpleTask);
    }


    public void getEpicTask() {
        Epic epicTask = new Epic();
        System.out.println("Введите имя эпик-задачи");
        String name = scanner.next();
        epicTask.setName(name);
        System.out.println("Введите описание задачи");
        String description = scanner.next();
        epicTask.setDescription(description);
        epicTask.setId(generateId());
        System.out.println("Сколько подзадач включает в себя эпик-задача?");
        int numberSubTask = scanner.nextInt();
        int idEpic = generateId();
        epicTask.setStatus("NEW");
        HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
        for (int i = 0; i < numberSubTask; i++) {
            SubTask subTask = new SubTask();
            System.out.println("Введите имя под-задачи");
            String nameSub = scanner.next();
            subTask.setName(nameSub);
            System.out.println("Введите описание под-задачи");
            String descriptionSub = scanner.next();
            subTask.setDescription(descriptionSub);
            subTask.setId(generateId());
            subTask.setStatus("NEW");
            subTask.setIdEpic(idEpic-1);
            subTaskMap.put(subTask.getId(), subTask);
            epicTask.setSubTasks(subTaskMap);
        }
        epicsList.add(epicTask);
        subTaskToEpic=epicTask.getSubTasks();
    }

}
