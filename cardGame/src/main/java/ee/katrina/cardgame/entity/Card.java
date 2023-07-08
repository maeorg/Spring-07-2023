package ee.katrina.cardgame.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Card {

    @Id
    private Long id;
    private String suit;
    private String rank;

    public String toString() {
        return "Card: " + this.rank + " of " + this.suit;
    }
}
