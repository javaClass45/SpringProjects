package my.springREST.pro.controller;

import my.springREST.pro.dto.EventDTO;
import my.springREST.pro.model.Event;
import my.springREST.pro.model.Person;
import my.springREST.pro.services.AdminService;
import my.springREST.pro.services.EventService;
import my.springREST.pro.services.PersonService;
import my.springREST.pro.services.UserService;
import my.springREST.pro.util.EventNotCreatedException;
import my.springREST.pro.util.EventNotFoundException;
import my.springREST.pro.util.Search;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/event")
public class EventController {

    private final AdminService adminService;
    private final EventService eventService;
    private final PersonService personService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public EventController(AdminService adminService, EventService eventService, PersonService personService,
                           UserService userService, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.eventService = eventService;
        this.personService = personService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<EventDTO> index() {

        return eventService.findAll(false)
                .stream()
                .map(this::convertToEventDTO)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EventDTO show(@PathVariable("id") int id) {

        return convertToEventDTO(eventService.findOne(id));
    }




    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody @Valid Event event, BindingResult bindingResult)
            throws EventNotCreatedException {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage() == null ?
                        error.getCode() : error.getDefaultMessage())
                        .append(";");
            }
            throw new EventNotCreatedException(errorMsg.toString());
        }

        event.setOwner(personService.getPD());
        eventService.save(event);
        return new ResponseEntity<>("Event is created successful", HttpStatus.OK);

    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public ResponseEntity<List<Event>> create(@RequestBody @Valid Date date, BindingResult bindingResult)
            throws EventNotCreatedException {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage() == null ?
                        error.getCode() : error.getDefaultMessage())
                        .append(";");
            }
            throw new EventNotCreatedException(errorMsg.toString());
        }

        return new ResponseEntity<>(eventService.searchByDeadlineDate(date),HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody @Valid Event event,
                                         BindingResult bindingResult,
                                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return
                new ResponseEntity<>("Event is not updated", HttpStatus.BAD_REQUEST);
        boolean isEventExist = eventService.isEventExist(id);
        if (isEventExist) {
            eventService.update(id, event);
            return new ResponseEntity<>("Event is updated successful", HttpStatus.OK);
        } else {
            throw new EventNotFoundException("Event not found");
        }

    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {

        eventService.delete(id);
        return new ResponseEntity<>("Event is deleted successful", HttpStatus.OK);
    }

    // присвоить полю Owner null
    @RequestMapping(value = "/{id}/release", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> release(@PathVariable("id") int id) {
        eventService.release(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // присвоить полю Owner id аутентифицированного пользователя
    @RequestMapping(value = "/{id}/assign", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> assign(@PathVariable("id") int id, @RequestBody @Valid Person selectedPerson) {

        eventService.assign(id, selectedPerson);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    //ищем по начальным буквам названия Задачи
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> makeSearch(@RequestBody Search search) {

        return new ResponseEntity<>(eventService.searchByTitle(search.getSearch()), HttpStatus.OK);
    }


    private Event convertToEvent(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }

    private EventDTO convertToEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }


}
