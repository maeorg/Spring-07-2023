package ee.katrina.lemmikloomad.repository;

import ee.katrina.lemmikloomad.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, String>  {
}
