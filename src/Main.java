import mgr.Manager;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");

        Manager mgr = new Manager();

        mgr.addNewTask("Задача 1", "Описание задачи 1");
        mgr.addNewTask("Задача 2", "Описание задачи 2");

        mgr.addNewEpic("Эпик 1", "Описание эпика 1");
        mgr.addNewEpic("Эпик 2", "Описание эпика 2");

        mgr.addNewSubtask(2, "Имя подзадачи 1-1", "Описание подзадачи 1-1");
        mgr.addNewSubtask(2, "Имя подзадачи 1-2", "Описание подзадачи 1-2");
        mgr.addNewSubtask(3, "Имя подзадачи 2-1", "Описание подзадачи 2-1");

        mgr.addNewEpic("Эпик 3", "Описание эпика 3");
        mgr.addNewSubtask(7, "Имя подзадачи 3-1", "Описание подзадачи 3-1");
        mgr.addNewSubtask(7, "Имя подзадачи 3-2", "Описание подзадачи 3-2");
        mgr.addNewSubtask(7, "Имя подзадачи 3-3", "Описание подзадачи 3-3");

        mgr.addNewEpic("Эпик 4", "Описание эпика 4");
        mgr.addNewSubtask(11, "Имя подзадачи 4-1", "Описание подзадачи 4-1");
        mgr.addNewSubtask(11, "Имя подзадачи 4-2", "Описание подзадачи 4-2");
        mgr.addNewSubtask(11, "Имя подзадачи 4-3", "Описание подзадачи 4-3");

        System.out.println(mgr.getAllTasks());


        // Изменяем задачу 2
        mgr.editTask(new Task(1,"Задача 2", "Измен Оп задачи 2", Task.tStatInPr));

        System.out.println(mgr.getAllTasks());


        System.out.println(mgr.getEpicSubtasks(2));
        System.out.println(mgr.getEpicSubtasks(3));

        System.out.println(mgr.getAllEpics());
        System.out.println(mgr.getAllSubtasks());


        System.out.println("Эпики:");
        mgr.showAllEpics();

        System.out.println("Меняем статусы:");
        mgr.setStatus(1, Task.tStatDone);

        mgr.setStatus(5, Task.tStatInPr);
        mgr.setStatus(6, Task.tStatDone);

        mgr.setStatus(12, Task.tStatDone);
        mgr.setStatus(13, Task.tStatInPr);
        mgr.setStatus(14, Task.tStatDone);

        System.out.println("Эпики после смены статуса:");
        mgr.showAllEpics();
        System.out.println("</>Эпики после смены статуса:");

        System.out.println(mgr.getAllEpics());
        System.out.println(mgr.getAllSubtasks());

        System.out.println("Удаляем 1-й:");
        mgr.deleteTask(1);
        System.out.println("Удалён");

        System.out.println("Удаляем все:");

        mgr.delAllTasks();

        System.out.println(mgr.getAllTasks());
        System.out.println(mgr.getAllEpics());
        System.out.println(mgr.getAllSubtasks());
    }
}
