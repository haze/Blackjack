package pw.haze.jackblack;

import com.sun.tools.javac.util.Pair;
import pw.haze.jackblack.core.Card;
import pw.haze.jackblack.core.CardType;
import pw.haze.jackblack.core.Suit;
import pw.haze.jackblack.entity.Player;
import pw.haze.jackblack.entity.Robot;
import pw.haze.jackblack.entity.RobotMoveType;
import pw.haze.jackblack.impl.HandCommand;
import pw.haze.jackblack.impl.HitCommand;
import pw.haze.jackblack.impl.RestartCommand;

import java.util.*;

/**
 * @author haze
 * @since 8/5/16
 */
public class BlackJackGame {

    public List<Command> getCommands() {
        return commands;
    }

    private List<Command> commands;

    public static final List<Card> BASE_DECK = generateBaseDeck();
    private List<Card> playingDeck = BASE_DECK;

    public BlackJackGame() {
        this.commands = new ArrayList<>();
        Collections.shuffle(this.playingDeck, new Random(System.nanoTime()));
        this.commands.add(new HitCommand());
        this.commands.add(new HandCommand());
        this.commands.add(new RestartCommand());
    }

    public List<Card> getPlayingDeck() {
        return playingDeck;
    }

    public Card drawCard() {
        final Random rand = new Random();
        return this.getPlayingDeck().get(rand.nextInt(this.getPlayingDeck().size()));
    }


    public String evaluate(Robot robot, RobotMoveType moveType) {
        switch(moveType) {
            case HIT:
                final Card drawnCard = robot.hit(this);
                System.out.printf("[JB]: There are %d cards left in the playing deck.\n", getPlayingDeck().size());
                return String.format("has drawn a %s card with a value of %d. [sum: %d]", drawnCard.getType().name().toLowerCase(), drawnCard.getValue(), robot.sum());
            case STAND: // give up
                return "has stood.";
            default:
                return "Something happened and I broke something, nice!";
        }
    }

    public Pair<Boolean, String> evaluate(Player player, String command, String[] arguments){
        for(Command cmd: this.commands)
            if (cmd.identifier().equalsIgnoreCase(command)) return cmd.execute(player, this, arguments);
        return new Pair<>(true, "Command " + command + " not found!");
    }



    // base deck
    public static List<Card> generateBaseDeck() {
        final List<Card> cards = new ArrayList<>();
        for(Suit suit: Suit.values()) {
            for (int i = 0; i < 13; i++) {
                if (i > 9) cards.add(new Card(10, suit, CardType.FACE));
                else cards.add(new Card(i + 1, suit, CardType.NUMBERED));
            }
        }
        return cards;
    }

}
