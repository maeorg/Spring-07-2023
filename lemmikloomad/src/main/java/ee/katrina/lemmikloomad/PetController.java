package ee.katrina.lemmikloomad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController { // controller võtab forntendi päringuid vastu

    @Autowired // otseühendus selel failiga ehk kui tekitatakse see praegune fail, siis tekitatakse koheselt ka autowire-datud fail siia külge. Dependency Injection
    PetRepository petRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @GetMapping("pet/add") // localhost:8080/pet/add?name=Koer&weight=2000
    public List<Pet> addPet(
            @RequestParam String name,
            @RequestParam double weight
    ) {
        Pet pet = new Pet(name, weight);
        petRepository.save(pet);
        return petRepository.findAll();
    }

    @GetMapping("owner/add") // localhost:8080/owner/add?name=Ott
    public List<Owner> addOwner(
            @RequestParam String name
    ) {
        Owner owner = new Owner();
        owner.setName(name);
        ownerRepository.save(owner);
        return ownerRepository.findAll();
    }

    @GetMapping("owner/add-pet") // localhost:8080/owner/add-pet?owner=Ott&pet=Koer
    public Owner addPetToOwner(
            @RequestParam String owner,
            @RequestParam String pet
    ) {
        Owner found = ownerRepository.findById(owner).get();
        Pet foundPet = petRepository.findById(pet).get();
        List<Pet> pets = found.getPets();
        pets.add(foundPet);
        found.setPets(pets);
        return ownerRepository.save(found);
    }
}
