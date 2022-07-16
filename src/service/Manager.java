package service;

import model.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Manager {
    Scanner scanner = new Scanner(System.in);

    protected static final MapTask<SimpleTask> simpleTask = new MapTask<>();
    protected static final MapTask<EpicTask> epicTask = new MapTask<>();
    protected static final MapTask<SubTask> subTask = new MapTask<>();

    static int id;

    public static int generateId() {
        id++;
        return id;
    }

    public void getSimpleTask(){
        String name = "nameSimpleTask";
        String description = "descriptionSimpleTask";
        int idSimple = generateId();
        simpleTask.addTask(idSimple,new SimpleTask(name, description, idSimple, StatusTask.NEW));
    }

    public void getEpicTask(){
        String name = "nameEpicTask";
        String description = "descriptionEpicTask";
        int epicId = generateId();
        epicTask.addTask(epicId,new EpicTask(name, description, epicId, StatusTask.NEW));
        for (int i = 0; i < 2; i++) {
            getSubTask(epicId);
        }
    }

    public void getSubTask(int ID){
        String name="nameSubTask";
        String description="descriptionSubTask";
        int id=generateId();
        subTask.addTask(id,new SubTask(name,description,id,StatusTask.NEW, ID));
    }

    public void readTask() {
        for (Map.Entry<Integer, SimpleTask> print : simpleTask.getTasks().entrySet()){
            System.out.println(print);
        }
        for(Map.Entry<Integer, EpicTask> print : epicTask.getTasks().entrySet()){
            System.out.println(print);
        }
        for(Map.Entry<Integer, SubTask> print : subTask.getTasks().entrySet()){
            System.out.println(print);
        }
    }

    public void findTask(){
        int findId = scanner.nextInt();
        for (Map.Entry<Integer, SimpleTask> print : simpleTask.getTasks().entrySet()){
            if(print.getKey()==findId){
                System.out.println(print);
            }
        }
        for (Map.Entry<Integer, EpicTask> print : epicTask.getTasks().entrySet()){
            if(print.getKey()==findId){
                System.out.println(print);
            }
        }
        for (Map.Entry<Integer, SubTask> print : subTask.getTasks().entrySet()){
            if(print.getKey()==findId){
                System.out.println(print);
            }
        }
    }

    public void changeStatusId() {
        int findId = scanner.nextInt();
        for (Map.Entry<Integer, SimpleTask> task : simpleTask.getTasks().entrySet()) {
            if (task.getKey() == findId) {
                task.getValue().status=StatusTask.DONE;
            }
        }
        for (Map.Entry<Integer, SubTask> task : subTask.getTasks().entrySet()) {
            if (task.getKey() == findId) {
                task.getValue().status=StatusTask.DONE;
            }
        }
        checkIdStatus();
    }

    public void checkIdStatus() {
        ArrayList<String> statuses = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> checkEpic : epicTask.getTasks().entrySet()) {
            for (Map.Entry<Integer, SubTask> checkSub : subTask.getTasks().entrySet()) {
                if (checkSub.getValue().idEpic==checkEpic.getKey()) {
                    statuses.add(String.valueOf(checkSub.getValue().status));
                }
            }
            changeEpicIdStatus(statuses, checkEpic.getKey());
            statuses.clear();
        }
    }

    public void changeEpicIdStatus(ArrayList<String> statuses, int idEpic) {
        if (statuses.contains("DONE")&&statuses.contains("NEW")) {
            for (Map.Entry<Integer, EpicTask> checkEpic : epicTask.getTasks().entrySet()) {
                if (checkEpic.getKey() == idEpic) {
                    checkEpic.getValue().status=StatusTask.IN_PROCESS;
                }
            }
        } else if (statuses.contains("DONE") && !statuses.contains("NEW")) {
            for (Map.Entry<Integer, EpicTask> checkEpic : epicTask.getTasks().entrySet()) {
                if (checkEpic.getKey() == idEpic) {
                    checkEpic.getValue().status=StatusTask.DONE;
                }
            }
        }
    }

    public void clearAllTask(){
        simpleTask.getTasks().clear();
        epicTask.getTasks().clear();
        subTask.getTasks().clear();
    }

    public void clearCertainTask(){
        int findId = scanner.nextInt();
        for (Map.Entry<Integer, SimpleTask> print : simpleTask.getTasks().entrySet()){
            if(print.getKey()==findId){
                simpleTask.getTasks().remove(findId);
            }
        }
        for (Map.Entry<Integer, EpicTask> print : epicTask.getTasks().entrySet()){
            if(print.getKey()==findId){
                epicTask.getTasks().remove(findId);
            }
        }
        for (Map.Entry<Integer, SubTask> print : subTask.getTasks().entrySet()){
            if(print.getKey()==findId){
                subTask.getTasks().remove(findId);
            }
        }
    }
}
