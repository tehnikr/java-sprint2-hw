package tasks;

public class Subtask extends Task {

    int epicId;

    public Subtask(int id, int epicId, String name, String description) {
        super(id, name, description);
        this.epicId = epicId;
    }

    public Subtask(int id, int epicId, String name, String description, TaskStatus status) {
        super(id, name, description);
        this.epicId = epicId;
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return this.getId() + "," + TaskType.SUBTASK + "," + this.getName() + "," + this.getStatus() + "," + this.getDescription() + "," + this.getEpicId();
    }
}
