package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id = 0;
    private final String name;
    private final String description;
    private StatusTask status;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Task(String name, String description, StatusTask status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration.toMinutes());
        this.startTime = startTime;
        this.endTime = getEndTime();
    }


    public Task(String name, String description, StatusTask status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public StatusTask getStatus() {
        return status;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plus(this.duration);
    }

    public String getStrEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy: HH:mm");
        return this.endTime.format(formatter);
    }

    public String getStrDuration() {
        LocalTime t = LocalTime.MIDNIGHT.plus(this.duration);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return t.format(formatter);
    }

    public String getStrStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy: HH:mm");
        return this.startTime.format(formatter);
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + getStrDuration() +
                ", startTime=" + getStrStartTime() +
                ", endTime=" + getStrEndTime() +
                '}';
    }
}
