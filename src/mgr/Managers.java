package mgr;

import java.io.File;
import java.io.IOException;

public class Managers {
    static String filename = "tasks.csv";
    static String urlKVServer = "http://localhost:8078";
    static HTTPTaskManager httpMgr;

    static {
        try {
            httpMgr = new HTTPTaskManager(urlKVServer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager getDefault() {
        return httpMgr;
    }

    public static HistoryManager getDefaultHistory() {
        return httpMgr.getDefaultHistoryManager();
    }

}