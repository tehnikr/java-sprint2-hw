package mgr;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private int size = 0;
    private Integer first = null;
    private Integer last = null;

    private HashMap<Integer, Node> historyLL = new HashMap<>();

    public static class Node<E> {
        public final E value;
        public Integer next;
        public Integer previous;

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = null;
        }

        public E getValue() {
            return value;
        }
    }

    @Override
    public void add(Task task) {

        Node<Task> newNode = new Node<>(task, null);

        if (size == 0) {
            size++;
            first = task.getId();
            last = task.getId();
            newNode.previous = null;
            historyLL.put(task.getId(), newNode);
            return;
        }

        if (historyLL.containsKey(task.getId())) {
            Integer removeId = task.getId();
            if (historyLL.get(removeId).previous == null) {
                first = historyLL.get(removeId).next;
            } else {
                historyLL.get(historyLL.get(removeId).previous).next = historyLL.get(removeId).next;
            }
            historyLL.get(historyLL.get(removeId).next).previous = historyLL.get(removeId).previous;
            historyLL.remove(removeId);
            newNode.next = null;
            newNode.previous = last;
            historyLL.get(last).next = newNode.value.getId();
            last = newNode.value.getId();
            historyLL.put(newNode.value.getId(), newNode);
            size++;
            return;
        } else {
            linkLast(newNode);
        }

    }

    @Override
    public void remove(int removeId) {
        if (removeId < 0) {
            return;
        }

        if ((historyLL.get(removeId)).value.getClass().equals((new Epic(1, "aes", "aswef")).getClass())) {

            Epic g = (Epic) historyLL.get(removeId).value;
            System.out.println("Эпик: " + removeId);

            for (Subtask s : g.getSubtaskList()) {

                if (historyLL.containsKey(s.getId())) {


                    //historyLL.remove(s.getId());
                    removeNode(historyLL.get(s.getId()));

                    System.out.println("Субтаск: " + s.getName() + " удалён");
                }
            }
        }

        removeNode(historyLL.get(removeId));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Node<Task> newNode) {
        historyLL.get(last).next = newNode.value.getId();
        newNode.previous = last;
        last = newNode.value.getId();
        historyLL.put(newNode.value.getId(), newNode);
        size++;
    }

    private List<Task> getTasks() {
        ArrayList<Task> historyListToRet = new ArrayList<>();

        Node<Task> temp = historyLL.get(first);


        while (temp.next != null) {
            historyListToRet.add(temp.value);
            temp = historyLL.get(temp.next);
        }

        historyListToRet.add(temp.value);
        return historyListToRet;
    }

    private void removeNode(Node<Task> r) {
        int removeId = r.value.getId();
        if (historyLL.containsKey(removeId)) {
            if (historyLL.get(removeId).previous == null) {
                first = historyLL.get(removeId).next;
            } else {

                Integer g = historyLL.get(removeId).next;
                historyLL.get(historyLL.get(removeId).previous).next = g;
            }

            if (last == removeId) {
                last = historyLL.get(last).previous;
                historyLL.get(last).previous = null;
            } else {
                historyLL.get(historyLL.get(removeId).next).previous = historyLL.get(removeId).previous;
            }
            historyLL.remove(removeId);
            size--;
            return;
        }
    }
}
