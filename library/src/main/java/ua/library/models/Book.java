package ua.library.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "название книги не может быть пустым")
    @Size(min = 2, max = 100, message = "название должно быть от 2-х до 100 символов длинной")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "автор не должен быть пустым")
    @Size(min = 2, max = 100, message = "имя автора должнобыть от 2 до 100 символов")
    @Column(name = "author")
    private String author;

    @Min(value = 1500, message = "год должен быть от 1500го")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient //Hibernate не должен замечать это поле
    private boolean expired;

    public Book() {
    }

    public Book(@NotEmpty(message = "название книги не может быть пустым")
                @Size(min = 2, max = 100, message = "название должно быть от 2-х до 100 символов длинной")
                        String title, @NotEmpty(message = "автор не должен быть пустым")
                @Size(min = 2, max = 100, message = "имя автора должнобыть от 2 до 100 символов")
                        String author, @Min(value = 1500, message = "год должен быть от 1500го")
                        int year) {
        this.title = title;
        this.author = author;
        this.year = year;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
