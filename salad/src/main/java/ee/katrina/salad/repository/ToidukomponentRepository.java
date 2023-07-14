package ee.katrina.salad.repository;

import ee.katrina.salad.entity.Toidukomponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToidukomponentRepository extends JpaRepository<Toidukomponent, Long> {

    List<Toidukomponent> findToidukomponentsByToiduaineNimetus(String nimetus);
}
