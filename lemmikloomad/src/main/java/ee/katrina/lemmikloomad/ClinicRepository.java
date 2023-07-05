package ee.katrina.lemmikloomad;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, String>  {
}
