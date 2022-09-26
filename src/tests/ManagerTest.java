package tests;

import taskManager.FiledBackedTasksManager;
import taskManager.InMemoryTaskManager;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.Assert;
import org.junit.Test;


import java.io.IOException;
import java.util.*;

import static taskManager.Managers.getDefaultHistory;

public class ManagerTest {

    @Test
    public void Get_All_Tasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        //список expected
        ArrayList<Task> expected = taskManager.getAllTasks();

        //список actual
        ArrayList<Task> actual = new ArrayList<>();
        actual.add(task1);
        actual.add(task2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_EpicTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);
        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", StatusTask.NEW);

        taskManager.addEpicTask(epicTask1);
        taskManager.addEpicTask(epicTask2);

        ArrayList<EpicTask> expected = taskManager.getAllEpicTasks();

        ArrayList<EpicTask> actual = new ArrayList<>();
        actual.add(epicTask1);
        actual.add(epicTask2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_SubTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", StatusTask.NEW);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        ArrayList<SubTask> expected = taskManager.getAllSubTasks();

        ArrayList<SubTask> actual = new ArrayList<>();
        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(subTask3);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Update_Task() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW);
        Task taskNew1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.IN_PROCESS);
        Task taskNew2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.IN_PROCESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.updateTask(task1.getId(),taskNew1);
        taskManager.updateTask(task2.getId(),taskNew2);

        Assert.assertNotSame(taskManager.taskMap.get(task1.getId()),task1);
        Assert.assertNotSame(taskManager.taskMap.get(task2.getId()),task2);
    }

    @Test
    public void Update_EpicTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", StatusTask.NEW);

        SubTask subTaskNew1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.IN_PROCESS);
        SubTask subTaskNew2 = new SubTask("Мебель", "Собрать кухню", StatusTask.IN_PROCESS);
        SubTask subTaskNew3 = new SubTask("Билеты", "Найти билеты", StatusTask.DONE);

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);
        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", StatusTask.NEW);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.addEpicTask(epicTask1);
        taskManager.addEpicTask(epicTask2);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask2.setSubTaskId(subTask3.getId());

        taskManager.updateSubTask(subTask1.getId(), subTaskNew1);
        taskManager.updateSubTask(subTask2.getId(),subTaskNew2);
        taskManager.updateSubTask(subTask3.getId(),subTaskNew3);

        Assert.assertSame(epicTask1.getStatus(), StatusTask.IN_PROCESS);
        Assert.assertSame(epicTask2.getStatus(), StatusTask.DONE);
    }

    @Test
    public void Update_SubTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", StatusTask.NEW);

        SubTask subTaskNew1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.IN_PROCESS);
        SubTask subTaskNew2 = new SubTask("Мебель", "Собрать кухню", StatusTask.IN_PROCESS);
        SubTask subTaskNew3 = new SubTask("Билеты", "Найти билеты", StatusTask.DONE);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.updateSubTask(subTask1.getId(),subTaskNew1);
        taskManager.updateSubTask(subTask2.getId(),subTaskNew2);
        taskManager.updateSubTask(subTask3.getId(), subTaskNew3);

        Assert.assertNotSame(taskManager.subTaskMap.get(subTask1.getId()),subTask1);
        Assert.assertNotSame(taskManager.subTaskMap.get(subTask2.getId()),subTask2);
        Assert.assertNotSame(taskManager.subTaskMap.get(subTask3.getId()),subTask3);

    }

    @Test
    public void Remove_Task() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.removeTask(task1.getId());

        HashMap<Integer, Task> expected = taskManager.taskMap;

        Assert.assertFalse(expected.containsKey(task1.getId()));
    }

    @Test
    public void Remove_EpicTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);
        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", StatusTask.NEW);

        taskManager.addEpicTask(epicTask1);
        taskManager.addEpicTask(epicTask2);

        taskManager.removeEpicTask(epicTask1.getId());

        HashMap<Integer, EpicTask> expected = taskManager.epicMap;

        Assert.assertFalse(expected.containsKey(epicTask1.getId()));

    }

    @Test
    public void Check_History(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW);

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", StatusTask.NEW);
        SubTask subTask3 = new SubTask("Розетки", "Сделать розетки", StatusTask.NEW);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask1.setSubTaskId(subTask3.getId());

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.addEpicTask(epicTask1);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        //первая проверка

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());

        taskManager.getEpicTask(epicTask1.getId());

        taskManager.getSubTask(subTask1.getId());
        taskManager.getSubTask(subTask2.getId());
        taskManager.getSubTask(subTask3.getId());

        System.out.println(getDefaultHistory().getHistory());

        // вторая проверка

        taskManager.getSubTask(subTask1.getId());
        taskManager.getSubTask(subTask2.getId());
        taskManager.getSubTask(subTask3.getId());

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());

        taskManager.getEpicTask(epicTask1.getId());

        System.out.println(getDefaultHistory().getHistory());

        //проверка с удалением Task

        taskManager.removeTask(task1.getId());

        System.out.println(getDefaultHistory().getHistory());

        //вторая проверка c удалением EpicTask

        taskManager.removeEpicTask(epicTask1.getId());

        System.out.println(getDefaultHistory().getHistory());
    }


    @Test
    public void CheckSaveLoad() throws IOException {
        //Создаем и записываем объекты
        FiledBackedTasksManager taskManagerSave = new FiledBackedTasksManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW);

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", StatusTask.NEW);

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);

        taskManagerSave.addTask(task1);
        taskManagerSave.addTask(task2);

        taskManagerSave.addSubTask(subTask1);
        taskManagerSave.addSubTask(subTask2);

        taskManagerSave.addEpicTask(epicTask1);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());

//        получение всех задач

        ArrayList<Task> expectedTask = taskManagerSave.getAllTasks();
        ArrayList<SubTask> expectedSubTask = taskManagerSave.getAllSubTasks();
        ArrayList<EpicTask> expectedEpicTask = taskManagerSave.getAllEpicTasks();

//        добавление просмотров задач

        taskManagerSave.getTask(task1.getId());

        taskManagerSave.getEpicTask(epicTask1.getId());

        taskManagerSave.getSubTask(subTask1.getId());
        taskManagerSave.getSubTask(subTask2.getId());

        taskManagerSave.print();

        List<Task> expectedHistory = getDefaultHistory().getHistory();

//        получение задач из файла

        FiledBackedTasksManager taskManagerLoad = new FiledBackedTasksManager();
        try {
            taskManagerLoad.loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Сравнение тасков");
        for(Map.Entry<Integer, Task> expected : taskManagerSave.taskMap.entrySet()){
            if(taskManagerLoad.taskMap.containsKey(expected.getKey())){
                String save = String.valueOf(expected.getValue());
                String load = String.valueOf(taskManagerLoad.taskMap.get(expected.getKey()));
                System.out.println(save.equals(load));
            } else {
                System.out.println(false);
            }
        }

        System.out.println("Сравнение субтасков");
        for(Map.Entry<Integer, SubTask> expected : taskManagerSave.subTaskMap.entrySet()){
            if(taskManagerLoad.subTaskMap.containsKey(expected.getKey())){
                String save = String.valueOf(expected.getValue());
                String load = String.valueOf(taskManagerLoad.subTaskMap.get(expected.getKey()));
                System.out.println(save.equals(load));
            } else {
                System.out.println(false);
            }
        }

        System.out.println("Сравнение эпиков");
        for(Map.Entry<Integer, EpicTask> expected : taskManagerSave.epicMap.entrySet()){
            if(taskManagerLoad.epicMap.containsKey(expected.getKey())){
                String save = String.valueOf(expected.getValue());
                String load = String.valueOf(taskManagerLoad.epicMap.get(expected.getKey()));
                System.out.println(save.equals(load));
            } else {
                System.out.println(false);
            }
        }

        List<Task> actualHistory = getDefaultHistory().getHistory();

        System.out.println("Сравнение истории просмотров");
        System.out.println(String.valueOf(expectedHistory).equals(String.valueOf(actualHistory)));
    }
}