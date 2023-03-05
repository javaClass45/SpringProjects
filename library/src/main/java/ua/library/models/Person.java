package ua.library.models;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "имя не может быть пустым")
    @Size(min = 2, max = 100, message = "имя долно быть от 2 до 100 символов длинной")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "год рождения может быть больше 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person() {
    }

    public Person(@NotEmpty(message = "имя не может быть пустым")
                  @Size(min = 2, max = 100, message = "имя долно быть от 2 до 100 символов длинной")
                          String fullName, @Min(value = 1900, message = "год рождения может быть больше 1900")
            int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
