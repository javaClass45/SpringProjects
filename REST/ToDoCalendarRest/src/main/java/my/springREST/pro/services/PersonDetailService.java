package my.springREST.pro.services;

import my.springREST.pro.model.Person;
import my.springREST.pro.repositories.PersonRepository;
import my.springREST.pro.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//предназначен именно для спринг сецурити имплементируем UDS чтобы спринг знал что мы здесь загружаем пользователя
@Service
@Transactional(readOnly = true)
public class PersonDetailService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    //Спринг при помощи PersonDetailServices посмотрит в БД данные пользователя
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found!!!");
        }
        return new PersonDetails(person.get());
    }



}
