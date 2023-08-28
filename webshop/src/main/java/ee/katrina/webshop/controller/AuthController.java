package ee.katrina.webshop.controller;

import ee.katrina.webshop.dto.security.AuthToken;
import ee.katrina.webshop.dto.security.LoginData;
import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.repository.PersonRepository;
import ee.katrina.webshop.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping("login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginData loginData) throws RuntimeException {
        Person person = personRepository.findByPersonalCode(loginData.getPersonalCode());
        if (!encoder.matches(loginData.getPassword(), person.getPassword())) {
            throw new RuntimeException("Parool ei ole õige");
        }
        return new ResponseEntity<>(tokenGenerator.getToken(person), HttpStatus.OK);
    }

    @GetMapping("social")
    public ResponseEntity<String> socialLogin(OAuth2AuthenticationToken token) {
        System.out.println(token);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<AuthToken> signup(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(tokenGenerator.getToken(savedPerson), HttpStatus.OK);
    }
}
