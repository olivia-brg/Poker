package poker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final List<Card> hand;
    private final int tokens;
    private boolean Folded = false;

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

    public int getTokens() {
        return tokens;
    }

    public String getName() {
        return name;
    }

    public boolean hasFolded() {
        return Folded;
    }
    
    public void setFolded(boolean Folded) {
        this.Folded = Folded;
    }

    @Override
    public String toString() {
        return name + " : " + hand + " and has " + tokens + " tokens\n";
    }
}
