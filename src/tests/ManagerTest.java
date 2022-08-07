package tests;

import memoryTaskManager.InMemoryTaskManager;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static memoryTaskManager.Managers.getDefaultHistory;

public class ManagerTest {
    InMemoryTaskManager memoryTaskManager = new InMemoryTaskManager();

    //объекты для тестов

    Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", InMemoryTaskManager.generateId(), StatusTask.NEW);
    Task task2 = new Task("Сходить в магазин", "Купить продукты", InMemoryTaskManager.generateId(), StatusTask.NEW);
    Task taskNew1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", task1.getId(), StatusTask.IN_PROCESS);
    Task taskNew2 = new Task("Сходить в магазин", "Купить продукты", task2.getId(), StatusTask.IN_PROCESS);


    EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", InMemoryTaskManager.generateId(), StatusTask.NEW);
    SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", InMemoryTaskManager.generateId(), StatusTask.NEW);
    SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", InMemoryTaskManager.generateId(), StatusTask.NEW);
    SubTask subTaskNew1 = new SubTask("Техника", "Купить бытовую технику", subTask1.getId(), StatusTask.IN_PROCESS);
    SubTask subTaskNew2 = new SubTask("Мебель", "Собрать кухню", subTask2.getId(), StatusTask.IN_PROCESS);

    EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", InMemoryTaskManager.generateId(), StatusTask.NEW);
    SubTask subTask3 = new SubTask("Билеты", "Найти билеты", InMemoryTaskManager.generateId(), StatusTask.NEW);
    SubTask subTaskNew3 = new SubTask("Билеты", "Найти билеты", subTask3.getId(), StatusTask.DONE);


    @Test
    public void Get_All_Tasks() {
        memoryTaskManager.addTask(task1);
        memoryTaskManager.addTask(task2);

        //список expected
        ArrayList<Task> expected = memoryTaskManager.getAllTasks();

        //список actual
        ArrayList<Task> actual = new ArrayList<>();
        actual.add(task1);
        actual.add(task2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_EpicTasks() {

        memoryTaskManager.addEpicTask(epicTask1);
        memoryTaskManager.addEpicTask(epicTask2);

        ArrayList<EpicTask> expected = memoryTaskManager.getAllEpicTasks();

        ArrayList<EpicTask> actual = new ArrayList<>();
        actual.add(epicTask1);
        actual.add(epicTask2);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_SubTasks() {

        memoryTaskManager.addSubTask(subTask1);
        memoryTaskManager.addSubTask(subTask2);
        memoryTaskManager.addSubTask(subTask3);

        ArrayList<SubTask> expected = memoryTaskManager.getAllSubTasks();

        ArrayList<SubTask> actual = new ArrayList<>();
        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(subTask3);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void Update_Task() {
        memoryTaskManager.addTask(task1);
        memoryTaskManager.addTask(task2);

        memoryTaskManager.updateTask(taskNew1);
        memoryTaskManager.updateTask(taskNew2);

        Assert.assertNotSame(InMemoryTaskManager.taskMap.get(task1.getId()),task1);
        Assert.assertNotSame(InMemoryTaskManager.taskMap.get(task2.getId()),task2);
    }

    @Test
    public void Update_EpicTask() {
        memoryTaskManager.addSubTask(subTask1);
        memoryTaskManager.addSubTask(subTask2);
        memoryTaskManager.addSubTask(subTask3);

        memoryTaskManager.addEpicTask(epicTask1);
        memoryTaskManager.addEpicTask(epicTask2);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask2.setSubTaskId(subTask3.getId());

        memoryTaskManager.updateSubTask(subTaskNew1);
        memoryTaskManager.updateSubTask(subTaskNew2);
        memoryTaskManager.updateSubTask(subTaskNew3);

        Assert.assertSame(epicTask1.getStatus(), StatusTask.IN_PROCESS);
        Assert.assertSame(epicTask2.getStatus(), StatusTask.DONE);
    }

    @Test
    public void Update_SubTask() {
        memoryTaskManager.addSubTask(subTask1);
        memoryTaskManager.addSubTask(subTask2);
        memoryTaskManager.addSubTask(subTask3);

        memoryTaskManager.updateSubTask(subTaskNew1);
        memoryTaskManager.updateSubTask(subTaskNew2);
        memoryTaskManager.updateSubTask(subTaskNew3);

        Assert.assertNotSame(InMemoryTaskManager.subTaskMap.get(subTask1.getId()),subTask1);
        Assert.assertNotSame(InMemoryTaskManager.subTaskMap.get(subTask2.getId()),subTask2);
        Assert.assertNotSame(InMemoryTaskManager.subTaskMap.get(subTask3.getId()),subTask3);

    }

    @Test
    public void Remove_Task() {
        memoryTaskManager.addTask(task1);

        memoryTaskManager.removeTask(task1.getId());

        HashMap<Integer, Task> expected = InMemoryTaskManager.taskMap;

        Assert.assertFalse(expected.containsKey(task1.getId()));
    }

    @Test
    public void Remove_EpicTask() {

        memoryTaskManager.addEpicTask(epicTask1);
        memoryTaskManager.addEpicTask(epicTask2);

        memoryTaskManager.removeEpicTask(epicTask1.getId());

        HashMap<Integer, EpicTask> expected = InMemoryTaskManager.epicMap;

        Assert.assertFalse(expected.containsKey(epicTask1.getId()));

    }

    @Test
    public void Check_History(){
        memoryTaskManager.addTask(task1);
        memoryTaskManager.addTask(task2);

        memoryTaskManager.addEpicTask(epicTask1);
        memoryTaskManager.addEpicTask(epicTask2);

        memoryTaskManager.addSubTask(subTask1);
        memoryTaskManager.addSubTask(subTask2);
        memoryTaskManager.addSubTask(subTask3);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask2.setSubTaskId(subTask3.getId());

        memoryTaskManager.getTask(task1.getId());
        memoryTaskManager.getTask(task2.getId());

        memoryTaskManager.getEpicTask(epicTask1.getId());
        memoryTaskManager.getEpicTask(epicTask2.getId());

        memoryTaskManager.getSubTask(subTask1.getId());
        memoryTaskManager.getSubTask(subTask2.getId());
        memoryTaskManager.getSubTask(subTask3.getId());

        Assert.assertEquals(7, getDefaultHistory().getHistory().size());

    }
}