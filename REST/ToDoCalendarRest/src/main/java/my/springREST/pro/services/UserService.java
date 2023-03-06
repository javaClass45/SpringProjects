package my.springREST.pro.services;
import my.springREST.pro.model.Person;
import my.springREST.pro.security.PersonDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//блокировку методов делаем через сервисы
@Service
// мотоды доступные только User
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // or hasRole('ROLE_SOME')") в этот метод пустит только определенного пользователя
public class UserService {
    public void doUserStuff() {
        System.out.println("Only User&Admin here!");
    }

    //todo удалить из окончательного варианта
    public void userMetodOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
              System.out.println(personDetails.getPerson().getUsername());
    }



}