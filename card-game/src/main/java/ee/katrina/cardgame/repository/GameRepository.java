package ee.katrina.cardgame.repository;

import ee.katrina.cardgame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    // 2. Tagastatakse kõik mängud
    List<Game> findAllBy();

    // 3. Tagatatakse kõik mängud correctanswers järjekorras
    List<Game> findAllByOrderByCorrectAnswers();

    // 5. Tagastataske kõik selle mängija mängud
    List<Game> findAllByPlayerName(String name);

    // 6. Tagastataske kõik selle mängija mängud correctAnswers järjekorras
    List<Game> findAllByPlayerNameOrderByCorrectAnswers(String name);

    // 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
    List<Game> findAllByCorrectAnswersIsGreaterThanEqual(int nr);

    // 8. Kõige suurema correctanswers mäng
    Game findFirstByOrderByCorrectAnswersDesc();

    // 10. Top3 correctanswers mängud
    List<Game> findTop3ByOrderByCorrectAnswersDesc();
}
