public class Subtask extends Task{

    int epicId;

    Subtask(int id, int epicId, String name, String description){
        super(id, name, description);
        this.epicId = epicId;
    }

    Subtask(int id, int epicId, String name, String description, String status){
        super(id, name, description);
        this.epicId = epicId;
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
