package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task{

    private int id = 0;

    public SubTask(String name, String description, StatusTask status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        LocalDateTime endTime = getEndTime();
    }

    @Override
    public String toString() {
        return "SUBTASK{" +
                "id=" + getId() +
                ", name='" + getName()  + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", duration=" + getStrDuration() +
                ", startTime=" + getStrStartTime() +
                ", endTime=" + getStrEndTime() +
                '}';
    }

}




