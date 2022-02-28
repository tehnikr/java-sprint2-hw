package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int taskCount = 0;

    HashMap<Integer, Task> Tasks;
    HashMap<Integer, Epic> Epics;

    public Manager() {
        Tasks = new HashMap<>();
        Epics = new HashMap<>();
    }


    public void addNewTask(String name, String description) {
        Tasks.put(taskCount, new Task(taskCount, name, description));
        taskCount++;
    }


    // 2.5 Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра
    // Это нужно?
    public void editTask(Task task){
        Tasks.put(task.getId(),task);
    }

    public void editEpic(Epic epic){
        Epics.put(epic.getId(), epic);
    }

    public void editSubtask(Subtask subtask){
        Epics.get(subtask.getEpicId()).addSubtask(subtask.getId(), subtask);
    }
    // 2.5 Обновление....

    public void addNewEpic(String name, String description) {
        Epics.put(taskCount, new Epic(taskCount, name, description));
        taskCount++;
    }

    public void addNewSubtask(int epicId, String name, String description) {

        if (Epics.get(epicId) == null){
            System.out.println("Эпик %d не найден, невозможно добавить подзадачу в несуществующий эпик");
            return;
        }
        Epics.get(epicId).addSubtask(taskCount, new Subtask(taskCount, epicId, name,description));
        taskCount++;
    }

    public void setStatus(int id, String status) {

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
            for (Subtask subtask : Epics.get(i).getSubtaskList()){
                if (subtask.getId() == id){
                    Epics.get(i).setSubtaskStatus(id,status);
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

        for (Integer i : Epics.keySet()){
            for(Subtask s : Epics.get(i).getSubtaskList()){
                subtasks.add(s);
            }
        }
        return subtasks;
    }

    public void showAllEpics(){
        for ( Integer i : Epics.keySet()){
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
                return;
            }
        }

        for (Integer i : Epics.keySet()) {
            if (i == d) {
                Epics.remove(d);
                return;
            }
        }

        for (Integer i : Epics.keySet()) {
            for (Subtask subtask : Epics.get(i).getSubtaskList()){
                if (subtask.getId() == d) {
                    Epics.get(i).deleteSubtask(d);
                    return;
                }
            }
        }
        System.out.println("Id: " + d + " не найден !!!");
    }

    public ArrayList<Subtask> getEpicSubtasks(int id) {
        return Epics.get(id).getSubtaskList();
    }
}
