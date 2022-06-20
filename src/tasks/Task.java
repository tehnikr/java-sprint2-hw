package tasks;

import java.time.Duration;
import java.time.LocalTime;

public class Task {

    int id;
    protected String name;
    protected String description;
    protected TaskStatus status;

    LocalTime startTime;
    protected Duration duration;



    public enum TaskStatus {
        NEW_TASK,
        IN_PROGRESS_TASK,
        DONE_TASK;
    }

    public enum TaskType {
        TASK,
        EPIC,
        SUBTASK;
    }

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW_TASK;
    }

    public Task(int id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return this.getId() + "," + TaskType.TASK + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription();
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public LocalTime getEndTime(){
        return startTime.plus(duration);
    }

    public Duration getDuration (){
        return duration;
    }


}
