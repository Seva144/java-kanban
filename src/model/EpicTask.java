package model;

import java.util.ArrayList;

public class EpicTask extends Task {

    private final ArrayList<Integer> subTaskId = new ArrayList<>();

    public EpicTask(String name, String description, int id, StatusTask status) {
        super(name, description, id, status);
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int id){
        subTaskId.add(id);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() + '\'' +
                ", subTaskId=" + subTaskId +
                '}';
    }
}
