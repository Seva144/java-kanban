package model;

public class Node<T> {

    public T task;
    private Node<T> prev;
    private Node<T> next;

    public Node(T task) {
        this.task = task;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
