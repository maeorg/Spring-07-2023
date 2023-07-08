package ee.katrina.cardgame.repository;

import ee.katrina.cardgame.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardGameRepository extends JpaRepository<Card, Long> {
}
