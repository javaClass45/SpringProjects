package my.springREST.pro.services;

import my.springREST.pro.dto.PersonDTO;
import my.springREST.pro.model.Event;
import my.springREST.pro.model.Person;
import my.springREST.pro.repositories.PersonRepository;
import my.springREST.pro.security.PersonDetails;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")//нижеприведенными методами сможет пользоваться только ADMIN
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Event> getEventByPersonId(int id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getEvents());

            return person.get().getEvents();
        } else return Collections.emptyList();
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        Person person = personRepository.findById(id).get();
        person.setUsername(updatePerson.getUsername());
        person.setEmail(updatePerson.getEmail());
        person.setRole(updatePerson.getRole());

        personRepository.save(person);
    }

// на рестконтроллере методы утратили актуальность
//    @Transactional
//    public void setRoleAdmin(Person updatePerson) {
//        updatePerson.setRole("ROLE_ADMIN");
//        personRepository.save(updatePerson);
//    }
//
//    @Transactional
//    public void setRoleUser(Person updatePerson) {
//        updatePerson.setRole("ROLE_USER");
//        personRepository.save(updatePerson);
//    }


    public Person getPD() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        return personDetails.getPerson();
    }

}
