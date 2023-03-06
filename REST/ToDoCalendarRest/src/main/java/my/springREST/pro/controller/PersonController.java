package my.springREST.pro.controller;

import my.springREST.pro.dto.PersonDTO;
import my.springREST.pro.model.Event;
import my.springREST.pro.model.Person;
import my.springREST.pro.services.AdminService;
import my.springREST.pro.services.*;
import my.springREST.pro.services.RegistrationService;
import my.springREST.pro.util.PersonErrorResponse;
import my.springREST.pro.util.PersonNotCreatedException;
import my.springREST.pro.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
/*— говорит спрингу, что данный класс является REST контроллером. Т.е. в
 данном классе будет реализована логика обработки клиентских запросов. И реализованы
 методы возвращающие представления в виде JSON*/
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final RegistrationService registrationService;
    private final AdminService adminService;// организуем доступ на страницу Админу
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, RegistrationService registrationService,
                            AdminService adminService, ModelMapper modelMapper) {
        this.personService = personService;
        this.registrationService = registrationService;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }


    //@GetMapping
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<PersonDTO> getPeople() {
     return  personService.findAll().stream()
             .map(this::convertToPersonDTO)
             .collect(Collectors.toList());
    }


    //@GetMapping("/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonDTO> getPerson(@PathVariable("id") int id) {
       return new ResponseEntity<> (convertToPersonDTO(personService.findOne(id)), HttpStatus.OK);
    }

    //@GetMapping("/{id}/event")
    @RequestMapping(value = "/{id}/event", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getPersonEvent(@PathVariable("id") int id) {
        return new ResponseEntity<>(personService.getEventByPersonId(id), HttpStatus.OK);
    }



    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity<PersonDTO> update(@PathVariable("id") int id,
                                             @RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult) throws PersonNotCreatedException {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage() == null ?
                        error.getCode() : error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        personService.update(id,convertToPerson(personDTO));
        // отправляем HTTP ответ с пустым телом и статусом 200
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis()
        );
        // В HTTP ответе тело (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //404
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),System.currentTimeMillis());
        // В HTTP ответе тело (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //404
    }



    //@DeleteMapping("/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        registrationService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

//   с REST контроллером   Роли, имена, имейлы будут меняться
//  в PUT запросе "http://localhost:8080/person/2/edit"




    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
