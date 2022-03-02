package mgr;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    ArrayList<Task> historyList = new ArrayList<>();

    @Override
    public void add(Task task) {
        historyList.add(task);
        if (historyList.size() > 10) historyList.remove(0); // Реализуем ограничение размера списка
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
