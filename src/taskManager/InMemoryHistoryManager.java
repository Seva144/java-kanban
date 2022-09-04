package taskManager;

import interfaces.HistoryManager;
import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    static class CustomLinkedList{

        private static final List<Node> linkedList = new ArrayList<>();
        private static final HashMap<Integer, Node> searchNode = new HashMap<>();

        private static Node head;


        public static void linkList(Node node) {
            if (head == null) {
                head = node;
            } else {
                head.next=node;
            }
            node.prev = head;
            getTask(node);
        }

        public static void getTask(Node node) {
            linkedList.add(node);
        }

        public static void removeNode(Node node1) {
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
                linkedList.remove(node);
            }
        }
    }

    List<Task> history = new ArrayList<>();

    @Override
    public void remove(int id) {
        CustomLinkedList.removeNode(CustomLinkedList.searchNode.get(id));
        CustomLinkedList.searchNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        for (Node linkList : CustomLinkedList.linkedList) {
            history.add(linkList.task);
        }
        return history;
    }

    @Override
    public void add(Task task) {
        Node node = new Node(task);
        CustomLinkedList.removeNode(node);
        CustomLinkedList.linkList(node);
        CustomLinkedList.searchNode.put(task.getId(), node);
    }
}



