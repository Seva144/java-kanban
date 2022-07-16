package model;
import java.util.HashMap;
import java.util.Map;

public class MapTask<T extends Task> {

    public final Map<Integer, T> tasks = new HashMap<>();

    public void addTask(int id,T task) {
        tasks.put(id,task);
    }

    public Map<Integer, T> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "MapTask{" +
                "tasks=" + tasks +
                '}';
    }

}
