package my.springREST.pro.util;

import my.springREST.pro.model.Person;
import my.springREST.pro.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private final PersonDetailService personDetailServices;

    @Autowired
    public PersonValidator(PersonDetailService personDetailServices) {
        this.personDetailServices = personDetailServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailServices.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException e) {
            return; //костыль. все ок пользователь не найден
        }
        errors.rejectValue("username", "",
                "Такой человек уже есть");
    }


}