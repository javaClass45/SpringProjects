package my.springREST.pro.controller;

import my.springREST.pro.dto.AuthenticationDTO;
import my.springREST.pro.dto.PersonDTO;
import my.springREST.pro.model.Person;
import my.springREST.pro.security.JWTUtil;
import my.springREST.pro.services.RegistrationService;
import my.springREST.pro.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/auth") //у всех методов в этом контроллере в адресе сначала будет идти /auth
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


// страница для создания нового ользователя
    //@PostMapping("/regPage")
    @RequestMapping(value = "/regPage", method = RequestMethod.POST)
    public Map<String, String> performRegistration(@RequestBody @Valid Person person,
                                                   BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return Map.of("message", "ашипка");
        registrationService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("token", token);
    }

    //метод для аутентификации
    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "ашипка BadCredentials");
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("token", token);
    }



    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }


}
