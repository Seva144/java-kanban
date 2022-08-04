package model;

public class SubTask extends Task{

    public SubTask(String name, String description, int id, StatusTask status) {
        super(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }

}


