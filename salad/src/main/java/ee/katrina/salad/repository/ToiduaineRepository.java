package ee.katrina.salad.repository;

import ee.katrina.salad.entity.Toiduaine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiduaineRepository extends JpaRepository<Toiduaine, Long> {

    Toiduaine findToiduaineByNimetus(String nimetus);
}
