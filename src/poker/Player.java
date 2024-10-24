package poker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final List<Card> hand;
    private int tokens;
    private boolean Folded = false;
    private int actualBet;

    public Player (String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.tokens = 150;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> card) {
        this.hand.clear();
        this.hand.addAll(card);
    }

    public int getTokens() {
        return tokens;
    }

    public void earnedTokens(int gain) {
        this.tokens += gain;
    }

    public void betTokens(int bet) {
        this.tokens -= bet;
        this.actualBet += bet;
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

    public int getActualBet() {
        return actualBet;
    }
    
    public void resetActualBet() {
        this.actualBet = 0;
    }

    @Override
    public String toString() {
        return name + " : " + hand + " and has " + tokens + " tokens\n";
    }
}
