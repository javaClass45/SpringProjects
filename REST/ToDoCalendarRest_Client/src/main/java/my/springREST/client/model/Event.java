package my.springREST.client.model;

import java.util.Date;

public class Event {
    private String title;
    private String description;
    private String deadlineDate;
    private String deadlineTime;
    private String completed;
    public Event() {
    }

    public Event(String title, String description, String deadlineDate, String deadlineTime, String completed) {
        this.title = title;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
