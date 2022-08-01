package model;

import static service.Manager.generateId;

public class EpicTask extends Task {
    public int id;

    public EpicTask(String name, String description, StatusTask status) {
        super(name, description, status);
        id=generateId();
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
