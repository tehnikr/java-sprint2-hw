package mgr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class FileBackedTasksManagerTest extends TaskManagerTest {

    FileBackedTasksManager t;

    @BeforeEach
    void beforeEach() throws IOException {
        File taskFile = new File("task1.csv");
        taskFile.delete();
        taskFile.createNewFile();
        t = new FileBackedTasksManager("task1.csv");
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

    @Test
    void addNewTaskTestNotId() {
        super.addNewTaskTestNotId(t);
    }


    @Test
    void fileList0Test() throws IOException {

        File taskFile = new File("task1.csv");
        taskFile.delete();

        taskFile.createNewFile();
        FileBackedTasksManager ftm = new FileBackedTasksManager("task1.csv");

        FileBackedTasksManager ftma = new FileBackedTasksManager("task1.csv");

        Assertions.assertEquals(0, ftma.getAllEpics().size(), "Ошибка размера восстановленного FileBackedTasksManager с одним epic");

    }

    @Test
    void fileListTest() throws IOException {

        File taskFile = new File("task1.csv");
        taskFile.delete();

        taskFile.createNewFile();
        FileBackedTasksManager ftm = new FileBackedTasksManager("task1.csv");

        //ftm.getEpic(1);


        ftm.addNewEpic("Epic1", "DescrEpic1");
        ftm.addNewEpic("Epic2", "DescrEpic2");
        FileBackedTasksManager ftma = new FileBackedTasksManager("task1.csv");

        Assertions.assertEquals(2, ftma.getAllEpics().size(), "Ошибка размера восстановленного FileBackedTasksManager с одним epic");

    }

    @Test
    void fileListEpicTest() throws IOException {

        File taskFile = new File("task1.csv");
        taskFile.delete();

        taskFile.createNewFile();
        FileBackedTasksManager ftm = new FileBackedTasksManager("task1.csv");


        ftm.addNewEpic("Epic1", "DescrEpic1");
        ftm.addNewEpic("Epic2", "DescrEpic2");
        ftm.getEpic(1); // Создаем запись в истории
        FileBackedTasksManager ftma = new FileBackedTasksManager("task1.csv");

        Assertions.assertEquals(2, ftma.getAllEpics().size(), "Ошибка размера восстановленного FileBackedTasksManager с одним epic");

    }

    @Test
    void fileListEpicHist0Test() throws IOException {

        File taskFile = new File("task1.csv");
        taskFile.delete();

        taskFile.createNewFile();
        FileBackedTasksManager ftm = new FileBackedTasksManager("task1.csv");


        ftm.addNewEpic("Epic1", "DescrEpic1");
        ftm.addNewEpic("Epic2", "DescrEpic2");

        // Записи в истории нет - история пустая

        FileBackedTasksManager ftma = new FileBackedTasksManager("task1.csv");

        Assertions.assertEquals(2, ftma.getAllEpics().size(), "Ошибка размера восстановленного FileBackedTasksManager с одним epic");
    }
}