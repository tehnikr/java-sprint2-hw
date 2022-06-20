package tasks;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new HashMap<>();
    }

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW_TASK);
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

    public Subtask getSubtask(Integer id) {
        return subtasks.get(id);
    }


    @Override
    public String toString() {
        return this.getId() + "," + TaskType.EPIC + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription();
    }

    @Override
    public void setStatus(TaskStatus status) {
        // Переопределил метод чтобы он ничего не делал
    }

    public void setSubtaskStatus(Integer id, TaskStatus status) {
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
                //", name='" + this.getName() + '\'' +
                //", description='" + this.getDescription() + '\'' +
                //", status='" + this.getStatus() + '\'' +
                '}');

        for (Integer i : subtasks.keySet()) {
            System.out.println("    " + subtasks.get(i));
        }
    }

    public void examinationEpicStatus() {
        TaskStatus tmpStatus = Task.TaskStatus.DONE_TASK;

        for (Integer ik : subtasks.keySet()) {
            if (subtasks.get(ik).getStatus().equals(Task.TaskStatus.IN_PROGRESS_TASK)) {
                tmpStatus = TaskStatus.IN_PROGRESS_TASK;
            }


        }

        boolean isNew = true;

        for (Integer ik : subtasks.keySet()) {
            if (!subtasks.get(ik).getStatus().equals(TaskStatus.NEW_TASK)) isNew = false;
        }

        if (isNew) {
            tmpStatus = TaskStatus.NEW_TASK;
        }

        boolean isDone = true;
        for (Integer ik : subtasks.keySet()) {
            if (!subtasks.get(ik).getStatus().equals(TaskStatus.DONE_TASK)) isDone = false;
        }
        if (isDone) tmpStatus = TaskStatus.DONE_TASK;


        if (subtasks.isEmpty()) tmpStatus = TaskStatus.NEW_TASK;

        isNew = false;
        isDone = false;

        for (Integer ik : subtasks.keySet()) {
            if (subtasks.get(ik).getStatus().equals(TaskStatus.DONE_TASK)) isDone = true;
            if (subtasks.get(ik).getStatus().equals(TaskStatus.NEW_TASK)) isNew = true;
        }

        if (isDone && isNew) tmpStatus = TaskStatus.IN_PROGRESS_TASK;

        status = tmpStatus;
    }

    public LocalTime getEndTime(){

        LocalTime timeExecuteAllSubtasks = startTime;

        for (int i : subtasks.keySet()) {
            timeExecuteAllSubtasks = timeExecuteAllSubtasks.plus(subtasks.get(i).getDuration());
        }

        return timeExecuteAllSubtasks;
    }
}
