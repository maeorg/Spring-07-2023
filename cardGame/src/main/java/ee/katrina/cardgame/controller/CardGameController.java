package ee.katrina.cardgame.controller;

import ee.katrina.cardgame.entity.Card;
import ee.katrina.cardgame.service.CardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardGameController {

    @Autowired
    CardGameService cardGameService;

    @GetMapping("cardGame/startGame") // localhost:8080/cardGame/startGame
    public String startGame() {
        return cardGameService.startGame();
    }

    @GetMapping("cardGame/StartRoundRequest") // localhost:8080/cardGame/StartRoundRequest
    public String startRound() {
        return cardGameService.startRound().toString();
    }

    @GetMapping("cardGame/guess/{guess}") // localhost:8080/cardGame/guess/higher
    public String guess(@PathVariable String guess) {
        return cardGameService.checkGuessResult(guess);
    }

    @GetMapping("cardGame/viewCards") // localhost:8080/cardGame/viewCards
    public List<Card> viewCards() {
        return cardGameService.viewCards();
    }
}
