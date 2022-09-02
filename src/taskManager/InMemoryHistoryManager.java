package taskManager;

import interfaces.HistoryManager;
import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager<T> implements HistoryManager {

    List<Task> history = new ArrayList<>();
    private static final HashMap<Integer, Node> searchNode = new HashMap<>();

    //реализация двусвязного списка
    private final static List<Node> customLinkedList = new ArrayList<>();
    private Node head;
    private Node tail;

    public void linkList(Node node) {
        if (head == null) {
            head = node;
        } else {
            head.next=node;
        }
        node.prev = head;
        tail = node;
        getTask(node);
    }

    public void getTask(Node node) {
        customLinkedList.add(node);
    }

    public void removeNode(Node node1) {
        if (searchNode.containsKey(node1.task.getId())) {
            Node node = searchNode.get(node1.task.getId());
            if (node.prev != null && node.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            } else if (node.prev != null) {
                head = null;
            }
            node.prev = null;
            node.next = null;
            customLinkedList.remove(node);
        }
    }
    //********************************

    @Override
    public void remove(int id) {
        removeNode(searchNode.get(id));
        searchNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        for (Node linkList : customLinkedList) {
            history.add(linkList.task);
        }
        return history;
    }


    @Override
    public void add(Task task) {
        Node node = new Node(task);
        removeNode(node);
        linkList(node);
        searchNode.put(task.getId(), node);
    }
}



