package pw.haze.jackblack;

import com.sun.tools.javac.util.Pair;
import pw.haze.jackblack.entity.Player;

/**
 * @author haze
 * @since 8/5/16
 */
public interface Command {
    String identifier();
    Pair<Boolean, String> execute(Player player, BlackJackGame game, String[] arguments);
}
