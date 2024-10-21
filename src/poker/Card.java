package poker;

public class Card {

    enum Value { TWO, THREE, FOUR, FIVE, SIX, SEVEN, HEIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }
    enum Color { SPADE, CLUB, HEART, DIAMOND }

    private final Color color;
    private final Value value;

    public Card (Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " of " + color;
    }
}
