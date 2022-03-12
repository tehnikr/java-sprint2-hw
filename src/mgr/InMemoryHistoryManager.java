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
        Node<Task> newTask = new Node<>(task, null);
        removeNode(newTask);
        linkLast(newTask);
    }

    @Override
    public void remove(int removeId) {
        if (removeId < 0) {
            return;
        }
        if (historyLL.containsKey(removeId)) {

            if ((historyLL.get(removeId)).value.getClass().equals((new Epic(1, "aes", "aswef")).getClass())) {

                Epic g = (Epic) historyLL.get(removeId).value;
                System.out.println("Эпик: " + removeId);

                for (Subtask s : g.getSubtaskList()) {

                    if (historyLL.containsKey(s.getId())) {
                        removeNode(historyLL.get(s.getId()));
                        System.out.println("Субтаск: " + s.getName() + " удалён");
                    }
                }
            }
            removeNode(historyLL.get(removeId));
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Node<Task> newNode) {
        if (size == 0) {
            first = newNode.value.getId();
            last = newNode.value.getId();
            size++;
            historyLL.put(newNode.value.getId(), newNode);
        } else {
            historyLL.get(last).next = newNode.value.getId();
            newNode.previous = last;
            last = newNode.value.getId();
            historyLL.put(newNode.value.getId(), newNode);
            size++;
        }
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

    private void removeNode(Node<Task> nodeToRemove) {
        if (nodeToRemove.value == null) return;
        int removeId = nodeToRemove.value.getId();
        if (historyLL.containsKey(removeId)) {

            if (removeId != last && removeId != first) {
                historyLL.get(historyLL.get(removeId).next).previous = historyLL.get(removeId).previous;
                historyLL.get(historyLL.get(removeId).previous).next = historyLL.get(removeId).next;
            }

            if (removeId == first) {
                first = historyLL.get(removeId).next;
                historyLL.get(historyLL.get(removeId).next).previous = null;
            }

            if (removeId == last) {
                last = historyLL.get(last).previous;
                historyLL.get(historyLL.get(removeId).previous).next = null;
            }

            historyLL.remove(removeId);
            size--;
        }
    }
}