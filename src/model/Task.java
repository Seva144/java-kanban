package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    //геттеры описывающие объект
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

    //геттеры времени
    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plus(this.duration);
    }

    //сеттеры для описания объекта
    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    //сеттеры времени
    public void setDuration(Duration duration) {
        this.duration = Duration.ofMinutes(duration.toMinutes());
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    //геттеры для toString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (getId() != task.getId()) return false;
        if (getName() != null ? !getName().equals(task.getName()) : task.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(task.getDescription()) : task.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
