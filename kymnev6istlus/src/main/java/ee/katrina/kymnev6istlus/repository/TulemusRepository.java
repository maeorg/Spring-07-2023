package ee.katrina.kymnev6istlus.repository;

import ee.katrina.kymnev6istlus.entity.Tulemus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TulemusRepository extends JpaRepository<Tulemus, Long> {

    List<Tulemus> findAllBySportlaneId(Long id);

}
