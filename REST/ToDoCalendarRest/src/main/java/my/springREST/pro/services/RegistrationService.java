package my.springREST.pro.services;

import my.springREST.pro.model.Person;
import my.springREST.pro.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//сервис для регистрации нового поьзоваьтеля в БД
@Service
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
       // String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");// задаем роль по умолчанию,при создании новых пользователей
        personRepository.save(person);//сохраняем в БД нового пользователя
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }
}