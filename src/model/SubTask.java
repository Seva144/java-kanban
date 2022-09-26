package model;

public class SubTask extends Task{

    private int id;

    public SubTask(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    @Override
    public String toString() {
        return "SUBTASK{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }

}





