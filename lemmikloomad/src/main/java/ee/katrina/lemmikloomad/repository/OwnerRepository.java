package ee.katrina.lemmikloomad.repository;

import ee.katrina.lemmikloomad.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    // Owner or List<Owner>
    Owner findByPersonalCode(String personalCode); // Hibernate

    @Query("SELECT o FROM Owner o WHERE SIZE(o.pets) > :count")
    List<Owner> findAllByPetsGreaterThan(int count);
}
