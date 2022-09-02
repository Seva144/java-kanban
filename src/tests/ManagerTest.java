package tests;


import taskManager.InMemoryTaskManager;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;


import static taskManager.Managers.getDefaultHistory;

public class ManagerTest {

    @Test
    public void Get_All_Tasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", taskManager.generateId(), StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", taskManager.generateId(), StatusTask.NEW);

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

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", taskManager.generateId(), StatusTask.NEW);
        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", taskManager.generateId(), StatusTask.NEW);

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

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", taskManager.generateId(), StatusTask.NEW);

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

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", taskManager.generateId(), StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", taskManager.generateId(), StatusTask.NEW);
        Task taskNew1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", task1.getId(), StatusTask.IN_PROCESS);
        Task taskNew2 = new Task("Сходить в магазин", "Купить продукты", task2.getId(), StatusTask.IN_PROCESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.updateTask(taskNew1);
        taskManager.updateTask(taskNew2);

        Assert.assertNotSame(taskManager.taskMap.get(task1.getId()),task1);
        Assert.assertNotSame(taskManager.taskMap.get(task2.getId()),task2);
    }

    @Test
    public void Update_EpicTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", taskManager.generateId(), StatusTask.NEW);

        SubTask subTaskNew1 = new SubTask("Техника", "Купить бытовую технику", subTask1.getId(), StatusTask.IN_PROCESS);
        SubTask subTaskNew2 = new SubTask("Мебель", "Собрать кухню", subTask2.getId(), StatusTask.IN_PROCESS);
        SubTask subTaskNew3 = new SubTask("Билеты", "Найти билеты", subTask3.getId(), StatusTask.DONE);

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", taskManager.generateId(), StatusTask.NEW);
        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", taskManager.generateId(), StatusTask.NEW);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.addEpicTask(epicTask1);
        taskManager.addEpicTask(epicTask2);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask2.setSubTaskId(subTask3.getId());

        taskManager.updateSubTask(subTaskNew1);
        taskManager.updateSubTask(subTaskNew2);
        taskManager.updateSubTask(subTaskNew3);

        Assert.assertSame(epicTask1.getStatus(), StatusTask.IN_PROCESS);
        Assert.assertSame(epicTask2.getStatus(), StatusTask.DONE);
    }

    @Test
    public void Update_SubTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", taskManager.generateId(), StatusTask.NEW);

        SubTask subTaskNew1 = new SubTask("Техника", "Купить бытовую технику", subTask1.getId(), StatusTask.IN_PROCESS);
        SubTask subTaskNew2 = new SubTask("Мебель", "Собрать кухню", subTask2.getId(), StatusTask.IN_PROCESS);
        SubTask subTaskNew3 = new SubTask("Билеты", "Найти билеты", subTask3.getId(), StatusTask.DONE);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        taskManager.updateSubTask(subTaskNew1);
        taskManager.updateSubTask(subTaskNew2);
        taskManager.updateSubTask(subTaskNew3);

        Assert.assertNotSame(taskManager.subTaskMap.get(subTask1.getId()),subTask1);
        Assert.assertNotSame(taskManager.subTaskMap.get(subTask2.getId()),subTask2);
        Assert.assertNotSame(taskManager.subTaskMap.get(subTask3.getId()),subTask3);

    }

    @Test
    public void Remove_Task() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", taskManager.generateId(), StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", taskManager.generateId(), StatusTask.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.removeTask(task1.getId());

        HashMap<Integer, Task> expected = taskManager.taskMap;

        Assert.assertFalse(expected.containsKey(task1.getId()));
    }

    @Test
    public void Remove_EpicTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", taskManager.generateId(), StatusTask.NEW);
        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", taskManager.generateId(), StatusTask.NEW);

        taskManager.addEpicTask(epicTask1);
        taskManager.addEpicTask(epicTask2);

        taskManager.removeEpicTask(epicTask1.getId());

        HashMap<Integer, EpicTask> expected = taskManager.epicMap;

        Assert.assertFalse(expected.containsKey(epicTask1.getId()));

    }

    @Test
    public void Check_History(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", taskManager.generateId(), StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", taskManager.generateId(), StatusTask.NEW);

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", taskManager.generateId(), StatusTask.NEW);

        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask2 = new SubTask("Мебель", "Собрать кухню", taskManager.generateId(), StatusTask.NEW);
        SubTask subTask3 = new SubTask("Розетки", "Сделать розетки", taskManager.generateId(), StatusTask.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.addEpicTask(epicTask1);


        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        epicTask1.setSubTaskId(subTask1.getId());
        epicTask1.setSubTaskId(subTask2.getId());
        epicTask1.setSubTaskId(subTask3.getId());

        //1 - вызов задач

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());

        taskManager.getEpicTask(epicTask1.getId());

        taskManager.getSubTask(subTask1.getId());
        taskManager.getSubTask(subTask2.getId());
        taskManager.getSubTask(subTask3.getId());

        System.out.println(getDefaultHistory().getHistory());

        //2 - вызов задач

        taskManager.getSubTask(subTask1.getId());
        taskManager.getSubTask(subTask2.getId());
        taskManager.getSubTask(subTask3.getId());

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());

        taskManager.getEpicTask(epicTask1.getId());

        System.out.println(getDefaultHistory().getHistory());

        //3 - удаление одного таска

        taskManager.removeTask(task1.getId());

        System.out.println(getDefaultHistory().getHistory());

        //3 - удаление эпика

        taskManager.removeEpicTask(epicTask1.getId());

        System.out.println(getDefaultHistory().getHistory());
    }
}