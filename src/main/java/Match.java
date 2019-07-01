
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import game_manager.GameManager;
import maze.Maze;
import player.IPlayer;


public class Match {

    private Set<Class<? extends IPlayer>> playersClasses;
    private List<Maze> mazes;
    private Map<String, TreeMap<String, Integer>> reportMap;
    private List<GameManager> gameManagers = new ArrayList<>();


    public Match(Set<Class<? extends IPlayer>> players, List<Maze> mazes) {
        this.playersClasses = players;
        this.mazes = mazes;
    }


    private Map<String, TreeMap<String, Integer>> createTable() {
        // maze => player => result
        Map<String, TreeMap<String, Integer>> table = new TreeMap<>();
        for (Maze maze : mazes) {
            TreeMap<String, Integer> playersRes = new TreeMap<>(Comparator.naturalOrder());
            table.put(maze.getName(), playersRes);
            for (Class player : playersClasses) {
                playersRes.put(player.getSimpleName(), 0);
            }
        }
        return table;
    }


    public void runMatch(int numThreads) {

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);

        for (Class<? extends IPlayer> playerClass : playersClasses) {

            IPlayer player = null;
            try {
                Constructor<?> ctor = playerClass.getConstructor();
                 player = (IPlayer) ctor.newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Player not loaded well" + playerClass.getSimpleName());
                continue;
            }

            for (Maze maze : mazes) {
                GameManager gameManager = new GameManager(maze, player);
                gameManagers.add(gameManager);
            }
        }

        for (GameManager gameManager : gameManagers) {
            threadPool.execute(gameManager);

        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO shutdownNow ?
        }

        reportMap = createTable();
        for (GameManager gameManager : gameManagers) {
            Map<String,Integer> gameRes = reportMap.get(gameManager.getMaze().getName());
            gameRes.put(gameManager.getPlayer().getClass().getSimpleName(),gameManager.getGameResult());

        }
        printReport();
    }


    private void printReport() {

        boolean didPrintHeaders = false;

        for (Map.Entry<String, TreeMap<String, Integer>> gameReport : reportMap.entrySet()) {

            if (!didPrintHeaders) {
                StringBuilder playersName = new StringBuilder();
                for (Map.Entry<String, Integer> playerName : gameReport.getValue().entrySet()) {
                    playersName.append(getPaddedString(playerName.getKey()) + " | ");
                }
                System.out.println(getPaddedString("Maze Name") + " | " + getPaddedString(playersName.toString()));
                didPrintHeaders = true;
            }
            StringBuilder gameRes = new StringBuilder();
            gameRes.append(getPaddedString(gameReport.getKey()) + " | ");
            for (Map.Entry<String, Integer> pair1 : gameReport.getValue().entrySet()) {
                gameRes.append(getPaddedString(String.valueOf(pair1.getValue()))).append(" | ");
            }
            System.out.println(gameRes);
        }
    }

    private String getPaddedString(String string) {
        return String.format(" %-50s\t", string);
    }

    public static void main(String[] args) throws Exception {

        CommandLineParser commandLineParser = new CommandLineParser(args[1], args[3]);

        if (commandLineParser.validateArguments(args)) {
            commandLineParser.init();
            Match match = new Match(commandLineParser.getPlayers(), commandLineParser.getMazes());
            match.runMatch(Integer.parseInt(args[5]));
        }
    }
}
