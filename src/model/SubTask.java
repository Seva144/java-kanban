package model;

import static service.Manager.generateId;

public class SubTask extends Task{
    public int id;
    public int idEpic;

    public SubTask(String name, String description, StatusTask status, int idEpic) {
        super(name, description, status);
        id = generateId();
        this.idEpic=idEpic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "idEpic=" + idEpic +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}


