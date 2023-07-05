package ee.katrina.lemmikloomad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetController { // controller võtab forntendi päringuid vastu

    @Autowired // otseühendus selel failiga ehk kui tekitatakse see praegune fail, siis tekitatakse koheselt ka autowire-datud fail siia külge. Dependency Injection
    PetRepository petRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    ClinicRepository clinicRepository;

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

    // Võimalda küsida Lemmikloomade koguarvu ühe omaniku osas (sisendiks omanik ja väljundiks arv).
    @GetMapping("owner/numberOfPets") // localhost:8080/owner/numberOfPets?owner=Ott
    public int numberOfPets(@RequestParam String owner) {
        Owner found = ownerRepository.findById(owner).get();
        List<Pet> pets = found.getPets();
        return pets.size();
    }

    // Võimalda ühe omaniku kõige suurema kaaluga lemmiklooma leida ja kõige väiksema kaaluga.
    @GetMapping("owner/biggestSmallestPet") // localhost:8080/owner/biggestSmallestPet?owner=Ott
    public List<Pet> biggestSmallestPet(@RequestParam String owner) {
        Owner found = ownerRepository.findById(owner).get();
        List<Pet> pets = found.getPets();
        if (pets.size() == 0) return null;
        Pet smallest = new Pet("", Double.MAX_VALUE);
        Pet biggest = new Pet("", 0.0);
        for (Pet pet : pets) {
            if (pet.getWeight() <= smallest.getWeight()) smallest = pet;
            if (pet.getWeight() >= biggest.getWeight()) biggest = pet;
        }
        List<Pet> result = new ArrayList<>();
        result.add(biggest);
        result.add(smallest);
        return result;
    }

    // Võimalda sisestada minimaalne kaal ja maksimaalne kaal ning väljasta kõik lemmikloomad selles vahemikus.
    @GetMapping("pet/weightRange") // localhost:8080/pet/weightRange?minWeight=100&maxWeight=2000
    public List<Pet> weightRange(@RequestParam Double minWeight, @RequestParam Double maxWeight) {
        List<Pet> pets = petRepository.findAll();
        pets.removeIf(pet -> pet.getWeight() < minWeight || pet.getWeight() > maxWeight);
        return pets;
    }

    // Võimalda kliinikust otsida kindlat lemmiklooma tema nimetuse järgi.
    @GetMapping("clinic/searchPetFromClinic") // localhost:8080/clinic/searchPetFromClinic?clinicName=Kliinik&petName=Hiir
    public Pet searchPetFromClinic(@RequestParam String petName, @RequestParam String clinicName) {
        Clinic clinic = clinicRepository.findById(clinicName).get();
        List<Pet> pets = clinic.getPets();
        for (Pet pet : pets) {
            if (pet.getName().equals(petName)) return pet;
        }
        return null;
    }

    @GetMapping("clinic/addPet") // localhost:8080/clinic/addPet?clinic=Kliinik&pet=Hiir
    public Clinic addPetToClinic(
            @RequestParam String clinic,
            @RequestParam String pet
    ) {
        Clinic found = clinicRepository.findById(clinic).get();
        Pet foundPet = petRepository.findById(pet).get();
        List<Pet> pets = found.getPets();
        pets.add(foundPet);
        found.setPets(pets);
        return clinicRepository.save(found);
    }

    // Võimalda API otspunktist anda vaid üks kliinik - kellel on kõige rohkem lemmikloomi.
    @GetMapping("clinic/mostPets") // localhost:8080/clinic/mostPets
    public Clinic clinicWithMostPets() {
        List<Clinic> clinics = clinicRepository.findAll();
        int most = 0;
        Clinic mostPetsClinic = new Clinic();
        for (Clinic clinic : clinics) {
            if (clinic.getPets().size() >= most) {
                most = clinic.getPets().size();
                mostPetsClinic = clinic;
            }
        }
        return mostPetsClinic;
    }

}
