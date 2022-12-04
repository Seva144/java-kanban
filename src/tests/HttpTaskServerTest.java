package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HttpTaskServer;
import taskManager.InMemoryTaskManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static taskManager.Managers.getGson;

public class HttpTaskServerTest {

    private final Gson gson = getGson();
    InMemoryTaskManager manager;
    HttpTaskServer taskServer;

    Task task1;
    EpicTask epicTask1;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;


    @BeforeEach
    public void Http_Server_SetUp() throws IOException {

        manager = new InMemoryTaskManager();

        task1 = new Task("Завтрак", "Позавтракать", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 8, 0));
        epicTask1 = new EpicTask("Уборка", "Убрать квартиру", StatusTask.NEW);
        subTask1 = new SubTask("Уборка ч.1", "Пылесосить", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask2 = new SubTask("Уборка ч.2", "Убрать вещи", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 10, 0));
        subTask3 = new SubTask("Уборка ч.3",
                "Протереть пыль", StatusTask.NEW, Duration.ofMinutes(55), LocalDateTime.of(2022, 1, 1, 11, 0));

        manager.addTask(task1);

        manager.addEpicTask(epicTask1);

        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);

        manager.setSubTaskForEpic(epicTask1.getId(), subTask1.getId());
        manager.setSubTaskForEpic(epicTask1.getId(), subTask2.getId());
        manager.setSubTaskForEpic(epicTask1.getId(), subTask3.getId());

        manager.getTask(task1.getId());
        manager.getSubTask(subTask1.getId());
        manager.getEpicTask(epicTask1.getId());

        taskServer = new HttpTaskServer(manager);

        taskServer.start();
    }

    @AfterEach
    void Stop() {
        taskServer.stop();
    }

    @Test
    void Get_Tasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> tasks = getGson().fromJson(response.body(), tasksType);
        System.out.println(tasks);

        Assertions.assertNotNull(tasks, "Список задач пуст");
        Assertions.assertEquals(1, tasks.size());
        Assertions.assertEquals(manager.getAllTasks(), tasks);
    }

    @Test
    void Get_SubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<ArrayList<SubTask>>() {
        }.getType();
        List<SubTask> tasks = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(tasks, "Список задач пуст");
        Assertions.assertEquals(3, tasks.size());
        Assertions.assertEquals(manager.getAllSubTasks(), tasks);

        System.out.println(manager.getAllSubTasks());
    }

    @Test
    void Get_Epics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<ArrayList<EpicTask>>() {
        }.getType();
        List<EpicTask> tasks = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(tasks, "Список задач пуст");
        Assertions.assertEquals(1, tasks.size());
        Assertions.assertEquals(manager.getAllEpicTasks(), tasks);
    }

    @Test
    void Get_Task_Id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<Task>() {}.getType();
        Task task = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(task, "Список задач пуст");

        Assertions.assertEquals(task1, task);
    }

    @Test
    void Get_SubTask_Id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/3");
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<SubTask>() {}.getType();
        SubTask task = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(task, "Список задач пуст");

        Assertions.assertEquals(subTask1, task);
    }

    @Test
    void Get_Epic_Id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/2");
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<EpicTask>() {}.getType();
        EpicTask task = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(task, "Список задач пуст");

        Assertions.assertEquals(epicTask1, task);
    }

    @Test
    void Get_History() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/history");
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> history = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(history, "Список задач пуст");
    }

    @Test
    void Get_PrioritizedTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/getPrioritizedTasks");
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

        Type tasksType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> prioritizedTask = gson.fromJson(response.body(), tasksType);

        Assertions.assertNotNull(prioritizedTask, "Список задач пуст");
    }

    @Test
    void Delete_Tasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        HttpRequest httpRequest = HttpRequest.newBuilder().DELETE().uri(uri).build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void Delete_SubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void Delete_Epics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void Delete_Tasks_Id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void Delete_Subtasks_Id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/3");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void Delete_Epic_Id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/2");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void Add_Task() throws IOException, InterruptedException {
        Task task2 = new Task("Обед", "Пообедать"
                , StatusTask.NEW, Duration.ofMinutes(115)
                , LocalDateTime.of(2022, 1, 1, 12, 0));
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task");
        String json = getGson().toJson(task2);
        HttpRequest.BodyPublisher body =HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201,  response.statusCode());
        System.out.println(manager.getAllTasks());
    }

    @Test
    void Add_SubTask() throws IOException, InterruptedException {
        SubTask subTask4 = new SubTask("Фильм ч.1", "Выбрать часть StarWars"
                , StatusTask.NEW, Duration.ofMinutes(55)
                , LocalDateTime.of(2022, 1, 1, 14, 0));
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/2");
        String json = getGson().toJson(subTask4);
        HttpRequest.BodyPublisher body =HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201,  response.statusCode());
    }

    @Test
    void Add_Epic() throws IOException, InterruptedException {
        EpicTask epicTask2 = new EpicTask("Фильм", "Просмотр фильма", StatusTask.NEW);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        String json = getGson().toJson(epicTask2);
        HttpRequest.BodyPublisher body =HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201,  response.statusCode());
    }

    @Test
    void Update_Task() throws IOException, InterruptedException{
        Task task1new = new Task("Завтрак", "Позавтракать"
                , StatusTask.DONE, Duration.ofMinutes(55)
                , LocalDateTime.of(2022, 1, 1, 8, 0));
        task1new.setId(task1.getId());
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/update/1");
        String json = getGson().toJson(task1new);
        HttpRequest.BodyPublisher body =HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201,  response.statusCode());
        System.out.println(manager.getAllTasks());
    }

    @Test
    void Update_SubTask() throws IOException, InterruptedException{
        SubTask subTask1new = new SubTask("Уборка ч.1", "Пылесосить"
                , StatusTask.DONE, Duration.ofMinutes(55)
                , LocalDateTime.of(2022, 1, 1, 9, 0));
        subTask1new.setId(subTask1.getId());
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/update/3");
        String json = getGson().toJson(subTask1new);
        HttpRequest.BodyPublisher body =HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201,  response.statusCode());
    }
}
