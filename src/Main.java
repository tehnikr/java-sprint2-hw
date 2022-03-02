import mgr.*;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        TaskManager tmgr = Managers.getDefault();
        HistoryManager hmgr = Managers.getDefaultHistory();

        tmgr.addNewTask("Задача 1", "Описание задачи 1");
        tmgr.addNewTask("Задача 2", "Описание задачи 2");

        tmgr.addNewEpic("Эпик 1", "Описание эпика 1");
        tmgr.addNewEpic("Эпик 2", "Описание эпика 2");

        tmgr.addNewSubtask(2, "Имя подзадачи 1-1", "Описание подзадачи 1-1");
        tmgr.addNewSubtask(2, "Имя подзадачи 1-2", "Описание подзадачи 1-2");
        tmgr.addNewSubtask(3, "Имя подзадачи 2-1", "Описание подзадачи 2-1");

        tmgr.addNewEpic("Эпик 3", "Описание эпика 3");
        tmgr.addNewSubtask(7, "Имя подзадачи 3-1", "Описание подзадачи 3-1");
        tmgr.addNewSubtask(7, "Имя подзадачи 3-2", "Описание подзадачи 3-2");
        tmgr.addNewSubtask(7, "Имя подзадачи 3-3", "Описание подзадачи 3-3");

        tmgr.addNewEpic("Эпик 4", "Описание эпика 4");
        tmgr.addNewSubtask(11, "Имя подзадачи 4-1", "Описание подзадачи 4-1");
        tmgr.addNewSubtask(11, "Имя подзадачи 4-2", "Описание подзадачи 4-2");
        tmgr.addNewSubtask(11, "Имя подзадачи 4-3", "Описание подзадачи 4-3");

        System.out.println(tmgr.getAllTasks());


        // Изменяем задачу 2
        tmgr.editTask(new Task(1, "Задача 2", "Измен Оп задачи 2", Task.TaskStatus.IN_PROGRESS_TASK));

        System.out.println(tmgr.getAllTasks());


        System.out.println(tmgr.getEpicSubtasks(2));
        System.out.println(tmgr.getEpicSubtasks(3));

        System.out.println(tmgr.getAllEpics());
        System.out.println(tmgr.getAllSubtasks());


        System.out.println("Эпики:");
        tmgr.showAllEpics();

        System.out.println("Меняем статусы:");
        tmgr.setStatus(1, Task.TaskStatus.DONE_TASK);

        tmgr.setStatus(5, Task.TaskStatus.IN_PROGRESS_TASK);
        tmgr.setStatus(6, Task.TaskStatus.DONE_TASK);

        tmgr.setStatus(12, Task.TaskStatus.DONE_TASK);
        tmgr.setStatus(13, Task.TaskStatus.IN_PROGRESS_TASK);
        tmgr.setStatus(14, Task.TaskStatus.DONE_TASK);

        System.out.println("Эпики после смены статуса:");
        tmgr.showAllEpics();
        System.out.println("</>Эпики после смены статуса:");

        System.out.println(tmgr.getAllEpics());
        System.out.println(tmgr.getAllSubtasks());


        System.out.println("<Проверка истории>");
        tmgr.getTask(0);
        tmgr.getEpic(2);
        tmgr.getSubtask(4);
        tmgr.getTask(1);
        tmgr.getEpic(3);
        tmgr.getSubtask(5);
        tmgr.getTask(0);
        tmgr.getEpic(7);
        tmgr.getSubtask(6);
        tmgr.getTask(1);
        tmgr.getEpic(2);
        tmgr.getSubtask(8);

        //System.out.println(tmgr.history());
        List<Task> l = tmgr.history();
        System.out.println("  Размер истории: " + l.size());

        for (Task t : l) {
            System.out.println("    " + t);
        }
        System.out.println("</Проверка истории>");

        System.out.println("Удаляем 1-й:");
        tmgr.deleteTask(1);
        System.out.println("Удалён");

        System.out.println("Удаляем все:");

        tmgr.delAllTasks();

        System.out.println(tmgr.getAllTasks());
        System.out.println(tmgr.getAllEpics());
        System.out.println(tmgr.getAllSubtasks());
    }
}
