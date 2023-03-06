package my.springREST.pro.services;

import my.springREST.pro.repositories.EventRepository;
import my.springREST.pro.model.Event;
import my.springREST.pro.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/*В сервисе будут реализованы CRUD операции над Задачами */
/*Это специальный тип классов, в котором реализуется некоторая бизнес логика
 приложения. Впоследствии, благодаря этой аннотации Spring будет предоставлять
  нам экземпляр данного класса в местах, где это, нужно с помощью Dependency
  Injection. */

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;


    @Autowired
    public EventService(EventRepository eventRepository ) {
        this.eventRepository = eventRepository;

    }


    // сортировка по дате Задач. пойдет в EventController @RequestParam(value = "sort_by_date"...
       public List<Event> findAll(boolean sortByDate) {
        if (sortByDate) return eventRepository.findAll(Sort.by("date"));
        else return eventRepository.findAll();
    }

    public Event findOne(int id) {
        Optional<Event> foundEvent = eventRepository.findById(id);
        return foundEvent.orElse(null);
    }

    public List<Event> searchByTitle(String query) {
        return eventRepository.findByTitleStartingWith(query);
    }

    public List<Event> searchByDeadlineDate(Date deadlineDate) {
        return eventRepository.findByDeadlineDate(deadlineDate);
    }

    //return null, if has no owner
    public Person getEventOwner(int id) {
        return eventRepository.findById(id).map(Event::getOwner).orElse(null);
    }

    @Transactional
    public void save(Event event) {
        //System.out.println("getOwner ->>>>>>>>>>>>>>>" + getEventOwner(event.getId()));

        eventRepository.save(event);
    }


    @Transactional
    public void update(int id, Event updatedEvent) {
        Event eventToBeUpdated = eventRepository.findById(id).get();
        updatedEvent.setId(id);
        updatedEvent.setOwner(eventToBeUpdated.getOwner());//чтобы не терялась связь при обновлении
        eventRepository.save(updatedEvent);
    }

    @Transactional
    public void delete(int id) {
        eventRepository.deleteById(id);
    }



    @Transactional
    public void release(int id) {
        eventRepository.findById(id).ifPresent(
                event -> event.setOwner(null));
    }


    @Transactional
    public void assign(int id, Person selectedPerson) {
        eventRepository.findById(id).ifPresent(
                event -> {
                    event.setOwner(selectedPerson);
                }
        );
    }


    public boolean isEventExist(int id) {
        return eventRepository.existsById(id);
    }

}
