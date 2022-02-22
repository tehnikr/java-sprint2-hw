import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{


    Epic(int id, String name, String description){

        super(id, name, description);
    }

    Epic(int id, String name, String description, String status){

        super(id, name, description, status);
    }

    public ArrayList<Subtask> getSubtasksArr(HashMap <Integer, Subtask> f) {
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for(Integer s:f.keySet()){
            if (f.get(s).getEpicId() == this.id){
                subtasks.add(f.get(s));
            }
        }

        return subtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
