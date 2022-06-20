package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void addNewTask(String s, String s1);

    int addNewTask(Task task);

    void addNewEpic(String s, String s1);

    int addNewEpic(Epic epic);

    void addNewSubtask(int i, String s, String s1);

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    void editTask(Task task);

    void editEpic(Epic epic);

    void editSubtask(Subtask subtask);

    ArrayList<Task> getAllTasks();

    ArrayList<Subtask> getEpicSubtasks(int i);

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    void showAllEpics();

    void delAllTasks();

    void deleteTask(int i);

    void setStatus(int i, Task.TaskStatus doneTask);

    List<Task> history();

    HistoryManager getDefaultHistoryManager();

    void showAllTasks();
}
