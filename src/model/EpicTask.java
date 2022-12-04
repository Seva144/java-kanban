package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EpicTask extends Task {

    private final ArrayList<Integer> subTaskId = new ArrayList<>();

    public EpicTask(String name, String description, StatusTask status) {
        super(name, description, status);
    }


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
            return super.getStartTime();
        } else {
            return null;
        }
    }

    @Override
    public Duration getDuration() {
        if (!getSubTaskId().isEmpty()) {
            return super.getDuration();
        } else{
            return null;
        }
    }

    @Override
    public LocalDateTime getEndTime(){
        if(getStartTime()!=null){
            return super.getEndTime();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EpicTask)) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return Objects.equals(getSubTaskId(), epicTask.getSubTaskId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSubTaskId());
    }
}



