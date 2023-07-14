package ee.katrina.salad.repository;

import ee.katrina.salad.entity.Toit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToitRepository extends JpaRepository<Toit, Long> {

    Toit findByNimetus(String nimetus);
}
