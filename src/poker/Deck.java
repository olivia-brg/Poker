package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    public static List<Card> deck;

    public static void CreateDeck() {
        deck = new ArrayList<>();

        for (Card.Color color : Card.Color.values()) {
            for (Card.Value value : Card.Value.values()) {
                deck.add(new Card(color, value));
            }
        }
        Collections.shuffle(deck);
    }

    public static List<Card> Deal(int number) {
        List<Card> draw = new ArrayList<>();

        if (deck.size() < number) {
            return null;
        }
        for (int i = 0; i < number; i++) {
            draw.add(deck.get(0));
            deck.remove(0);
        }
        return draw;
    }
}
