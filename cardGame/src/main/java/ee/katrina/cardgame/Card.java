package ee.katrina.cardgame;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Card {

    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    private static final String[] suits = {"hearts", "diamonds", "spades", "clubs"};
    private static final Map<String, Integer> ranks = Stream.of(new Object[][]{
            {"2", 2}, {"3", 3}, {"4", 4}, {"5", 5}, {"6", 6}, {"7", 7}, {"8", 8}, {"9", 9}, {"10", 10}, {"J", 10}, {"Q", 10}, {"K", 10}, {"A", 10}
    }).collect(Collectors.toMap(p -> (String) p[0], p -> (Integer) p[1]));

    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks.keySet()) {
                Card card = new Card(suit, rank);
                deck.add(card);
            }
        }
        return shuffleDeck(deck);
    }

    public static List<Card> shuffleDeck(List<Card> deck) {
        List<Card> newDeck = new ArrayList<>();
        while (!deck.isEmpty()) {
            int nr = new Random().nextInt(deck.size());
            newDeck.add(deck.get(nr));
            deck.remove(nr);
        }
        return newDeck;
    }

    public static Card drawCard(List<Card> deck) {
        if (deck.isEmpty()) return null;
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public static int checkResult(Card baseCard, Card resultCard, String choice, List<Card> deck) {
        Integer rankBaseCard = ranks.get(baseCard.rank);
        Integer rankResultCard = ranks.get(resultCard.rank);
        if (choice.equals("e") && rankBaseCard.equals(rankResultCard)) return 1;
        if (choice.equals("h") && rankBaseCard < rankResultCard) return 1;
        if (choice.equals("l") && rankBaseCard > rankResultCard) return 1;
        return 0;
    }

    public String toString() {
        return this.rank + " of " + this.suit;
    }

    public static void main(String[] args) {
        int points = 0;
        List<Card> deck = createDeck();
        Card baseCard = drawCard(deck);
        while (!deck.isEmpty()) {
            System.out.println(baseCard);
            Card resultCard = drawCard(deck);
            long timestamp = new Date().getTime(); // current timestamp in milliseconds
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter your choice(h - higher, l - lower, e - equal): ");
            String choice = myObj.nextLine();
            if (new Date().getTime() > timestamp+10000) {
                System.out.println("TIME_OUT");
                break;
            }
            points += checkResult(baseCard, resultCard, choice, deck);
            baseCard = resultCard;
            System.out.println("You have " + points + " points.");
        }
    }
}
