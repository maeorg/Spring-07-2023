package ee.katrina.cardgame.controller;

// a. Uus kontroller: DatabaseController
// b. Tehke ühendus nii GameRepository'ga kui ka PlayerRepository'ga
// Custom päringud repositorides järgmiselt:

import ee.katrina.cardgame.entity.Game;
import ee.katrina.cardgame.entity.Player;
import ee.katrina.cardgame.repository.GameRepository;
import ee.katrina.cardgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    // 1. Tagastatakse kõik mängijad
    @GetMapping("getAllPlayers") // localhost:8080/getAllPlayers
    public List<Player> getAllPlayers() {
        return playerRepository.findAllBy();
    }

    // 2. Tagastatakse kõik mängud
    @GetMapping("getAllGames") // localhost:8080/getAllGames
    public List<Game> getAllGames() {
        return gameRepository.findAllBy();
    }

    // 3. Tagatatakse kõik mängud correctanswers järjekorras
    @GetMapping("getAllGamesByCorrectAnswers") // localhost:8080/getAllGamesByCorrectAnswers
    public List<Game> getAllGamesByCorrectAnswers() {
        return gameRepository.findAllByOrderByCorrectAnswers();
    }

    // 4. Tagatatakse kõik mängijad high-score järjekorras
    @GetMapping("getAllPlayersByHighestScore") // localhost:8080/getAllPlayersByHighestScore
    public List<Player> getAllPlayersByHighestScore() {
        return playerRepository.findAllByOrderByHighestScore();
    }

    // 5. Tagastataske kõik selle mängija mängud
    @GetMapping("getAllGamesForPlayer/{player_name}")
    public List<Game> getAllGamesForPlayer(@PathVariable String player_name) {
        return gameRepository.findAllByPlayerName(player_name);
    }

    // 6. Tagastataske kõik selle mängija mängud correctAnswers järjekorras
    @GetMapping("getAllGamesForPlayerOrderByCorrectAnswers/{player_name}")
    public List<Game> getAllGamesForPlayerOrderByCorrectAnswers(@PathVariable String player_name) {
        return gameRepository.findAllByPlayerNameOrderByCorrectAnswers(player_name);
    }

    // 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
    @GetMapping("getAllGamesAtleastTwoCorrectAnswers")
    public List<Game> getAllGamesAtleastTwoCorrectAnswers() {
        return gameRepository.findAllByCorrectAnswersIsGreaterThanEqual(2);
    }

    // 8. Kõige suurema correctanswers mäng
    @GetMapping("getMostCorrectAnswersGame")
    public Game getMostCorrectAnswersGame() {
        return gameRepository.findFirstByOrderByCorrectAnswersDesc();
    }

    // 9. Kõige suurema highscore-ga mängija
    @GetMapping("getPlayerWithHighestScore")
    public Player getPlayerWithHighestScore() {
        return playerRepository.findFirstByOrderByHighestScoreDesc();
    }

    // 10. Top3 correctanswers mängud
    @GetMapping("getTopThreeCorrectAnswerGames")
    public List<Game> getTopThreeCorrectAnswerGames() {
        return gameRepository.findTop3ByOrderByCorrectAnswersDesc();
    }

}
