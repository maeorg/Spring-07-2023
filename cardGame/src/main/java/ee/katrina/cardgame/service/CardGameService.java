package ee.katrina.cardgame.service;

import ee.katrina.cardgame.entity.Card;
import ee.katrina.cardgame.repository.CardGameRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Getter
public class CardGameService {

    @Autowired
    CardGameRepository cardGameRepository;

    private Card baseCard;
    private Card resultCard;
    private long timestamp;
    private int points;
    private  String[] suits = {"hearts", "diamonds", "spades", "clubs"};
    private Map<String, Integer> ranks = Stream.of(new Object[][]{
            {"2", 2}, {"3", 3}, {"4", 4}, {"5", 5}, {"6", 6}, {"7", 7}, {"8", 8}, {"9", 9}, {"10", 10}, {"J", 10}, {"Q", 10}, {"K", 10}, {"A", 10}
    }).collect(Collectors.toMap(p -> (String) p[0], p -> (Integer) p[1]));

    public List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks.keySet()) {
                Card card = new Card();
                card.setSuit(suit);
                card.setRank(rank);
                deck.add(card);
            }
        }
        deck = shuffleDeck(deck);
        long i = 1L;
        for (Card card : deck) {
            card.setId(i);
            i++;
        }
        cardGameRepository.saveAll(deck);
        return deck;
    }

    public List<Card> shuffleDeck(List<Card> deck) {
        List<Card> newDeck = new ArrayList<>();
        while (!deck.isEmpty()) {
            int nr = new Random().nextInt(deck.size());
            newDeck.add(deck.get(nr));
            deck.remove(nr);
        }
        return newDeck;
    }

    public Card drawCard() {
        if (cardGameRepository.findAll().isEmpty()) return null;
        long startId = 53 - cardGameRepository.findAll().size();
        Card card = cardGameRepository.findById(startId).get();
        cardGameRepository.delete(card);
        return card;
    }

    public Card startRound() {
        resultCard = drawCard();
        timestamp = new Date().getTime();
        return baseCard;
    }

    public void startGame() {
        points = 0;
        createDeck();
        baseCard = drawCard();
    }

    public boolean checkTimeout() {
        return new Date().getTime() > timestamp + 10000;
    }

    public int checkResult(String choice) {
        Integer rankBaseCard = ranks.get(baseCard.getRank());
        Integer rankResultCard = ranks.get(resultCard.getRank());
        if (choice.equals("equal") && rankBaseCard.equals(rankResultCard)) return 1;
        if (choice.equals("higher") && rankBaseCard < rankResultCard) return 1;
        if (choice.equals("lower") && rankBaseCard > rankResultCard) return 1;
        return 0;
    }

    public int calculatePoints(int result) {
        points += result;
        baseCard = resultCard;
        return points;
    }


    public List<Card> viewCards() {
        return cardGameRepository.findAll();
    }
}
