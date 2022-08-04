package manager;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerTest {
    Manager manager = new Manager();

    //объекты для тестов

    Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", Manager.generateId(), StatusTask.NEW);
    Task task2 = new Task("Сходить в магазин", "Купить продукты", Manager.generateId(), StatusTask.NEW);
    Task taskNew1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", task1.getId(), StatusTask.IN_PROCESS);
    Task taskNew2 = new Task("Сходить в магазин", "Купить продукты", task2.getId(), StatusTask.IN_PROCESS);


    EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", Manager.generateId(), StatusTask.NEW);
    SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", Manager.generateId(), StatusTask.NEW);
    SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", Manager.generateId(), StatusTask.NEW);
    SubTask subTaskNew1 = new SubTask("Техника", "Купить бытовую технику", subTask1.getId(), StatusTask.IN_PROCESS);
    SubTask subTaskNew2 = new SubTask("Мебель", "Собрать кухню", subTask2.getId(), StatusTask.IN_PROCESS);

    EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", Manager.generateId(), StatusTask.NEW);
    SubTask subTask3 = new SubTask("Билеты", "Найти билеты", Manager.generateId(), StatusTask.NEW);
    SubTask subTaskNew3 = new SubTask("Билеты", "Найти билеты", subTask3.getId(), StatusTask.DONE);


    @Test
    public void Get_All_Tasks() {
        manager.addTask(task1);
        manager.addTask(task2);

        //список expected
        ArrayList<Task> expected = manager.getAllTasks();

        //список actual
        ArrayList<Task> actual = new ArrayList<>();
        actual.add(task1);
        actual.add(task2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_EpicTasks() {

        manager.addEpicTask(epicTask1);
        manager.addEpicTask(epicTask2);

        ArrayList<EpicTask> expected = manager.getAllEpicTasks();

        ArrayList<EpicTask> actual = new ArrayList<>();
        actual.add(epicTask1);
        actual.add(epicTask2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_SubTasks() {

        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        ArrayList<SubTask> expected = manager.getAllSubTasks();

        ArrayList<SubTask> actual = new ArrayList<>();
        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(subTask3);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Update_Task() {

        manager.addTask(task1);
        manager.addTask(task2);

        manager.updateTask(taskNew1);
        manager.updateTask(taskNew2);

        HashMap<Integer, Task> expected = Manager.taskMap;
        HashMap<Integer, Task> actual = new HashMap<>();

        actual.put(taskNew1.getId(), taskNew1);
        actual.put(taskNew2.getId(), taskNew2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Update_EpicTask() {

        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        manager.addEpicTask(epicTask1);
        manager.addEpicTask(epicTask2);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask2.setSubTaskId(subTask3.getId());

        manager.updateSubTask(subTaskNew1);
        manager.updateSubTask(subTaskNew2);
        manager.updateSubTask(subTaskNew3);

        HashMap<Integer, EpicTask> expected = Manager.epicMap;
        HashMap<Integer, EpicTask> actual = new HashMap<>();

        EpicTask epicTaskTest1 = new EpicTask("Ремонт", "Ремонт кухни", epicTask1.getId(), StatusTask.IN_PROCESS);
        EpicTask epicTaskTest2 = new EpicTask("Отдых", "Запланировать отдых", epicTask2.getId(), StatusTask.DONE);
        epicTaskTest1.setSubTaskId(subTask1.getId());
        epicTaskTest1.setSubTaskId(subTask2.getId());
        epicTaskTest2.setSubTaskId(subTask3.getId());


        actual.put(epicTaskTest1.getId(), epicTaskTest1);
        actual.put(epicTaskTest2.getId(), epicTaskTest2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Update_SubTask() {

        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        manager.updateSubTask(subTaskNew1);
        manager.updateSubTask(subTaskNew2);
        manager.updateSubTask(subTaskNew3);

        HashMap<Integer, SubTask> expected = Manager.subTaskMap;
        HashMap<Integer, SubTask> actual = new HashMap<>();

        actual.put(subTaskNew1.getId(), subTaskNew1);
        actual.put(subTaskNew2.getId(), subTaskNew2);
        actual.put(subTaskNew3.getId(), subTaskNew3);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Remove_Task() {
        manager.addTask(task1);
        manager.addTask(task2);

        manager.removeTask(task1.getId());

        HashMap<Integer, Task> expected = Manager.taskMap;
        HashMap<Integer, Task> actual = new HashMap<>();

        actual.put(task2.getId(), task2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Remove_EpicTask() {

        manager.addEpicTask(epicTask1);
        manager.addEpicTask(epicTask2);

        manager.removeEpicTask(epicTask1.getId());

        HashMap<Integer, EpicTask> expected = Manager.epicMap;
        HashMap<Integer, EpicTask> actual = new HashMap<>();

        actual.put(epicTask2.getId(),epicTask2);

        Assert.assertEquals(expected, actual);
    }
}