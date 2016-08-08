package pw.haze.jackblack;

import com.sun.tools.javac.util.Pair;
import pw.haze.jackblack.entity.Entity;
import pw.haze.jackblack.entity.Player;
import pw.haze.jackblack.entity.Robot;
import pw.haze.jackblack.entity.impl.RandomBot;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author haze
 * @since 8/5/16
 */
public class Main {

    public static Optional<Pair<Boolean, Entity>> finished = Optional.empty();
    public static boolean hasPrintedResult = false;
    private static List<Entity> contestants = new ArrayList<>();


    @SuppressWarnings("unchecked")
    public static <T> T[] skip(T[] arr, Class<T> type, int num) {
        T[] skipArr = (T[]) Array.newInstance(type, arr.length - num);
        for(int i = 0; i < arr.length; i++) {
            if(i < num) continue;
            skipArr[i - num] = arr[i];
        }
        return skipArr;
    }


    public static void generateContestants(String playerName, int amount) {
        final Random random = new Random();
        String[] robotNames = new String[]{"Gamble", "Sleaze", "Worker", "Silly"};
        Queue<String> nameQueue = new ConcurrentLinkedQueue<>(Arrays.asList(robotNames));
        contestants.add(new Player(playerName));
        for(int i=0; i < amount; i++)
            contestants.add(new RandomBot(nameQueue.poll()));
    }

    public static String getCommandsString(BlackJackGame game){
        final StringJoiner joiner = new StringJoiner(", ");
        for(Command cmd: game.getCommands())
            joiner.add(cmd.identifier());
        return joiner.toString();
    }

    public static void enterREPL(BlackJackGame game, Player player, Scanner in) {
        boolean done = false;
        while(!done) {
            String line = in.nextLine();
            switch(line.toLowerCase()) {
                case "end":
                    done = true;
                    break;
                default:
                    String command = line.split(" ")[0];
                    String[] arguments = skip(line.split(" "), String.class, 1);
                    Pair<Boolean, String> output = game.evaluate(player, command, arguments);
                    done = !output.fst;
                    System.out.printf("[JB {YOU}]: %s\n", output.snd);
            }
        }
    }

    public static Optional<Entity> findWinner(List<Entity> contestants) {
        return contestants.parallelStream().sorted((o1, o2) -> new Integer(o1.sum()).compareTo(o2.sum())).findFirst();
    }

    public static void main(String... args) throws InterruptedException {
        final Scanner scan = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String playerName = scan.nextLine();
        System.out.println("Please enter the amount of bots you want to play against: ");
        int bots = scan.nextInt();
        generateContestants(playerName, bots);
        BlackJackGame game = new BlackJackGame();
        System.out.printf("[JB]: Commands are: %s\n", getCommandsString(game));
        while (!finished.isPresent()) {
            for(Entity ent: contestants){
                if(ent instanceof Player) {
                    System.out.println("[JB]: Your turn!");
                    enterREPL(game, (Player) ent, scan);
                } else if(ent instanceof Robot && !ent.isDisqualified()) {
                    Robot roboEnt = (Robot) ent;
                    System.out.printf("[JB]: %s %s\n", ent.getName(), game.evaluate(roboEnt, roboEnt.executeMove()));
                }
                ent.checkDisqualified();
                if(game.getPlayingDeck().size() == 0) {
                    final Optional<Entity> winner = findWinner(contestants);
                    if(winner.isPresent()) {
                        System.out.println("The winner is " + winner.get().getName() + "!");
                    } else {
                        System.out.println("Somehow, nobody managed to win. Wow.");
                    }
                    Main.finished = Optional.empty();
                    generateContestants(playerName, bots);
                } else {
                    Thread.sleep(new Random().nextInt(3500 - 500) + 500);
                }
            }
            System.out.println("----- Round End! -----\n");
        }
    }
}
