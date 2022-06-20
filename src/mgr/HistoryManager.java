package mgr;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    static List<Integer> fromString(String value) {
        ArrayList hist = new ArrayList();
        String t[] = value.split(",");

        if (t.length != 1) {
            for (int i = 0; i < t.length; i++) {
                hist.add(Integer.parseInt(t[i]));
            }
        }
        return hist;
    }

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

    static String toString(HistoryManager manager) {
        return manager.toString();
    }
}
