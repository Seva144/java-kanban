package model;

import static service.Manager.generateId;

public class Task {

    protected String name;
    protected String description;
    public int id;
    public StatusTask status;

    public Task(String name, String description, StatusTask status) {
        this.name = name;
        this.description = description;
        id = generateId();
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