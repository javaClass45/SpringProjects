package my.springREST.pro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;



@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "название книги не может быть пустым")
    @Size(min = 2, max = 100, message = "название должно быть от 2-х до 100 символов длинной")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "описание задачи не должен быть пустым")
    @Size(min = 2, max = 255, message = "длинна задачи должна быть от 2 до 255 символов")
    @Column(name = "description")
    private String description;

   @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonBackReference
    private Person owner; //   это join переменная которая реализует ManyToOne-OneToMany


    @Column(name = "deadline_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;

    @Column(name = "deadline_time")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date deadlineTime;


    //@NotEmpty(message = "тут надо определиться задача выполнена или нет") // с этой строкой в БД принципиально Хибернайт не добавляет жалуется на NotEmpty
    @Column(name = "complited")
    private boolean complited;

    public Event() {
    }


    public Event(String title, String description, Date deadlineDate, Date deadlineTime,Person owner, boolean complited) {
        this.title = title;
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.owner = owner;
        this.complited = complited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isComplited() {
        return complited;
    }

    public void setComplited(boolean complited) {
        this.complited = complited;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline_date=" + deadlineDate +
                ", deadline_time=" + deadlineTime +
                ", owner=" + owner +
                ", complited=" + complited +
                '}';
    }
}
