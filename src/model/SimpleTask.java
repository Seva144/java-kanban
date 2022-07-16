package model;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, int id, StatusTask status) {
        super(name, description, id, status);
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}