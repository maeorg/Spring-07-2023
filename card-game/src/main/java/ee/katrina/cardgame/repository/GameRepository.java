package ee.katrina.cardgame.repository;

import ee.katrina.cardgame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    // 2. Tagastatakse kõik mängud
    List<Game> findAllBy();

    // 3. Tagatatakse kõik mängud correctanswers järjekorras
    // TO DO
}
