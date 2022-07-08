package mgr;

import com.google.gson.Gson;
import tasks.Epic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.File;

public class HTTPTaskManager extends FileBackedTasksManager {

    static String filename = "tasks.csv";

    public HTTPTaskManager() {
        super();
    }

    public HTTPTaskManager(String csvFIle, String t) {
        super(filename);
    }


    public HTTPTaskManager(String UrlStr) throws IOException, InterruptedException {
        super();

        // Создаем json строку из FileBackedTasksManager с загруженными из файла данными
        HTTPTaskManager tempTmgr = new HTTPTaskManager(filename, "sd");
        String jsonStr = tempTmgr.saveToJsonString();

        // Получаем токен
        String UrlRegister = UrlStr + "/register";
        URI uriRegister = URI.create(UrlRegister);
        HttpRequest requestRegister = HttpRequest.newBuilder() // получаем экземпляр билдера
                .GET()    // указываем HTTP-метод запроса
                .uri(uriRegister)// указываем заголовок Accept
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> responseRegister = client.send(requestRegister, handler);

        String apiToken = responseRegister.body();

        System.out.println("Api Token: " + apiToken);

        // Сохраняем состояние в КВСервер

        String UrlSave = UrlStr + "/save/sss" + "?API_TOKEN=" + apiToken;

        URI uriSave = URI.create(UrlSave);

        HttpRequest request = HttpRequest.newBuilder() // получаем экземпляр билдера
                .POST(HttpRequest.BodyPublishers.ofString(jsonStr))    // указываем HTTP-метод запроса
                .uri(uriSave)// указываем заголовок Accept
                .build();

        HttpResponse<String> response = client.send(request, handler);
        System.out.println("Код ответа сохранения состояния: " + response.statusCode());
        System.out.println("Тело ответа сохранения состояния: " + response.body());

        // Состояние менеджера сохранено в KVServer
        // Теперь можно его загрузить...

        String UrlLoad = UrlStr + "/load/sss" + "?API_TOKEN=" + apiToken;
        URI uriLoad = URI.create(UrlLoad);

        HttpRequest requestLoad = HttpRequest.newBuilder() // получаем экземпляр билдера
                .GET()    // указываем HTTP-метод запроса
                .uri(uriLoad)// указываем заголовок Accept
                .build();
        HttpResponse<String> responseLoad = client.send(requestLoad, handler);
        System.out.println("Код ответа загрузки состояния: " + responseLoad.statusCode());
        String jsonStrLoaded = responseLoad.body();

        // Десереализем загруженную строку.

        loadFromJsonString(jsonStrLoaded);

        // We happy!
    }

    public void loadFromJsonString(String j) {
        TaskManagerCondition tMcond = new Gson().fromJson(j, TaskManagerCondition.class);
        this.taskCount = tMcond.getTaskCount();
        this.Tasks = tMcond.getTasks();
        this.Epics = tMcond.getEpics();
        this.setFilename(tMcond.filename);

        this.historyManager = tMcond.getHistoryManager();
    }

    public String saveToJsonString() {
        TaskManagerCondition tMcondition = new TaskManagerCondition();
        tMcondition.saveTaskManagerCondition(this);
        return new Gson().toJson(tMcondition);
    }
}

