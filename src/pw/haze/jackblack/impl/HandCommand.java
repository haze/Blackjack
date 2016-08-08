package pw.haze.jackblack.impl;

import com.sun.tools.javac.util.Pair;
import pw.haze.jackblack.BlackJackGame;
import pw.haze.jackblack.Command;
import pw.haze.jackblack.Main;
import pw.haze.jackblack.core.Card;
import pw.haze.jackblack.entity.Player;

import java.util.StringJoiner;

/**
 * @author haze
 * @since 8/5/16
 */
public class HandCommand implements Command {
    @Override
    public String identifier() {
        return "hand";
    }

    @Override
    public Pair<Boolean, String> execute(Player player, BlackJackGame game, String[] arguments) {
        final String starter = String.format("Cards [sum: %d] (%d): ", player.sum(), player.getCards().size());
        final StringJoiner joiner = new StringJoiner(", ");
        for(Card card: player.getCards())
            joiner.add(card.toString());
        return new Pair<>(true, starter + joiner.toString());
    }
}
