package mgr;

import java.io.File;

public class Managers {
    static String filename = "tasks.csv";

    static FileBackedTasksManager tmgr = FileBackedTasksManager.loadFromFile(new File(filename));

    public static TaskManager getDefault() {
        return tmgr;
    }

    public static HistoryManager getDefaultHistory() {
        return tmgr.getDefaultHistoryManager();
    }

}
