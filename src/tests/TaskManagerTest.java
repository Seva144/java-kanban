package tests;

import interfaces.TaskManager;
import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskManager.FiledBackedTasksManager;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Calendar.*;
import static taskManager.Managers.getDefault;
import static taskManager.Managers.getDefaultHistory;


public abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;

    Task task1;
    EpicTask epicTask1;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;
    Task task2;
    EpicTask epicTask2;
    SubTask subTask4;
    SubTask subTask5;
    Task task3;
    EpicTask epicTask3;

    public void TaskManagerSetUp() throws FileNotFoundException {

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        subTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 11, 0));
        task2 = new Task("Обед", "Пообедать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 12, 0));
        epicTask2 = new EpicTask("Фильм", "Просмотр фильма", StatusTask.NEW);
        subTask4 = new SubTask("Фильм ч.1", "Выбрать часть StarWars", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 14, 0));
        subTask5 = new SubTask("Фильм ч.2", "просмотр StarWars", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 15, 0));
        task3 = new Task("Ужин", "Ужинать", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, 1, 1, 16, 0));

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.addEpicTask(epicTask1);
        taskManager.addEpicTask(epicTask2);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        taskManager.addSubTask(subTask4);
        taskManager.addSubTask(subTask5);

        taskManager.setSubTaskForEpic(epicTask1, subTask1.getId());
        taskManager.setSubTaskForEpic(epicTask1, subTask2.getId());
        taskManager.setSubTaskForEpic(epicTask1, subTask3.getId());
        taskManager.setSubTaskForEpic(epicTask2, subTask4.getId());
        taskManager.setSubTaskForEpic(epicTask2, subTask5.getId());
    }

    @Test
    public void Get_All_Tasks() {
        //список expected
        ArrayList<Task> expected = taskManager.getAllTasks();
        //список actual
        ArrayList<Task> actual = new ArrayList<>();

        actual.add(task1);
        actual.add(task2);
        actual.add(task3);
        //Проверка
        Assertions.assertEquals(expected, actual);

        //Пустой список задач
        taskManager.removeAllTasks();
    }

    @Test
    public void Get_All_SubTasks() {
        //список expected
        ArrayList<SubTask> expected = taskManager.getAllSubTasks();
        //список actual
        ArrayList<SubTask> actual = new ArrayList<>();

        actual.add(subTask1);
        actual.add(subTask2);
        actual.add(subTask3);
        actual.add(subTask4);
        actual.add(subTask5);
        //Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_EpicTasks() {
        //список expected
        ArrayList<EpicTask> expected = taskManager.getAllEpicTasks();
        //список actual
        ArrayList<EpicTask> actual = new ArrayList<>();

        actual.add(epicTask1);
        actual.add(epicTask2);
        //Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_Tasks_Map() {
        HashMap<Integer, Task> expected = taskManager.getAllTasksMap();

        HashMap<Integer, Task> actual = new HashMap<>();

        actual.put(task1.getId(), task1);
        actual.put(task2.getId(), task2);
        actual.put(task3.getId(), task3);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_SubTasks_Map() {
        HashMap<Integer, SubTask> expected = taskManager.getAllSubTasksMap();

        HashMap<Integer, SubTask> actual = new HashMap<>();

        actual.put(subTask1.getId(), subTask1);
        actual.put(subTask2.getId(), subTask2);
        actual.put(subTask3.getId(), subTask3);
        actual.put(subTask4.getId(), subTask4);
        actual.put(subTask5.getId(), subTask5);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_EpicTasks_Map() {
        HashMap<Integer, EpicTask> expected = taskManager.getAllEpicTasksMap();

        HashMap<Integer, EpicTask> actual = new HashMap<>();

        actual.put(epicTask1.getId(), epicTask1);
        actual.put(epicTask2.getId(), epicTask2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Remove_All_Tasks() {
        taskManager.removeAllTasks();
        ArrayList<Task> actual = new ArrayList<>();

        Assertions.assertEquals(taskManager.getAllTasks(), actual);
    }

    @Test
    public void Remove_All_SubTasks() {
        taskManager.removeAllSubTasks();
        ArrayList<SubTask> actual = new ArrayList<>();

        Assertions.assertEquals(taskManager.getAllSubTasks(), actual);
    }

    @Test
    public void Remove_All_EpicTasks() {
        taskManager.removeAllEpicTasks();
        ArrayList<EpicTask> actual = new ArrayList<>();

        Assertions.assertEquals(taskManager.getAllEpicTasks(), actual);
    }

    //Операции для задач Task
    @Test
    public void Add_Task() {
        Assertions.assertTrue(taskManager.getAllTasks().contains(task1));
    }

    @Test
    public void Remove_Task() {
        taskManager.removeTask(task1.getId());
        Assertions.assertFalse(taskManager.getAllTasks().contains(task1));

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.removeTask(12));
    }

    @Test
    public void Get_Task() {
        Task expected = taskManager.getTask(task1.getId());
        Task actual = task1;
        Assertions.assertEquals(expected, actual);

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getTask(12));
    }

    @Test
    public void Update_Task() {
        Task updateTask1 = new Task("Завтрак", "Позавтракать", StatusTask.DONE, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 8, 0));
        taskManager.updateTask(task1.getId(), updateTask1);
        Assertions.assertTrue(taskManager.getAllTasks().contains(updateTask1));

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.updateTask(12, task1));
    }

    //Операции для задач SubTask

    @Test
    public void Add_SubTask() {
        Assertions.assertTrue(taskManager.getAllSubTasks().contains(subTask1));
    }

    @Test
    public void Remove_SubTask() {
        taskManager.removeSubTask(subTask1.getId());
        Assertions.assertFalse(taskManager.getAllSubTasks().contains(subTask1));

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.removeSubTask(12));
    }

    @Test
    public void Get_SubTask() {
        Task expected = taskManager.getSubTask(subTask1.getId());
        Task actual = subTask1;
        Assertions.assertEquals(expected, actual);

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getSubTask(12));
    }

    @Test
    public void Update_SubTask() {
        SubTask updateSubTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.DONE, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        taskManager.updateSubTask(subTask1.getId(), updateSubTask1);
        Assertions.assertTrue(taskManager.getAllSubTasks().contains(updateSubTask1));

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.updateSubTask(12, updateSubTask1));
    }

    //Операции для задач EpicTask
    @Test
    public void Add_EpicTask() {
        Assertions.assertTrue(taskManager.getAllEpicTasks().contains(epicTask1));
    }

    @Test
    public void Remove_EpicTask() {
        taskManager.removeEpicTask(epicTask1.getId());
        Assertions.assertFalse(taskManager.getAllEpicTasks().contains(epicTask1));

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.removeEpicTask(12));
    }

    @Test
    public void Remove_SubTasksOfEpic_After_RemoveEpic() {
        ArrayList<Integer> idSub = epicTask1.getSubTaskId();
        taskManager.removeEpicTask(epicTask1.getId());
        for (Integer id : idSub) {
            Assertions.assertFalse(taskManager.getAllSubTasksMap().containsKey(id));
        }
    }

    @Test
    public void Update_EpicTask() throws FileNotFoundException {
        //Пустой список подзадач
        epicTask3 = new EpicTask("Путешествие", "Спланировать путешествие", StatusTask.NEW);
        taskManager.addEpicTask(epicTask3);
        Assertions.assertSame(epicTask3.getStatus(), StatusTask.NEW);

        //Все подзадачи со статусом NEW
        Assertions.assertSame(epicTask1.getStatus(), StatusTask.NEW);

        //Все подзадачи со статусом DONE
        SubTask updateSubTask4 = new SubTask("Фильм ч.1", "Выбрать часть StarWars", StatusTask.DONE, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 14, 0));
        SubTask updateSubTask5 = new SubTask("Фильм ч.2", "просмотр StarWars", StatusTask.DONE, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 15, 0));
        taskManager.updateSubTask(subTask4.getId(), updateSubTask4);
        taskManager.updateSubTask(subTask5.getId(), updateSubTask5);
        Assertions.assertSame(epicTask2.getStatus(), StatusTask.DONE);

        //Подзадачи со статусом NEW|DONE
        SubTask updateSubTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.DONE, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 9, 0));
        SubTask updateSubTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 10, 0));
        SubTask updateSubTask3 = new SubTask("Уборка ч.3", "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 11, 0));
        taskManager.updateSubTask(subTask1.getId(), updateSubTask1);
        taskManager.updateSubTask(subTask2.getId(), updateSubTask2);
        taskManager.updateSubTask(subTask3.getId(), updateSubTask3);
        Assertions.assertSame(epicTask1.getStatus(), StatusTask.IN_PROCESS);

        //Подзадачи со статусом IN_PROGRESS
        SubTask update2SubTask4 = new SubTask("Фильм ч.1", "Выбрать часть StarWars", StatusTask.IN_PROCESS, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 14, 0));
        SubTask update2SubTask5 = new SubTask("Фильм ч.2", "просмотр StarWars", StatusTask.IN_PROCESS, Duration.ofMinutes(60), LocalDateTime.of(2022, 1, 1, 15, 0));
        taskManager.updateSubTask(subTask4.getId(), update2SubTask4);
        taskManager.updateSubTask(subTask5.getId(), update2SubTask5);
        Assertions.assertSame(epicTask1.getStatus(), StatusTask.IN_PROCESS);

    }

    @Test
    public void Get_EpicTask() {
        Task expected = taskManager.getEpicTask(epicTask1.getId());
        Task actual = epicTask1;
        Assertions.assertEquals(expected, actual);

        //Неверный идентификатор
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getEpicTask(12));
    }

//
//
//    @Test
//    public void CheckSaveLoad() throws IOException {
//        //Создаем и записываем объекты
//        FiledBackedTasksManager taskManagerSave = new FiledBackedTasksManager();
//
//        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, AUGUST, 10, 14, 0));
//        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW, Duration.ofMinutes(40), LocalDateTime.of(2022, AUGUST, 10, 19, 0));
//
//        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни", StatusTask.NEW);
//        EpicTask epicTask2 = new EpicTask("Отдых", "Сходить в кино", StatusTask.NEW);
//
//        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW, Duration.ofMinutes(180), LocalDateTime.of(2022, JUNE, 10, 10, 0));
//        SubTask subTask2 = new SubTask("Сборка кухни", "Собрать кухню", StatusTask.NEW, Duration.ofMinutes(400), LocalDateTime.of(2022, JUNE, 10, 14, 0));
//        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", StatusTask.NEW, Duration.ofMinutes(30), LocalDateTime.of(2022, JULY, 10, 8, 0));
//        SubTask subTask4 = new SubTask("Кино", "Просмотр кино", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, JULY, 10, 13, 0));
//
//        epicTask1.setSubTaskId(subTask1.getId());
//        epicTask1.setSubTaskId(subTask2.getId());
//        epicTask2.setSubTaskId(subTask3.getId());
//        epicTask2.setSubTaskId(subTask4.getId());
//
//        taskManagerSave.addTask(task1);
//        taskManagerSave.addTask(task2);
//
//        taskManagerSave.addEpicTask(epicTask1);
//        taskManagerSave.addEpicTask(epicTask2);
//
//        taskManagerSave.addSubTask(subTask1);
//        taskManagerSave.addSubTask(subTask2);
//        taskManagerSave.addSubTask(subTask3);
//        taskManagerSave.addSubTask(subTask4);
//
//
////        получение всех задач
//
//        ArrayList<Task> expectedTask = taskManagerSave.getAllTasks();
//        ArrayList<SubTask> expectedSubTask = taskManagerSave.getAllSubTasks();
//        ArrayList<EpicTask> expectedEpicTask = taskManagerSave.getAllEpicTasks();
//
////        добавление просмотров задач
//
//        taskManagerSave.getTask(task1.getId());
//
//        taskManagerSave.getEpicTask(epicTask1.getId());
//
//        taskManagerSave.getSubTask(subTask1.getId());
//        taskManagerSave.getSubTask(subTask2.getId());
//
//        try {
//            taskManagerSave.print();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<Task> expectedHistory = getDefaultHistory().getHistory();
//
////        получение задач из файла
//
//        FiledBackedTasksManager taskManagerLoad = new FiledBackedTasksManager();
//        try {
//            taskManagerLoad.loadFromFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println("Сравнение тасков");
//        for (Map.Entry<Integer, Task> expected : taskManagerSave.getAllTasksMap().entrySet()) {
//            if (taskManagerLoad.getAllTasksMap().containsKey(expected.getKey())) {
//                String save = String.valueOf(expected.getValue());
//                String load = String.valueOf(taskManagerLoad.getAllTasksMap().get(expected.getKey()));
//                System.out.println(save.equals(load));
//            } else {
//                System.out.println(false);
//            }
//        }
//
//        System.out.println("Сравнение субтасков");
//        for (Map.Entry<Integer, SubTask> expected : taskManagerSave.getAllSubTasksMap().entrySet()) {
//            if (taskManagerLoad.getAllSubTasksMap().containsKey(expected.getKey())) {
//                String save = String.valueOf(expected.getValue());
//                String load = String.valueOf(taskManagerLoad.getAllSubTasksMap().get(expected.getKey()));
//                System.out.println(save.equals(load));
//            } else {
//                System.out.println(false);
//            }
//        }
//
//        System.out.println("Сравнение эпиков");
//        for (Map.Entry<Integer, EpicTask> expected : taskManagerSave.getAllEpicTasksMap().entrySet()) {
//            if (taskManagerLoad.getAllEpicTasksMap().containsKey(expected.getKey())) {
//                String save = String.valueOf(expected.getValue());
//                String load = String.valueOf(taskManagerLoad.getAllEpicTasksMap().get(expected.getKey()));
//                System.out.println(save.equals(load));
//            } else {
//                System.out.println(false);
//            }
//        }
//
//        List<Task> actualHistory = getDefaultHistory().getHistory();
//
//        System.out.println("Сравнение истории просмотров");
//        System.out.println(String.valueOf(expectedHistory).equals(String.valueOf(actualHistory)));
//
//        System.out.println(getDefault().getAllEpicTasks());
//    }

//    @Test
//    public void GetPrioritizedTasks() {
//        InMemoryTaskManager taskManager = new InMemoryTaskManager();
//
//        Task task1 = new Task("Посмотреть фильм", "Посмотреть Star Wars", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, JUNE, 11, 12, 0));
//        Task task2 = new Task("Сходить в магазин", "Купить продукты", StatusTask.NEW, Duration.ofMinutes(60), LocalDateTime.of(2022, JUNE, 14, 14, 0));
//
//        EpicTask epicTask1 = new EpicTask("Ремонт", "Ремонт кухни");
//        EpicTask epicTask2 = new EpicTask("Отдых", "Сходить в кино");
//
//        SubTask subTask1 = new SubTask("Техника", "Купить бытовую технику", StatusTask.NEW, Duration.ofMinutes(180), LocalDateTime.of(2022, JUNE, 10, 10, 0));
//        SubTask subTask2 = new SubTask("Сборка кухни", "Собрать кухню", StatusTask.NEW, Duration.ofMinutes(400), LocalDateTime.of(2022, JUNE, 10, 14, 0));
//        SubTask subTask3 = new SubTask("Билеты", "Найти билеты", StatusTask.NEW, Duration.ofMinutes(30), LocalDateTime.of(2022, JULY, 10, 8, 0));
//        SubTask subTask4 = new SubTask("Кино", "Просмотр кино", StatusTask.NEW, Duration.ofMinutes(120), LocalDateTime.of(2022, JULY, 10, 13, 0));
//
//        taskManager.addTask(task1);
//        taskManager.addTask(task2);
//
//        taskManager.addEpicTask(epicTask1);
//        taskManager.addEpicTask(epicTask2);
//
//        taskManager.addSubTask(subTask1);
//        taskManager.addSubTask(subTask2);
//        taskManager.addSubTask(subTask3);
//        taskManager.addSubTask(subTask4);
//
//        epicTask1.setSubTaskId(subTask1.getId());
//        epicTask1.setSubTaskId(subTask2.getId());
//        epicTask2.setSubTaskId(subTask3.getId());
//        epicTask2.setSubTaskId(subTask4.getId());
//
//
//        System.out.println(getDefault().getPrioritizedTasks());
//
//
//    }
}