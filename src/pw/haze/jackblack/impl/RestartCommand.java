package pw.haze.jackblack.impl;

import com.sun.tools.javac.util.Pair;
import pw.haze.jackblack.BlackJackGame;
import pw.haze.jackblack.Command;
import pw.haze.jackblack.Main;
import pw.haze.jackblack.entity.Player;

import java.util.Optional;

/**
 * @author haze
 * @since 8/5/16
 */
public class RestartCommand implements Command {
    @Override
    public String identifier() {
        return "restart";
    }

    @Override
    public Pair<Boolean, String> execute(Player player, BlackJackGame game, String[] arguments) {
        if(Main.finished.isPresent() || arguments.length > 0 && arguments[0].equalsIgnoreCase("force") ){
            Main.finished = Optional.empty();
            player.getCards().clear();
            return new Pair<>(false, "Game restarted!");
        } else {
            return new Pair<>(true, "Game is still in progress! (want to force a restart? use restart force)");
        }
    }
}
