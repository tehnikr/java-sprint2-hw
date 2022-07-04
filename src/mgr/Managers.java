package mgr;

import java.io.File;

public class Managers {
    static String filename = "tasks.csv";
    static String urlKVServer = "http://localhost:8078";

    //static FileBackedTasksManager httpMgr = FileBackedTasksManager.loadFromFile(new File(filename));
    static FileBackedTasksManager httpMgr = HTTPTaskManager.loadFromURL(urlKVServer);

    public static TaskManager getDefault() {
        return httpMgr;
    }

    public static HistoryManager getDefaultHistory() {
        return httpMgr.getDefaultHistoryManager();
    }

}