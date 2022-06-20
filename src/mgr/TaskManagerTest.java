package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    void addNewTaskTest(T taskManager) {

        Task task = new Task("Test addNewTask", "Test addNewTask description", Task.TaskStatus.NEW_TASK);

        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    // Не пустой список задач

    void addNewTaskTest2Task(T taskManager) {

        Task task1 = new Task("Task1", "Task1 description", Task.TaskStatus.NEW_TASK);
        Task task2 = new Task("Task2", "Task2 description", Task.TaskStatus.NEW_TASK);

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);

        Task task = new Task("Test addNewTask", "Test addNewTask description", Task.TaskStatus.NEW_TASK);


        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(3, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(2), "Задачи не совпадают.");
    }

    // Неверный идентификатор задачи

    void addNewTaskTestNotId(T taskManager) {

        Task task = new Task("Test addNewTask", "Test addNewTask description", Task.TaskStatus.NEW_TASK);

        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");


        final IndexOutOfBoundsException exception = assertThrows(

                // класс ошибки
                IndexOutOfBoundsException.class,

                // создание и переопределение экземпляра класса Executable
                new Executable() {
                    @Override
                    public void execute() {
                        // здесь блок кода, который хотим проверить
                        // при делении на 0 ожидаем ArithmeticException
                        assertEquals(task, tasks.get(3), "Задачи не совпадают.");
                    }
                });

        // можно проверить, находится ли в exception ожидаемый текст
        assertEquals("Index 3 out of bounds for length 1", exception.getMessage());


    }


    void addNewEpicTest(T taskManager) {

        Epic epic = new Epic("Test addNewTask", "Test addNewTask description");

        final int epicId = taskManager.addNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    void addNewSubtaskTest(T taskManager) {
        Epic epic = new Epic("Epic1", "Epic 1 description");
        final int epicId = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask(epicId, 3, "Test addNewTask", "Test addNewTask description");

        taskManager.getEpic(epicId).addSubtask(3, subtask);

        final Epic savedEpic = taskManager.getEpic(epicId);

        final List<Subtask> savedSubtasks = savedEpic.getSubtaskList();

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        assertNotNull(savedSubtasks, "Задачи на возвращаются.");
        assertEquals(1, savedSubtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, savedSubtasks.get(0), "Задачи не совпадают.");
    }

    void delAllTasksTest(T taskManager) {

        taskManager.addNewTask("Task1", "Task1 description");
        taskManager.addNewEpic("Epic1", "Epic 1 description");

        assertNotNull(taskManager.getAllTasks(), "Задачи на возвращаются.");
        assertNotNull(taskManager.getAllEpics(), "Задачи на возвращаются.");

        assertEquals(1, taskManager.getAllTasks(), "Неверное количество задач.");
        assertEquals(1, taskManager.getAllEpics(), "Неверное количество эпиков.");

        taskManager.delAllTasks();

        assertEquals(0, taskManager.getAllTasks(), "Неверное количество задач после удаления.");
        assertEquals(0, taskManager.getAllEpics(), "Неверное количество эпиков после удаления.");
    }


}