package my.springREST.pro.controller;

import my.springREST.pro.security.PersonDetails;
import my.springREST.pro.services.AdminService;
import my.springREST.pro.services.PersonService;
import my.springREST.pro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



@RestController
@RequestMapping()
public class HelloController {

    private final AdminService adminService;
    private final PersonService personService;


    @Autowired
    public HelloController(AdminService adminService, PersonService personService) {
        this.adminService = adminService;
        this.personService = personService;
    }


    @GetMapping("/hello")
    public Map<String, String>  sayHello() {

        return Map.of("mesg", "hello");

    }

    @GetMapping("/index")
    public Map<String, String>  index() {
        return Map.of("mesg", "index");

    }


    //тестовая страничка
    @GetMapping("/testText")
    public Map<String, Integer> testText(Model model) {

        model.addAttribute(personService.getPD());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return Map.of(personDetails.getUsername(), personDetails.getPerson().getId());
    }


    //страничка на которую будет доступу АДМИНа
    @GetMapping("/admin")
    public Map<String, Integer>  adminPage(Model model) {
        model.addAttribute(adminService.getPD());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return Map.of(personDetails.getUsername(), personDetails.getPerson().getId());
    }


}
