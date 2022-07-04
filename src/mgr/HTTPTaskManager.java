package mgr;

import com.google.gson.Gson;
import tasks.Epic;

import java.io.File;

public class HTTPTaskManager extends FileBackedTasksManager {

    static String filename = "tasks.csv";

    static FileBackedTasksManager tmgr = FileBackedTasksManager.loadFromFile(new File(filename));
    //static FileBackedTasksManager httpMgr;

    public HTTPTaskManager(String UrlStr) {

    }

    public static FileBackedTasksManager loadFromURL(String url) {

        //return new Gson().fromJson(jsonStr, FileBackedTasksManager.class);

        String jsonTmgr = new Gson().toJson(tmgr);
        System.out.println("jsonTmgr: " + jsonTmgr);

        // FileBackedTasksManager httpMgr = new Gson().fromJson(jsonTmgr, FileBackedTasksManager.class);


        // Как я понял, из KVServer мы, в конечном итоге, получим строку
        // из которой нужно десериализовать FileBackedTasksManager

        // Соответственно я решил пропустить этап с сервером и сделать это преобразование без
        // сервера.

        // Но у меня выпадает ошибка:

        //  ... Caused by: java.lang.RuntimeException: Unable to create instance of interface mgr.HistoryManager.
        //  Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.
        //	at com.google.gson.internal.ConstructorConstructor$16.construct(ConstructorConstructor.java:275) ...

        // и я не знаю как её устранить.  :(

        // return httpMgr;

        return tmgr;
    }
}

