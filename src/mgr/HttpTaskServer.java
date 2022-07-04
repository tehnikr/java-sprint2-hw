package mgr;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import tasks.Epic;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static Gson gson = new Gson();
    HttpServer httpServer;
    TaskManager tmgr;
    HistoryManager hmgr;


    public HttpTaskServer(TaskManager mgr) throws IOException {
        this.tmgr = mgr;
        this.hmgr = mgr.getDefaultHistoryManager();
        this.httpServer = HttpServer.create();

        // тут конфигурирование и запуск сервера
        this.httpServer.bind(new InetSocketAddress(PORT), 0);
        this.httpServer.createContext("/tasks", new PostsHandler());


    }

    public void start() {


        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        //httpServer.stop(1);


    }

    class PostsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // ваш код

            String response = "";

            // извлеките метод из запроса
            String method = httpExchange.getRequestMethod();
            System.out.println("Метод запроса: " + method);

            GsonBuilder gsonBuilder = new GsonBuilder();
            String[] pth;

            switch (method) {

                case "GET":

                    pth = httpExchange.getRequestURI().getPath().split("/");
                    System.out.println("Запрос GET: ");
                    System.out.println("pth.length: " + pth.length);
                    for (int i = 0; i < pth.length; i++) {
                        System.out.println("i = " + i + ", pth[" + i + "] = " + pth[i]);
                    }


                    if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("tasks")) {

                        System.out.println("Выводим все задачи...");

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllTasks());

                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 4 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("task")) {

                        int taskNumber = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[3].substring(4));
                        System.out.println("Выводим задачу №" + taskNumber);

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getTask(taskNumber));


                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("epics")) {

                        System.out.println("Выводим все эпики...");

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllEpics());

                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 4 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("epic")) {

                        int epicNumber = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[3].substring(4));
                        System.out.println("Выводим эпик №" + epicNumber);

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getEpic(epicNumber));

                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("subtasks")) {

                        System.out.println("Выводим все субтаски...");

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllSubtasks());


                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("history")) {

                        System.out.println("Выводим историю...");

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(hmgr.getHistory());


                    } else {
                        System.out.println("Неверный запрос");
                        httpExchange.sendResponseHeaders(200, 0);
                        response = "Неверный запрос";
                    }
                    break;


                case "POST":
                    pth = httpExchange.getRequestURI().getPath().split("/");
                    System.out.println("Запрос POST: ");
                    System.out.println("pth.length: " + pth.length);
                    for (int i = 0; i < pth.length; i++) {
                        System.out.println("i = " + i + ", pth[" + i + "] = " + pth[i]);
                    }

                    if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("task")) {

                        System.out.println("Добавляем новый task...");

                        InputStream inputStream = httpExchange.getRequestBody();
                        String json = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

                        Task t = gson.fromJson(json, Task.class);
                        tmgr.addNewTask(t);

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllTasks());

                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("epic")) {

                        System.out.println("Добавляем новый epic...");

                        InputStream inputStream = httpExchange.getRequestBody();
                        String json = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

                        Epic t = gson.fromJson(json, Epic.class);
                        tmgr.addNewEpic(t);

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllEpics());
                    }
                    break;

                case "DELETE":
                    pth = httpExchange.getRequestURI().getPath().split("/");
                    System.out.println("Запрос DELETE: ");
                    System.out.println("pth.length: " + pth.length);
                    for (int i = 0; i < pth.length; i++) {
                        System.out.println("i = " + i + ", pth[" + i + "] = " + pth[i]);
                    }

                    if (httpExchange.getRequestURI().getPath().split("/").length == 3 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("alltasks")) {

                        System.out.println("Всё удаляем...");

                        tmgr.delAllTasks();

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllTasks());
                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 4 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("task")) {

                        int taskNumber = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[3].substring(4));
                        System.out.println("Удаляем задачу №" + taskNumber);
                        tmgr.deleteTask(taskNumber);

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllTasks());

                    } else if (httpExchange.getRequestURI().getPath().split("/").length == 4 &&
                            httpExchange.getRequestURI().getPath().split("/")[1].equals("tasks") &&
                            httpExchange.getRequestURI().getPath().split("/")[2].equals("epic")) {

                        int epicNumber = Integer.parseInt(httpExchange.getRequestURI().getPath().split("/")[3].substring(4));
                        System.out.println("Удаляем эпик №" + epicNumber);
                        tmgr.deleteTask(epicNumber);

                        httpExchange.sendResponseHeaders(200, 0);
                        response = new Gson().toJson(tmgr.getAllEpics());
                    }
                    break;

                default:
                    response = "Некорректный метод!";
            }

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

}
