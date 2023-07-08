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
        cardGameService.startGame();
        return "Game started. Deck is set and base card is drawn. Start your round.";
    }

    @GetMapping("cardGame/StartRoundRequest") // localhost:8080/cardGame/StartRoundRequest
    public String startRound() {
        return cardGameService.startRound().toString();
    }

    @GetMapping("cardGame/guess/{guess}") // localhost:8080/cardGame/guess/higher
    public String guess(@PathVariable String guess) {
        if (cardGameService.checkTimeout()) return "TIME_OUT";
        int result = cardGameService.checkResult(guess);
        String resultCard = cardGameService.getResultCard() + ". ";
        String points =  "You have " + cardGameService.calculatePoints(result) + " points. ";
        String instruction = "You have 10 seconds to make your next guess (higher, lower or equal)!";
        cardGameService.startRound();
        if (result > 0) {
            return resultCard + "Correct guess! " + points + instruction;
        } else {
            return resultCard + "Incorrect guess! " + points + instruction;
        }
    }

    @GetMapping("cardGame/viewCards") // localhost:8080/cardGame/viewCards
    public List<Card> viewCards() {
        return cardGameService.viewCards();
    }
}
