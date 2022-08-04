package model;

public class Task {

    private final String name;
    private final String description;
    private final int id;
    private StatusTask status;

    public Task(String name, String description, int id, StatusTask status) {
        this.name = name;
        this.description = description;
        this.id=id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}