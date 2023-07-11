package ee.katrina.cardgame.repository;

import ee.katrina.cardgame.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    // 1. Tagastatakse kõik mängijad
    List<Player> findAllBy();
}
