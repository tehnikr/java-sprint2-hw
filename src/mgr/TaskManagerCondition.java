package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManagerCondition {

    protected int taskCount;

    protected String filename;

    protected HashMap<Integer, Task> Tasks;
    protected HashMap<Integer, Epic> Epics;

    protected int hmSize;
    protected int hmFirst;
    protected int hmLast;
    protected ArrayList<Integer> hist = new ArrayList();

    public void saveTaskManagerCondition(FileBackedTasksManager tmgr) {

        taskCount = tmgr.getTaskCount();
        Tasks = tmgr.getTasksHashMap();
        Epics = tmgr.getEpicsHashMap();
        filename = tmgr.getFilename();

        InMemoryHistoryManager historyManager = tmgr.getInMemoryHistoryManager();

        hmSize = historyManager.getSize();
        hmFirst = historyManager.getFirst();
        hmLast = historyManager.getLast();

        List<Task> lt = historyManager.getHistory();
        for (int y = 0; y< lt.size(); y++){
            hist.add(lt.get(y).getId());
        }
    }

    public int getTaskCount() {
        return taskCount;
    }

    public HashMap<Integer, Task> getTasks() {
        return Tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return Epics;
    }

    public InMemoryHistoryManager getHistoryManager() {

        InMemoryHistoryManager hmgrFromJson = new InMemoryHistoryManager();

        for (int i = 0; i < hist.size(); i++) {
            if (Tasks.containsKey(hist.get(i))) {
                hmgrFromJson.add(Tasks.get(hist.get(i)));
            } else if (Epics.containsKey(hist.get(i))) {
                hmgrFromJson.add(Epics.get(hist.get(i)));
            } else {
                for (Integer e : Epics.keySet()) {
                    for (Subtask s : Epics.get(e).getSubtaskList()) {
                        if (s.getId() == hist.get(i)) {
                            //System.out.println("Найденный субтаск: " + s);
                            hmgrFromJson.add(s);
                        }
                    }
                }
            }
        }
        return hmgrFromJson;
    }
}
