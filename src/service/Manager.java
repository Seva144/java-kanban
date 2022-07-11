package service;

import model.Task;
import model.Epic;
import model.SubTask;

import java.util.*;


public class Manager {
    Scanner scanner = new Scanner(System.in);

    static GenerationTask generationTask = new GenerationTask();


    static HashMap<Integer, ArrayList<String>> simpleTasks = new HashMap<>();
    static HashMap<Integer, ArrayList<String>> epicTasks = new HashMap<>();
    static HashMap<Integer, ArrayList<String>> epicTasksFromSubTask = new HashMap<>();


    public void setSimpleTask() {
        generationTask.getSimpleTask();
        int idTask;
        for (Task field : generationTask.tasksList) {
            ArrayList<String> fieldsList = new ArrayList<>();
            fieldsList.add(field.getName());
            fieldsList.add(field.getDescription());
            fieldsList.add(field.getStatus());
            idTask = field.getId();
            simpleTasks.put(idTask, fieldsList);
        }
        System.out.println(simpleTasks);
    }

    public void setEpicTasks() {
        generationTask.getEpicTask();
        int idEpicTask = 0;
        for (Epic field : generationTask.epicsList) {
            ArrayList<String> fieldsList = new ArrayList<>();
            fieldsList.add(field.getName());
            fieldsList.add(field.getDescription());
            fieldsList.add(field.getStatus());
            idEpicTask = field.getId();
            epicTasks.put(idEpicTask, fieldsList);
        }

    }

    public void setSubTasks() {
        for (Map.Entry<Integer, SubTask> mapSubTasks : generationTask.subTaskToEpic.entrySet()) {
            ArrayList<String> fieldsList = new ArrayList<>();
            fieldsList.add(mapSubTasks.getValue().getName());
            fieldsList.add(mapSubTasks.getValue().getDescription());
            fieldsList.add(mapSubTasks.getValue().getStatus());
            fieldsList.add(String.valueOf(mapSubTasks.getValue().getIdEpic()));
            epicTasksFromSubTask.put(mapSubTasks.getKey(), fieldsList);
        }
    }

    public void readerTask() {
        System.out.println("Список всех простых задач");
        System.out.println(simpleTasks);
        System.out.println("******************");
        System.out.println("Список всех эпик-задач");
        for (Map.Entry<Integer, ArrayList<String>> printEpic : epicTasks.entrySet()) {
            System.out.println(printEpic);
            String idEpic = String.valueOf(printEpic.getKey());
            System.out.println("Подазадачи эпика");
            for (Map.Entry<Integer, ArrayList<String>> printSub : epicTasksFromSubTask.entrySet()) {
                if (printSub.getValue().contains(idEpic)) {
                    System.out.println(printSub);
                }
            }
            System.out.println("*****************");
        }
    }

    public void findTask() {
        System.out.println("Введите id задачи, которую хотите найти");
        int findId = scanner.nextInt();
        for (Map.Entry<Integer, ArrayList<String>> task1 : simpleTasks.entrySet()) {
            if (task1.getKey() == findId) {
                System.out.println("Это простая задача");
                System.out.println(task1);
            }
        }
        for (Map.Entry<Integer, ArrayList<String>> task2 : epicTasks.entrySet()) {
            if (task2.getKey() == findId) {
                System.out.println("Это эпик-задача");
                System.out.println(task2);
            }
        }
        for (Map.Entry<Integer, ArrayList<String>> task3 : epicTasksFromSubTask.entrySet()) {
            if (task3.getKey() == findId) {
                System.out.println("Это подзадача эпика");
                System.out.println(task3);
            }
        }
    }

    public void changeStatusId() {
        System.out.println("Введите id задачи которую уже сделали");
        int findId = scanner.nextInt();
        for (Map.Entry<Integer, ArrayList<String>> task : simpleTasks.entrySet()) {
            if (task.getKey() == findId) {
                task.getValue().set(2, "DONE");

            }
        }
        for (Map.Entry<Integer, ArrayList<String>> task : epicTasksFromSubTask.entrySet()) {
            if (task.getKey() == findId) {
                task.getValue().set(2, "DONE");
            }
        }
        checkIdStatus();

    }


    public void checkIdStatus() {
        ArrayList<String> statuses = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<String>> checkEpic : epicTasks.entrySet()) {
            String idEpic = String.valueOf(checkEpic.getKey());
            for (Map.Entry<Integer, ArrayList<String>> checkSub : epicTasksFromSubTask.entrySet()) {
                if (checkSub.getValue().contains(idEpic)) {
                    statuses.add(checkSub.getValue().get(2));
                }
            }
            changeEpicIdStatus(statuses, Integer.parseInt(idEpic));
            statuses.clear();
        }

    }

    public void changeEpicIdStatus(ArrayList<String> statuses, int idEpic) {
        if (statuses.contains("NEW") & statuses.contains("DONE")) {
            for (Map.Entry<Integer, ArrayList<String>> checkEpic : epicTasks.entrySet()) {
                if (checkEpic.getKey() == idEpic) {
                    checkEpic.getValue().set(2, "IN_PROCESS");
                }
            }
        } else if (statuses.contains("DONE") & !statuses.contains("NEW")) {
            for (Map.Entry<Integer, ArrayList<String>> checkEpic : epicTasks.entrySet()) {
                if (checkEpic.getKey() == idEpic) {
                    checkEpic.getValue().set(2, "DONE");
                }
            }
        }
    }

    public void clearTask(){
        System.out.println("Что вы хотите удалить: 1 - задачу по id, 2 - все задачи");
        int userInput = scanner.nextInt();
        if(userInput==1) {
            System.out.println("Введите id задачи, которую хотите удалить");
            int findId = scanner.nextInt();
            for (Map.Entry<Integer, ArrayList<String>> task1 : simpleTasks.entrySet()) {
                if (task1.getKey() == findId) {
                    simpleTasks.remove(findId);
                }
            }

            for (Map.Entry<Integer, ArrayList<String>> task2 : epicTasks.entrySet()) {
                if (task2.getKey() == findId) {
                    epicTasks.remove(findId);
                }
            }
            for (Map.Entry<Integer, ArrayList<String>> task3 : epicTasksFromSubTask.entrySet()) {
                if (task3.getKey() == findId) {
                    epicTasksFromSubTask.remove(findId);
                }
            }
        } if(userInput==2){
            simpleTasks.clear();
            epicTasks.clear();
            epicTasksFromSubTask.clear();
        }
    }
}
