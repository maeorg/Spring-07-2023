package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.security.LoginData;
import ee.katrina.webshop.dto.security.Token;
import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.repository.PersonRepository;
import ee.katrina.webshop.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody LoginData loginData) {
        // login
        Person person = personRepository.findByPersonalCode(loginData.getPersonalCode());
        return new ResponseEntity<>(tokenGenerator.getToken(person), HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<Token> signup(@RequestBody Person person) {
        // Signup
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(tokenGenerator.getToken(savedPerson), HttpStatus.OK);
    }
}
