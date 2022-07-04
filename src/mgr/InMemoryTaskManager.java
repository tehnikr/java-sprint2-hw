package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {


    protected int taskCount = 0;

    protected HashMap<Integer, Task> Tasks;
    protected HashMap<Integer, Epic> Epics;

    protected HistoryManager historyManager = new InMemoryHistoryManager();

    public InMemoryTaskManager() {
        Tasks = new HashMap<>();
        Epics = new HashMap<>();
    }

    public void setHistoryManager(HistoryManager mgr) {
        this.historyManager = mgr;
    }


    @Override
    public void addNewTask(String name, String description) {
        Tasks.put(taskCount, new Task(taskCount, name, description));
        taskCount++;
    }

    @Override
    public int addNewTask(Task task) {
        Tasks.put(taskCount, task);
        taskCount++;
        return taskCount - 1;
    }

    @Override
    public void addNewEpic(String name, String description) {
        Epics.put(taskCount, new Epic(taskCount, name, description));
        taskCount++;
    }

    @Override
    public int addNewEpic(Epic epic) {
        Epics.put(taskCount, epic);
        taskCount++;
        return taskCount - 1;
    }

    @Override
    public void addNewSubtask(int epicId, String name, String description) {

        if (Epics.get(epicId) == null) {
            System.out.println("Эпик %d не найден, невозможно добавить подзадачу в несуществующий эпик");
            return;
        }
        Epics.get(epicId).addSubtask(taskCount, new Subtask(taskCount, epicId, name, description));
        taskCount++;
    }

    @Override
    public Task getTask(int id) {
        Task task = Tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        if (Epics.containsKey(id)) {
            Epic epic = Epics.get(id);
            historyManager.add(epic);
            return epic;
        }
        return null;
    }

    @Override
    public Subtask getSubtask(int id) {
        Integer epicId;
        Subtask subtask = null;
        for (Integer i : Epics.keySet()) {
            for (Subtask k : Epics.get(i).getSubtaskList()) {
                if (k.getId() == id) {
                    epicId = i;
                    subtask = k;
                }
            }
        }
        if (subtask != null) {
            historyManager.add(subtask);
        }

        return subtask;
    }

    @Override
    public void editTask(Task task) {
        Tasks.put(task.getId(), task);
    }


    @Override
    public void editEpic(Epic epic) {
        Epics.put(epic.getId(), epic);
    }

    @Override
    public void editSubtask(Subtask subtask) {
        Epics.get(subtask.getEpicId()).addSubtask(subtask.getId(), subtask);
    }


    public void setStatus(int id, Task.TaskStatus status) {

        for (Integer i : Tasks.keySet()) {
            if (i == id) {
                Tasks.put(id, new Task(id, Tasks.get(id).getName(), Tasks.get(id).getDescription(), status));
            }
        }

        for (Integer i : Epics.keySet()) {
            if (i == id) {
                System.out.println("Невозможно поменять статус эпика !!!");
            }
        }

        for (int i : Epics.keySet()) {
            for (Subtask subtask : Epics.get(i).getSubtaskList()) {
                if (subtask.getId() == id) {
                    Epics.get(i).setSubtaskStatus(id, status);
                }
            }
        }
    }

    public ArrayList<Task> getAllTasks() {

        ArrayList<Task> qwe = new ArrayList<>();

        for (Integer i : Tasks.keySet()) {
            qwe.add(Tasks.get(i));
        }

        return qwe;
    }


    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> qwe = new ArrayList<>();

        for (Integer i : Epics.keySet()) {
            qwe.add(Epics.get(i));
        }

        return qwe;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (Integer i : Epics.keySet()) {
            for (Subtask s : Epics.get(i).getSubtaskList()) {
                subtasks.add(s);
            }
        }
        return subtasks;
    }

    public void showAllEpics() {
        for (Integer i : Epics.keySet()) {
            Epics.get(i).showEpicInfo();
        }
    }

    public void delAllTasks() {
        Tasks.clear();
        Epics.clear();
    }

    public int findEpicIdByName(String name) {

        for (Integer i : Epics.keySet()) {
            if (Epics.get(i).getName().equals(name)) {
                return i;
            } else {
                System.out.println("Ошибка поиска эпика по name - не найдена запись !!!");
                return -1;
            }
        }
        return -1;
    }

    public void deleteTask(int d) {
        for (Integer i : Tasks.keySet()) {
            if (i == d) {
                Tasks.remove(d);
                historyManager.remove(d);
                return;
            }
        }

        for (Integer i : Epics.keySet()) {
            if (i == d) {
                for (Subtask subtask : Epics.get(i).getSubtaskList()) {
                    historyManager.remove(subtask.getId());
                }
                Epics.remove(d);
                historyManager.remove(d);
                return;
            }
        }

        for (Integer i : Epics.keySet()) {
            for (Subtask subtask : Epics.get(i).getSubtaskList()) {
                if (subtask.getId() == d) {
                    Epics.get(i).deleteSubtask(d);
                    historyManager.remove(d);
                    return;
                }
            }
        }
        System.out.println("Id: " + d + " не найден !!!");
    }

    public ArrayList<Subtask> getEpicSubtasks(int id) {
        return Epics.get(id).getSubtaskList();
    }

    public List<Task> history() {
        return historyManager.getHistory();
    }

    @Override
    public HistoryManager getDefaultHistoryManager() {
        return historyManager;
    }


    @Override
    public void showAllTasks() {
        System.out.println("<All tasks>");
        System.out.println("\t<Tasks>");
        for (Integer t : Tasks.keySet()) {
            System.out.println("\t\t" + Tasks.get(t));
        }
        System.out.println("\t</Tasks>");

        System.out.println("\n\t<Epics>");
        for (Integer t : Epics.keySet()) {
            Epic ep = Epics.get(t);
            System.out.println("\t\t" + ep);

            for (Subtask st : ep.getSubtaskList()) {
                System.out.println("\t\t\t" + st);
            }

        }
        System.out.println("\t</Epics>");

        System.out.println("</All tasks>");
    }

    HistoryManager getHistoryManager() {
        return historyManager;
    }
}
