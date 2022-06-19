package tasks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    Epic et = new Epic(1,"testEpic", "testEpic");

    @Test
    void createEpicTest() {

        // Проверка работы рассчета статуса с пустым списком подзадач
        assertEquals(et.getStatus(), Task.TaskStatus.NEW_TASK, "Неправильный статус при создании эпика.");

        // Проверка работы рассчета статуса с списком подзадач где все NEW
        et.addSubtask(3, new Subtask(3, 1, "Имя подзадачи 1-1", "Описание подзадачи 1-1"));
        et.addSubtask(4, new Subtask(4, 1, "Имя подзадачи 1-2", "Описание подзадачи 1-2"));
        et.addSubtask(5, new Subtask(5, 1, "Имя подзадачи 1-3", "Описание подзадачи 1-3"));

        assertEquals(et.getStatus(), Task.TaskStatus.NEW_TASK, "Неправильный статус в эпике с тремя NEW_TASK задачами.");

        // Проверка работы рассчета статуса с списком подзадач где все DONE

        et.getSubtask(3).setStatus(Task.TaskStatus.DONE_TASK);
        et.getSubtask(4).setStatus(Task.TaskStatus.DONE_TASK);
        et.examinationEpicStatus();

        assertEquals(et.getStatus(), Task.TaskStatus.IN_PROGRESS_TASK, "Неправильный статус IN_PROGRESS_TASK в эпике с NEW_TASK и DONE_TASK задачами.");

        et.getSubtask(4).setStatus(Task.TaskStatus.IN_PROGRESS_TASK);

        System.out.println("Статус et: " + et.getStatus());
        System.out.println("Статус et.subTask 3: " + et.getSubtask(3).getStatus());
        System.out.println("Статус et.subTask 4: " + et.getSubtask(4).getStatus());
        System.out.println("Статус et.subTask 5: " + et.getSubtask(5).getStatus());

        assertEquals(et.getStatus(), Task.TaskStatus.IN_PROGRESS_TASK, "Неправильный статус IN_PROGRESS_TASK в эпике с IN_PROGRESS_TASK задачами.");

    }
}