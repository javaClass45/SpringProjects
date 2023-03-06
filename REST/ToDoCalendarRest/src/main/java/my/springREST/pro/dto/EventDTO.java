package my.springREST.pro.dto;

//import my.springREST.pro.util.JsonDateSerializer;
import my.springREST.pro.model.Person;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

public class EventDTO {



    @NotEmpty(message = "название книги не может быть пустым")
    @Size(min = 2, max = 100, message = "название должно быть от 2-х до 100 символов длинной")
    private String title;

    @NotEmpty(message = "описание задачи не должен быть пустым")
    @Size(min = 2, max = 255, message = "длинна задачи должна быть от 2 до 255 символов")
    private String description;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@JsonSerialize(using = JsonDateSerializer.class)
    private Date deadlineDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date deadlineTime;

    private boolean complited;



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

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public boolean isComplited() {
        return complited;
    }

    public void setComplited(boolean complited) {
        this.complited = complited;
    }


}
