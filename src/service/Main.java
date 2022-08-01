package service;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW);
        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW);

        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);
        SubTask subTask1= new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW, epicTask1.id);
        SubTask subTask2= new SubTask("Мебель", "Собрать кухню", StatusTask.NEW, epicTask1.id);

        EpicTask epicTask2 = new EpicTask("Отдых", "Запланировать отдых", StatusTask.NEW);
        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", StatusTask.NEW, epicTask2.id);

        manager.addTask(task1);
        manager.addTask(task2);

        manager.addEpicTask(epicTask1);
        manager.addEpicTask(epicTask2);

        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpicTasks());
        System.out.println(manager.getAllSubTasks());

        task1.status= StatusTask.IN_PROCESS;
        subTask1.status=StatusTask.IN_PROCESS;
        subTask3.status=StatusTask.DONE;

        manager.updateTask(task1);
        manager.updateSubTask(subTask1);
        manager.updateSubTask(subTask3);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpicTasks());
        System.out.println(manager.getAllSubTasks());

        manager.removeTask(task1.id);
        manager.removeEpicTask(epicTask1.id);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpicTasks());
        System.out.println(manager.getAllSubTasks());
    }
}
