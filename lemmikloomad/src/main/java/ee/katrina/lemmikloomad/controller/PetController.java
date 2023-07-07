package ee.katrina.lemmikloomad.controller;

import ee.katrina.lemmikloomad.dto.OwnerDTO;
import ee.katrina.lemmikloomad.entity.Clinic;
import ee.katrina.lemmikloomad.entity.Owner;
import ee.katrina.lemmikloomad.entity.Pet;
import ee.katrina.lemmikloomad.repository.ClinicRepository;
import ee.katrina.lemmikloomad.repository.OwnerRepository;
import ee.katrina.lemmikloomad.repository.PetRepository;
import ee.katrina.lemmikloomad.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// sout
// fori
// main
@RestController
public class PetController { // controller võtab forntendi päringuid vastu

    @Autowired // otseühendus selel failiga ehk kui tekitatakse see praegune fail, siis tekitatakse koheselt ka autowire-datud fail siia külge. Dependency Injection
    PetRepository petRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    OwnerService ownerService;

    @PostMapping ("pets") // localhost:8080/pets - POSTMANI kaudu POST päring parameetritega
    public List<Pet> addPet(
            @RequestParam String name,
            @RequestParam double weight
    ) {
        Pet pet = new Pet(name, weight);
        petRepository.save(pet);
        return petRepository.findAll();
    }

    @PostMapping ("owners") // localhost:8080/owners - POSTMANI kaudu POST päring parameetritega
    public List<Owner> addOwner(
            @RequestBody Owner owner
    ) throws Exception {
//        Owner owner = new Owner();
//        owner.setName(name);
        if (ownerRepository.findByPersonalCode(owner.getPersonalCode()) != null) {
            throw new Exception("Sama isikukoodiga inimene on juba olemas");
        }
        owner.setPets(new ArrayList<>());
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

    // Tagasta kõik omanikud
//    @GetMapping("owners") // localhost:8080/owners
//    public List<OwnerDTO> getAllOwners() {
//        List<Owner> owners = ownerRepository.findAll();
//        List<OwnerDTO> ownerDTOs = new ArrayList<>();
//        for (Owner owner : owners) {
//            OwnerDTO ownerDTO = new OwnerDTO();
//            ownerDTO.setName(owner.getName());
//            ownerDTO.setPets(owner.getPets());
//            ownerDTOs.add(ownerDTO);
//        }
//        return ownerDTOs;
//    }

    // Tagasta kõik omanikud
    @GetMapping("owners") // localhost:8080/owners
    public List<OwnerDTO> findAllOwners() {
        return ownerService.findAllOwners();
    }

    @GetMapping("owner-personcode/{personCode}") // localhost:8080/owner-personcode/
    public Owner findOwnerByPersonCode(@PathVariable String personCode) {
        return ownerRepository.findByPersonalCode(personCode);
    }

    @GetMapping("owner-by-pet") // localhost:8080/owner-by-pet
    public List<Owner> findOwnersByPetCount() {
        return ownerRepository.findAllByPetsGreaterThan(0);
    }

//    public List<Owner> findAllByPetsGreaterThan(List<Owner> owners, int count) {
//        List<Owner> result = new ArrayList<>();
//
//        for (Owner owner : owners) {
//            if (owner.getPets().size() > count) {
//                result.add(owner);
//            }
//        }
//
//        return result;
//

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
        return Arrays.asList(biggest, smallest);
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

// Controller - Võtab vastu päringuid ja tagastab vastuse
// Service - Teeb musta tööd, õhendab Controllerit
