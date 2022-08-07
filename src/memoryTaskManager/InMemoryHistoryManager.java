package memoryTaskManager;

import interfaces.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public static List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory(){
        return history;
    }

    @Override
    public void add(Task task){
    }

}
