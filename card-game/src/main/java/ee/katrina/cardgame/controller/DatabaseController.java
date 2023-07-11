package ee.katrina.cardgame.controller;

// a. Uus kontroller: DatabaseController
// b. Tehke ühendus nii GameRepository'ga kui ka PlayerRepository'ga
// Custom päringud repositorides järgmiselt:

// 4. Tagatatakse kõik mängijad high-score järjekorras
// 5. Tagastataske kõik selle mängija mängud
// 6. Tagastataske kõik selle mängija mängud correctAnswers järjekorras
// 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
// 8. Kõige suurema correctanswers mäng
// 9. Kõige suurema highscore-ga mängija
// 10. Top3 correctanswers mängud

import ee.katrina.cardgame.entity.Game;
import ee.katrina.cardgame.entity.Player;
import ee.katrina.cardgame.repository.GameRepository;
import ee.katrina.cardgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        return null; // TO DO
    }
}
