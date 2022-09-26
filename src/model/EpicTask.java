package model;

import java.util.ArrayList;

public class EpicTask extends Task {

    private final ArrayList<Integer> subTaskId = new ArrayList<>();

    private int id;

    public EpicTask(String name, String description, StatusTask status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int id){
        subTaskId.add(id);
    }

    @Override
    public String toString() {
        return "EPICTASK{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() + '\'' +
                ", subTaskId=" + subTaskId +
                '}';
    }
}

