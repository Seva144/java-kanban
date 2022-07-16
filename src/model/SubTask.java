package model;

public class SubTask extends Task {
    public int idEpic;

    public SubTask(String name, String description, int id, StatusTask status, int idEpic) {
        super(name, description, id, status);
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

