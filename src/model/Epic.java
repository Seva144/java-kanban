package model;

import java.util.HashMap;


public class Epic extends Task {
    private HashMap<Integer, SubTask> subTasks;


    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(HashMap<Integer, SubTask> subTasks) {
        this.subTasks = subTasks;
    }

}

