package my.springREST.pro.services;
import my.springREST.pro.model.Person;
import my.springREST.pro.security.PersonDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//блокировку методов делаем через сервисы
@Service
// мотоды доступные только Админу
@PreAuthorize("hasRole('ROLE_ADMIN')") // or hasRole('ROLE_SOME')") в этот метод пустит только определенного пользователя
public class AdminService {
    public void doAdminStuff() {
        System.out.println("Only Admin here!");
    }



    public Person getPD() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        return personDetails.getPerson();
    }

}