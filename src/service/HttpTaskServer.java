package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import interfaces.TaskManager;
import model.EpicTask;
import model.SubTask;
import model.Task;
import taskManager.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;
import static taskManager.Managers.getGson;

public class HttpTaskServer {

    private final int PORT = 8080;

    private final HttpServer server;
    private final Gson gson;
    private final TaskManager manager;

    public HttpTaskServer(TaskManager manager) throws IOException {
        this.manager = manager;
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new Handler());
    }

    class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                System.out.println("/tasks" + httpExchange.getRequestURI());
                String method = httpExchange.getRequestMethod();
                String path = httpExchange.getRequestURI().getPath();
                int id;
                switch (method) {
                    case ("GET"):
                        if (Pattern.matches("^/tasks/task$", path)) {
                            String response = gson.toJson(manager.getAllTasks());
                            sendText(httpExchange, response);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        } else if (Pattern.matches("^/tasks/subtask$", path)) {
                            String response = gson.toJson(manager.getAllSubTasks());
                            sendText(httpExchange, response);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        } else if (Pattern.matches("^/tasks/epic$", path)) {
                            String response = gson.toJson(manager.getAllEpicTasks());
                            sendText(httpExchange, response);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        } else if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/task/", ""));
                            if (id != -1) {
                                String response = gson.toJson(manager.getTask(id));
                                sendText(httpExchange, response);
                                httpExchange.sendResponseHeaders(200, 0);
                                return;
                            }
                        } else if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/subtask/", ""));
                            if (id != -1) {
                                String response = gson.toJson(manager.getSubTask(id));
                                sendText(httpExchange, response);
                                httpExchange.sendResponseHeaders(200, 0);
                                return;
                            }
                        } else if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/epic/", ""));
                            if (id != -1) {
                                String response = gson.toJson(manager.getEpicTask(id));
                                sendText(httpExchange, response);
                                httpExchange.sendResponseHeaders(200, 0);
                                return;
                            }
                        } else if (Pattern.matches("^/tasks/history$", path)) {
                            String response = gson.toJson(Managers.getDefaultHistory().getHistory());
                            sendText(httpExchange, response);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        } else if (Pattern.matches("^/tasks/getPrioritizedTasks$", path)) {
                            String response = gson.toJson(manager.getPrioritizedTasks());
                            sendText(httpExchange, response);
                            httpExchange.sendResponseHeaders(200, 0);
                            return;
                        }
                        break;
                    case ("POST"):
                        if (Pattern.matches("^/tasks/task$", path)) {
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            Task task = gson.fromJson(body, Task.class);
                            manager.addTask(task);
                            System.out.println("Задача " + task.getName() + " добавлена");
                            httpExchange.sendResponseHeaders(201, 0);

                        } else if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                            String[] splitStrings = path.split("/");
                            int idEpic = parsePathId(splitStrings[3]);
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            SubTask subTask = gson.fromJson(body, SubTask.class);
                            manager.addSubTask(subTask);
                            manager.setSubTaskForEpic(idEpic, subTask.getId());
                            System.out.println("Подзадача " + subTask.getName() + " добавлена");
                            httpExchange.sendResponseHeaders(201, 0);

                        } else if (Pattern.matches("^/tasks/epic$", path)) {
                            System.out.println("POST: началась обработка /tasks/epic запроса от клиента.\n");
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            httpExchange.sendResponseHeaders(201, 0);
                            EpicTask epicTask = getGson().fromJson(body, EpicTask.class);
                            manager.addEpicTask(epicTask);
                            System.out.println("Эпик-задача " + epicTask.getName() + " добавлена");

                        } else if (Pattern.matches("^/tasks/task/update/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/task/update/", ""));
                            if (id != -1) {
                                InputStream inputStream = httpExchange.getRequestBody();
                                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                                Task task = gson.fromJson(body, Task.class);
                                manager.updateTask(id, task);
                                System.out.println("Задача " + task.getName() + " обновлена");
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                        } else if (Pattern.matches("^/tasks/subtask/update/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/subtask/update/", ""));
                            if ((id != -1)) {
                                InputStream inputStream = httpExchange.getRequestBody();
                                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                                SubTask subTask = gson.fromJson(body, SubTask.class);
                                manager.updateSubTask(id, subTask);
                                System.out.println("Задача " + subTask.getName() + " обновлена");
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                        } else if (Pattern.matches("^/tasks/epic/update/\\d+/subtask/\\d+$", path)) {
                            String[] splitStrings = path.split("/");
                            int idEpic = parsePathId(splitStrings[4]);
                            int idSub = parsePathId(splitStrings[6]);
                            if (idEpic != -1 && idSub != -1) {
                                manager.setSubTaskForEpic(idEpic, idSub);
                                System.out.println("В EpicTask добавлен SubTask");
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                        }
                        break;
                    case ("DELETE"):
                        if (Pattern.matches("^/tasks/task$", path)) {
                            manager.removeAllTasks();
                            System.out.println("Все Tasks удалены");
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (Pattern.matches("^/tasks/subtask$", path)) {
                            manager.removeAllSubTasks();
                            System.out.println("Все SubTasks удалены");
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (Pattern.matches("^/tasks/epic$", path)) {
                            manager.removeAllEpicTasks();
                            System.out.println("Все Epics удалены");
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/task/", ""));
                            if (id != -1) {
                                manager.removeTask(id);
                                System.out.println("Удален Task с id = " + id);
                                httpExchange.sendResponseHeaders(200, 0);
                            }
                        } else if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/subtask/", ""));
                            if (id != -1) {
                                manager.removeSubTask(id);
                                System.out.println("Удален SubTask c id = " + id);
                                httpExchange.sendResponseHeaders(200, 0);
                            }
                        } else if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                            id = parsePathId(path.replaceFirst("/tasks/epic/", ""));
                            if (id != -1) {
                                manager.removeEpicTask(id);
                                System.out.println("Удален Epic c id = " + id);
                                httpExchange.sendResponseHeaders(200, 0);
                            }
                        }
                        break;
                    default:
                        System.out.println("Ждем GET, POST, DELETE, а получили" + method);
                        httpExchange.sendResponseHeaders(404, 0);
                }


            } catch (
                    Exception exception) {
                exception.printStackTrace();

            } finally {
                httpExchange.close();
            }

        }

    }

    private int parsePathId(String pathId) {
        try {
            return Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту" + PORT);
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/tasks");
        server.start();
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
