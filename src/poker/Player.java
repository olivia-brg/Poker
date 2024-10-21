package poker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final List<Card> hand;
    private final int tokens;

    public Player (String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.tokens = 150;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> card) {
        this.hand.addAll(card);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " : " + hand + "\nHas " + tokens + " tokens";
    }
}
