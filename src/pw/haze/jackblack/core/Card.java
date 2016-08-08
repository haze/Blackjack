package pw.haze.jackblack.core;


public class Card {
    private int value;
    private Suit suit;
    private CardType type;


    public Card(int value, Suit suit, CardType type) {
        this.value = value;
        this.suit = suit;
        this.type = type;
    }

    public CardType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }


    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return String.format("[%s] {%d}", this.getSuit().name(), this.getValue());
    }
}
