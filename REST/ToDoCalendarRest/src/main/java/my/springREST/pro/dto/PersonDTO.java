package my.springREST.pro.dto;


//Data Transfer Object только те поля что мы передаем коиентам
// у DTO нет связи с БД
// используется на уровне контроллера

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {


    @NotEmpty(message = "имя не может быть пустым")
    @Size(min = 2, max = 100, message = "имя долно быть от 2 до 100 символов длинной")
    private String username;


    @NotEmpty(message = "email не может быть пустым")
    @Email(message = "email should be valid")
    private String email;


    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
