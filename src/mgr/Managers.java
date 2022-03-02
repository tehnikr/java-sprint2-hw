package mgr;

public class Managers {

    static InMemoryTaskManager tmgr = new InMemoryTaskManager();

    public static TaskManager getDefault() {
        return tmgr;
    }

    public static HistoryManager getDefaultHistory() {
        return tmgr.getDefaultHistoryManager();
    }


}
