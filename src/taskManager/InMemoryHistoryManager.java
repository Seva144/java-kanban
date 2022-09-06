package taskManager;

import interfaces.HistoryManager;
import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static class CustomLinkedList {

        private static Node head;
        private static Node tail;

        public static void linkList(Node node) {
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            node.prev = tail;
            tail = node;
        }

        public static ArrayList<Node> getTask() {
            Node currentNode = head;
            ArrayList<Node> linkedList = new ArrayList<>();
            while (currentNode != null) {
                linkedList.add(currentNode);
                currentNode = currentNode.next;
            }
            return linkedList;
        }
        
        public static void removeNode(Node node1) {
            if (searchNode.containsKey(node1.task.getId())) {
                Node node = searchNode.remove(node1.task.getId());
                if (node.prev != null) {
                    //Удаляемый элемент - не первый
                    node.prev.next = node.next;
                    if (node.next == null) {
                        //Удаляемый элемент последний
                        tail = node.prev;
                    } else {
                        node.next.prev = node.prev;
                    }
                } else {
                    //Удаляемый элемент - первый
                    head = node.next;
                    if (head == null) {
                        //В списке был один элемент
                        tail = null;
                    } else {
                        head.prev = null;
                    }
                }
            }
        }
    }

    private static final HashMap<Integer, Node> searchNode = new HashMap<>();

    List<Task> history = new ArrayList<>();

    @Override
    public void remove(int id) {
        CustomLinkedList.removeNode(searchNode.get(id));
        searchNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        for (Node linkList : CustomLinkedList.getTask()) {
            history.add(linkList.task);
        }
        return history;
    }

    @Override
    public void add(Task task) {
        Node node = new Node(task);
        CustomLinkedList.removeNode(node);
        CustomLinkedList.linkList(node);
        searchNode.put(task.getId(), node);
    }
}




