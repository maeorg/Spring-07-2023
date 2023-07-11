package ee.katrina.cardgame.controller;

import ee.katrina.cardgame.model.Card;
import ee.katrina.cardgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    boolean guessed;

    @Autowired
    GameService gameService;

    @GetMapping("start/{playerName}") // localhost:8080/start/Katrina
    public Card startGame(@PathVariable String playerName) {
        guessed = false;
        gameService.getPlayer(playerName);
        return gameService.getBaseCard();
    }

    @GetMapping("guess/{input}")
    public String guess(@PathVariable String input) {
        String error = gameService.validate(guessed, input);
        if (!error.equals("")) return error;
        guessed = true;
        return gameService.checkIfCorrect(input);
    }
}