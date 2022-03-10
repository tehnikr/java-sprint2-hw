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
        tmgr.addNewSubtask(2, "Имя подзадачи 2-3", "Описание подзадачи 1-3");

        tmgr.getTask(0);
        System.out.println("История: " + hmgr.getHistory());

        tmgr.getEpic(2);
        System.out.println("История: " + hmgr.getHistory());

        tmgr.getSubtask(4);
        System.out.println("История: " + hmgr.getHistory());

        tmgr.getEpic(3);
        System.out.println("История: " + hmgr.getHistory());

        tmgr.getEpic(2);
        System.out.println("История: " + hmgr.getHistory());

        tmgr.getSubtask(5);
        System.out.println("История: " + hmgr.getHistory());

        hmgr.remove(3);
        System.out.println("История: " + hmgr.getHistory());

    }
}
