package mgr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManager t;

    @BeforeEach
    public void beforeEach() throws IOException {
        t = new InMemoryTaskManager();
    }

    @Test
    void addNewTaskTest() {
        super.addNewTaskTest(t);
    }

    @Test
    void addNewTaskTest2Task() {
        super.addNewTaskTest2Task(t);
    }

    @Test
    void addNewTaskTestNotId() {
        super.addNewTaskTestNotId(t);
    }

    @Test
    void addNewEpicTest() {
        super.addNewEpicTest(t);
    }

    @Test
    void addNewSubtaskTest() {
        super.addNewSubtaskTest(t);
    }

    @Test
    void delAllTasksTest() {
        super.addNewSubtaskTest(t);
    }

}