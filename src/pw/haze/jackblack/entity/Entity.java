package pw.haze.jackblack.entity;

import pw.haze.jackblack.BlackJackGame;
import pw.haze.jackblack.Main;
import pw.haze.jackblack.core.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author haze
 * @since 8/5/16
 */
public abstract class Entity {

    private String name;
    private boolean disqualified;

    public List<Card> getCards() {
        return cards;
    }

    private List<Card> cards = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Entity(String name) {
        this.name = name;
    }


    public Card hit(BlackJackGame game) {
        final Card drawnCard = game.drawCard();
        game.getPlayingDeck().remove(drawnCard);
        getCards().add(drawnCard);
        return drawnCard;
    }

    public boolean isDisqualified() {
        return disqualified;
    }

    public void checkDisqualified() {
        disqualified = sum() > 21;
        if(disqualified) {
            System.out.printf("[JB]: %s has been disqualified!\n", this.name);
        }

    }

    public int sum() { return cards.stream().mapToInt(Card::getValue).sum(); }
    public boolean hasWon() { return sum() == 21; }

}
