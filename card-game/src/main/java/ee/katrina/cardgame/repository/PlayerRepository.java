package ee.katrina.cardgame.repository;

import ee.katrina.cardgame.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    // 1. Tagastatakse kõik mängijad
    List<Player> findAllBy();

    // 4. Tagatatakse kõik mängijad high-score järjekorras
    List<Player> findAllByOrderByHighestScore();

    // 9. Kõige suurema highscore-ga mängija
    Player findFirstByOrderByHighestScoreDesc();
}
