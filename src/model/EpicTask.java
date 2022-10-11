package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static taskManager.Managers.getDefault;

public class EpicTask extends Task {

    private final ArrayList<Integer> subTaskId = new ArrayList<>();

    private int id = 0;

    public EpicTask(String name, String description, StatusTask status) {
        super(name, description, status);
        Duration duration = getDuration();
        LocalDateTime startTime = getStartTime();
        LocalDateTime endTime = getEndTime();
    }

    HashMap<Integer, SubTask> subTaskMap = getDefault().getAllSubTasksMap();

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int id){
        subTaskId.add(id);
    }

    public void removeAllSubTaskId(){
        subTaskId.clear();
    }

    @Override
    public LocalDateTime getStartTime(){
        LocalDateTime startTime = LocalDateTime.of(0,1,1,0,0);
        if(getSubTaskId()!=null) {
            for (Integer idSub : getSubTaskId()) {
                LocalDateTime newTime = subTaskMap.get(idSub).getStartTime();
                if (startTime.isBefore(newTime)) startTime = newTime;
            }
        }
        return startTime;
    }

    @Override
    public Duration getDuration() {
        long min = 0;
        if (subTaskId!=null) {
            for (Integer idSub : subTaskId) {
                min += subTaskMap.get(idSub).getDuration().toMinutes();
            }
        }
        return Duration.ofMinutes(min);
    }

    @Override
    public LocalDateTime getEndTime(){
        return getStartTime().plus(getDuration());
    }

    @Override
    public String getStrStartTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy: HH:mm");
        return getStartTime().format(formatter);
    }

    @Override
    public String getStrDuration(){
        LocalTime t = LocalTime.MIDNIGHT.plus(getDuration());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return t.format(formatter);
    }

    @Override
    public String getStrEndTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy: HH:mm");
        return getEndTime().format(formatter);
    }

    @Override
    public String toString() {
        return "EPICTASK{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", subTaskId=" + getSubTaskId() +
                ", duration=" + getStrDuration() +
                ", startTime=" + getStrStartTime() +
                ", endTime=" + getStrEndTime() +
                '}';
    }
}



