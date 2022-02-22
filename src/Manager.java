import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    int taskCount = 0;

    HashMap <Integer,Task> Tasks;
    HashMap <Integer,Subtask> Subtasks;
    HashMap <Integer,Epic> Epics;

    Manager (){
        Tasks       = new HashMap<>();
        Epics       = new HashMap<>();
        Subtasks    = new HashMap<>();
    }


    public void addNewTask(String name, String description) {
        Tasks.put(taskCount, new Task(taskCount, name, description));
        taskCount++;
    }

    public void addNewEpic(String name, String description) {
        Epics.put(taskCount, new Epic(taskCount, name, description));
        taskCount++;
    }

    public void addNewSubtask(int epicId, String name, String description) {
        Subtasks.put(taskCount, new Subtask(taskCount, epicId, name, description));
        taskCount++;
    }

    public void setStatus(int id, String status) {

        for (Integer i : Tasks.keySet()){
            if (i == id) {
                Tasks.put(id, new Task(id, Tasks.get(id).getName(),Tasks.get(id).getDescription(), status));
            }
        }

        for (Integer i : Epics.keySet()){
            if (i == id) {
                System.out.println("Невозможно поменять статус эпика !!!");
            }
        }

        for (Integer i : Subtasks.keySet()){
            if (i == id) {
                Subtasks.put(id, new Subtask(id, Subtasks.get(id).getEpicId(), Subtasks.get(id).getName(), Subtasks.get(id).getDescription(), status));
                examinationEpicStatus();
            }

        }
    }

    private void examinationEpicStatus() {

        for (Integer i: Epics.keySet()) {

            ArrayList<Subtask> ae = Epics.get(i).getSubtasksArr(Subtasks);
            String tmpStatus = Task.tStatDone;

            for (Subtask ik : ae) {
                if (ik.getStatus().equals(Task.tStatInPr)){
                    tmpStatus = Task.tStatInPr;
                }
            }

            boolean isNew = true;
            for (Subtask ik : ae) {
                if (!ik.getStatus().equals(Task.tStatNew)) isNew = false;
            }
            if (isNew){
                tmpStatus = Task.tStatNew;
            }

            boolean isDone = true;
            for (Subtask ik : ae) {
                if (!ik.getStatus().equals(Task.tStatDone)) isDone = false;
            }
            if (isDone) tmpStatus = Task.tStatDone;

            if (ae.size() == 0) tmpStatus = Task.tStatNew;

            Epics.put(Epics.get(i).getId(), new Epic(Epics.get(i).getId(), Epics.get(i).getName(), Epics.get(i).getDescription(), tmpStatus));
        }
    }

    public ArrayList<Task> getAllTasks() {

        ArrayList<Task> qwe = new ArrayList<>();

        for( Integer i : Tasks.keySet()){
            qwe.add(Tasks.get(i));
        }

        return qwe;
    }


    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> qwe = new ArrayList<>();

        for( Integer i : Epics.keySet()){
            qwe.add(Epics.get(i));
        }

        return qwe;
    }

    public ArrayList<Subtask> getAllSubtasks() {

        ArrayList<Subtask> qwe = new ArrayList<>();

        for( Integer i : Subtasks.keySet()){
            qwe.add(Subtasks.get(i));
        }

        return qwe;
    }

    public void delAllTasks(){
        Tasks.clear();
        Subtasks.clear();
        Epics.clear();
    }

    public int findEpicIdByName(String name) {

        for(Integer i : Epics.keySet()){
            if (Epics.get(i).getName().equals(name)){
                return i;
            } else {
                System.out.println("Ошибка поиска эпика по name - не найдена запись !!!");
                return -1;
            }
        }
        return -1;
    }

    public void addSubtask(int epicId, String name, String description) {
        Subtask st =  new Subtask(taskCount, epicId,name,description);
        Subtasks.put(taskCount, st);
        taskCount++;
    }

    public void deleteTask(int d) {
        for(Integer i : Tasks.keySet()){
            if (i == d) {
                Tasks.remove(d);
                return;
            }
        }

        for(Integer i : Epics.keySet()){
            if (i == d) {
                Epics.remove(d);
                return;
            }
        }

        for(Integer i : Subtasks.keySet()){
            if (i == d) {
                Subtasks.remove(d);
                return;
            }
        }
        System.out.println("Id: " + d + " не найден !!!");
    }

    public Task getTask(int id){
        return Tasks.get(id);
    }

    public Subtask getSubtask(int id){
        return Subtasks.get(id);
    }

    public Epic getEpic(int id){
        return Epics.get(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(int id){
        return Epics.get(id).getSubtasksArr(Subtasks);
    }
}
