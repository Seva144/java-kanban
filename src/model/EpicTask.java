package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static taskManager.Managers.getDefault;

public class EpicTask extends Task {

    private final ArrayList<Integer> subTaskId = new ArrayList<>();

    private final int id = 0;
    private final Duration duration = null;
    private final LocalDateTime startTime = null;
    private final LocalDateTime endTime = null;

    public EpicTask(String name, String description, StatusTask status) {
        super(name, description, status);
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
        if(!getSubTaskId().isEmpty()) {
            LocalDateTime startTime = subTaskMap.get(getSubTaskId().get(0)).getStartTime();
            for (Integer idSub : getSubTaskId()) {
                LocalDateTime newTime = subTaskMap.get(idSub).getStartTime();
                if (startTime.isAfter(newTime)) startTime = newTime;
            }
            return startTime;
        } else {
            return null;
        }
    }

    @Override
    public Duration getDuration() {
        long min = 0;
        if (!getSubTaskId().isEmpty()) {
            for (Integer idSub : subTaskId) {
                min += subTaskMap.get(idSub).getDuration().toMinutes();
            }
            return Duration.ofMinutes(min);
        } else{
            return null;
        }
    }

    @Override
    public LocalDateTime getEndTime(){
        if(getStartTime()!=null){
            return getStartTime().plus(getDuration());
        } else{
            return null;
        }
    }

    @Override
    public String getStrStartTime(){
        if(getStartTime()!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy: HH:mm");
            return getStartTime().format(formatter);
        } else{
            return null;
        }
    }

    @Override
    public String getStrDuration(){
        if(getDuration()!=null) {
            LocalTime t = LocalTime.MIDNIGHT.plus(getDuration());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return t.format(formatter);
        } else {
            return null;
        }
    }

    @Override
    public String getStrEndTime(){
        if(getEndTime()!=null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy: HH:mm");
            return getEndTime().format(formatter);
        } else{
            return null;
        }
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



