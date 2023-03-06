package my.springREST.pro.repositories;

import my.springREST.pro.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByTitleStartingWith(String title); //ищем по начальным буквам названия Задачи

    List<Event> findByDeadlineDate(Date deadlineDate);




}
