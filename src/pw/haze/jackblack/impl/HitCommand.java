package pw.haze.jackblack.impl;

import com.sun.tools.javac.util.Pair;
import pw.haze.jackblack.BlackJackGame;
import pw.haze.jackblack.Command;
import pw.haze.jackblack.Main;
import pw.haze.jackblack.core.Card;
import pw.haze.jackblack.entity.Player;

/**
 * @author haze
 * @since 8/5/16
 */
public class HitCommand implements Command {
    @Override
    public String identifier() {
        return "hit";
    }

    @Override
    public Pair<Boolean, String> execute(Player player, BlackJackGame game, String[] arguments) {
        if(player.isDisqualified()) return new Pair<>(true, "You are disqualified!");
        if(Main.finished.isPresent()) return new Pair<>(true, "The game has already taken place! Try \"restart\" to restart!");
        final Card drawnCard = player.hit(game);
        System.out.printf("[JB]: There are %d cards left in the playing deck.\n", game.getPlayingDeck().size());
        return new Pair<>(false, String.format("(Sum %d) You drew a %s card with a value of: %d.", player.sum(), drawnCard.getType().name().toLowerCase(), drawnCard.getValue()));
    }
}
