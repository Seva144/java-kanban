package model;


public class EpicTask extends Task {

    public EpicTask(String name, String description, int id, StatusTask status) {
        super(name, description, id, status);
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
