package mgr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {


    @Test
    void hmOneTest() {

        HistoryManager hm = new InMemoryHistoryManager();

        Assertions.assertEquals(hm.getHistory().size(), 0, "HistoryManager возвращает не 0 при пустой истории");

        Task t = new Task(1, "Task1", "Task 1 description");

        hm.add(t);

        Assertions.assertEquals(hm.getHistory().size(), 1, "HistoryManager возвращает не 1 при 1 задаче в истории");

        hm.add(t);

        Assertions.assertEquals(hm.getHistory().size(), 1, "HistoryManager возвращает не 1 при дублировании задачи в истории");
    }

    @Test
    void hmThreeFirstTest() {

        HistoryManager hm = new InMemoryHistoryManager();

        hm.add(new Task(1, "Task1", "Task 1 description"));
        hm.add(new Task(2, "Task2", "Task 2 description"));
        hm.add(new Task(3, "Task3", "Task 3 description"));

        hm.remove(1);

        Assertions.assertEquals(hm.getHistory().size(), 2, "HistoryManager возвращает не 2 при удалении 1 задачи из 3-х в истории");
    }

    @Test
    void hmThreeSrTest() {

        HistoryManager hm = new InMemoryHistoryManager();

        hm.add(new Task(1, "Task1", "Task 1 description"));
        hm.add(new Task(2, "Task2", "Task 2 description"));
        hm.add(new Task(3, "Task3", "Task 3 description"));

        hm.remove(2);

        Assertions.assertEquals(hm.getHistory().size(), 2, "HistoryManager возвращает не 2 при удалении 1 задачи из 3-х в истории");
    }

    @Test
    void hmThreeLastTest() {

        HistoryManager hm = new InMemoryHistoryManager();

        hm.add(new Task(1, "Task1", "Task 1 description"));
        hm.add(new Task(2, "Task2", "Task 2 description"));
        hm.add(new Task(3, "Task3", "Task 3 description"));

        hm.remove(3);

        Assertions.assertEquals(hm.getHistory().size(), 2, "HistoryManager возвращает не 2 при удалении 1 задачи из 3-х в истории");
    }


}
