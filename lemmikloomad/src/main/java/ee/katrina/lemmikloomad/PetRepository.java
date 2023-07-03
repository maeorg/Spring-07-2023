package ee.katrina.lemmikloomad;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, String> { // repository suhtleb andmebaasiga
}
