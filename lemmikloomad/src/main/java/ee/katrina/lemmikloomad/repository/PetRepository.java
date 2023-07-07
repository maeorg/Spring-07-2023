package ee.katrina.lemmikloomad.repository;

import ee.katrina.lemmikloomad.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, String> { // repository suhtleb andmebaasiga

    Pet findFirstByOrderByWeightAsc();

    Pet findFirstByOrderByWeightDesc();

    List<Pet> findAllByWeightBetween(double start, double end);
}
