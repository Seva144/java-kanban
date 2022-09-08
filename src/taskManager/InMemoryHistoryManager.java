package taskManager;

import interfaces.HistoryManager;
import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public static class CustomLinkedList {

        private static Node<Task> head;
        private static Node<Task> tail;

        public void linkList(Node<Task> node) {
            if (head == null) {
                head = node;
            } else {
                tail.setNext(node);
            }
            node.setPrev(tail);
            tail = node;
        }

        public Node<Task> head() {
            return head;
        }

        public void removeNode(Node<Task> node) {
            if (node.getPrev()!= null) {
                //Удаляемый элемент - не первый
                node.getPrev().setNext(node.getNext());
                if (node.getNext() == null) {
                    //Удаляемый элемент последний
                    tail = node.getPrev();
                } else {
                    node.getNext().setPrev(node.getPrev());
                }
            } else {
                //Удаляемый элемент - первый
                head = node.getNext();
                if (head == null) {
                    //В списке был один элемент
                    tail = null;
                } else {
                    head.setPrev(null);
                }
            }
        }
    }

    private static final HashMap<Integer, Node<Task>> searchNode = new HashMap<>();

    List<Task> history = new ArrayList<>();

    CustomLinkedList linkedList = new CustomLinkedList();

    @Override
    public void remove(int id) {
        linkedList.removeNode(searchNode.get(id));
        searchNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        Node<Task> currentNode = linkedList.head();
        ArrayList<Node<Task>> customList = new ArrayList<>();
        while (currentNode != null) {
            customList.add(currentNode);
            currentNode = currentNode.getNext();
        }
        for (Node<Task> node : customList) {
            history.add(node.task);
        }
        return history;
    }

    @Override
    public void add(Task task) {
        Node<Task> node = new Node<>(task);
        if (searchNode.containsKey(node.task.getId())) {
            Node<Task> delNode = searchNode.get(node.task.getId());
            linkedList.removeNode(delNode);
        }
        linkedList.linkList(node);
        searchNode.put(task.getId(), node);
    }
}




