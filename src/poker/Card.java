package poker;

public class Card {

    enum Value { DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF, DIX, VALET, DAME, ROI, AS }
    enum Color { PIQUE, TREFLE, COEUR, CARREAU }

    private final Color color;
    private final Value value;

    public Card (Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return value + " " + color;
    }
}
