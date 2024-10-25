package poker;

import poker.Card.Value;

public class Card {

    enum Value {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, HEIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    enum Symbol {
        SPADE, CLUB, HEART, DIAMOND
    }

    private final Symbol symbol;
    private final Value value;

    public Card(Symbol symbol, Value value) {
        this.symbol = symbol;
        this.value = value;
    }

    @Override
    public String toString() {
        if (null != symbol) switch (symbol) {
            case SPADE -> {
                System.out.print(Color.BLACK);
                System.out.print(Color.WHITE_BACKGROUND);
            }
            case CLUB -> {
                System.out.print(Color.WHITE);
                System.out.print(Color.BLACK_BACKGROUND);
            }
            case HEART -> {
                System.out.print(Color.RED);
                System.out.print(Color.WHITE_BACKGROUND);
            }
            case DIAMOND -> {
                System.out.print(Color.WHITE);
                System.out.print(Color.RED_BACKGROUND);
            }
            default -> {
            }
        }

        return value + " of " + symbol;
    }
}
