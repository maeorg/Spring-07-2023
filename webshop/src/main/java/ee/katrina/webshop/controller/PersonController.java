package ee.katrina.webshop.controller;

import ee.katrina.webshop.entity.Person;
import ee.katrina.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PersonController {

    // KODUS: Kõikide võtmine, Lisamine, Kustutamine, Ühe võtmine, Muutmine
    //                          korraga tuleb lisada ContactData ja siis Address

    @Autowired
    PersonRepository personRepository;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @PostMapping("persons")
    public List<Person> addPerson(@RequestBody Person person) {
        personRepository.save(person);
        return personRepository.findAll();
    }

    @DeleteMapping("persons/{id}")
    public List<Person> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return personRepository.findAll();
    }

    @GetMapping("persons/{id}")
    public Person getPerson(@PathVariable Long id) throws NoSuchElementException {
        if (!personRepository.existsById(id)) {
            throw new NoSuchElementException("Person not found");
        }
        return personRepository.findById(id).get();
    }

    @PutMapping("persons/{id}")
    public List<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        if (personRepository.existsById(id)) {
            person.setId(personRepository.findById(id).get().getId());
            personRepository.save(person);
        }
        return personRepository.findAll();
    }
}
