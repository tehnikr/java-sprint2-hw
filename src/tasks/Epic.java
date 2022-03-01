package tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new HashMap<>();
    }

    public ArrayList<Subtask> getSubtaskList() {
        ArrayList<Subtask> epicsList = new ArrayList<>();
        for (int i : subtasks.keySet()) {
            epicsList.add(subtasks.get(i));
        }

        return epicsList;
    }

    public void addSubtask(int id, Subtask subtask) {
        subtasks.put(id, subtask);
        examinationEpicStatus();
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                " id=" + id +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                '}';
    }

    @Override
    public void setStatus(String status) {
        // Переопределил метод чтобы он ничего не делал
    }

    public void setSubtaskStatus(Integer id, String status) {
        for (Integer i : subtasks.keySet()) {
            if (subtasks.get(i).getId() == id) {
                subtasks.get(i).setStatus(status);
            }
        }
        examinationEpicStatus();
    }

    public void deleteSubtask(int d) {
        subtasks.remove(d);
        examinationEpicStatus();
    }

    public void showEpicInfo() {
        System.out.println("tasks.Epic{" +
                " id=" + id +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                '}');

        for (Integer i : subtasks.keySet()) {
            System.out.println("    " + subtasks.get(i));
        }
    }

    private void examinationEpicStatus() {
        String tmpStatus = Task.tStatDone;

        for (Integer ik : subtasks.keySet()) {
            if (subtasks.get(ik).getStatus().equals(Task.tStatInPr)) {
                tmpStatus = Task.tStatInPr;
            }
        }

        boolean isNew = true;

        for (Integer ik : subtasks.keySet()) {
            if (!subtasks.get(ik).getStatus().equals(Task.tStatNew)) isNew = false;
        }

        if (isNew) {
            tmpStatus = Task.tStatNew;
        }

        boolean isDone = true;
        for (Integer ik : subtasks.keySet()) {
            if (!subtasks.get(ik).getStatus().equals(Task.tStatDone)) isDone = false;
        }
        if (isDone) tmpStatus = Task.tStatDone;


        if (subtasks.isEmpty()) tmpStatus = Task.tStatNew;

        status = tmpStatus;
    }
}
