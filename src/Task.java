public class Task {

    int id;
    String name;
    String description;
    String status;

    static final String tStatNew = "NEW";
    static final String tStatInPr = "IN_PROGRESS";
    static final String tStatDone = "DONE";

    Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = tStatNew;
    }

    Task(int id, String name, String description, String status) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }
}
