package my.springREST.pro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;


    @NotEmpty(message = "email не может быть пустым")
    @Email(message = "email should be valid")
    @Column(name = "email")
    private String email;

    //@NotEmpty(message = "role не может быть пустым") //  role-по умоляанию задается ROLE_USER
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "owner") //cascade = CascadeType.ALL, с person'ом умрут и его задачи
    @JsonManagedReference
    private List<Event> events; //   это join переменная которая реализует ManyToOne-OneToMany




    public Person() {
    }

    public Person(String username, String email /*, String role*/) {
        this.username = username;
        this.email = email;
        //this.role = role; // role-по умоляанию задается ROLE_USER
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status='" + role + '\'' +
                ", events=" + events +
                '}';
    }
}
