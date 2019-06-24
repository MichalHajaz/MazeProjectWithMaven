
import java.io.StringWriter;
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
    private Map<String, TreeMap<String, Integer>> reportMap = new TreeMap<>();



    public Match(Set<Class<? extends IPlayer>> players, List<Maze> mazes) {
        this.playersClasses = players;
        this.mazes = mazes;
    }


    private Map<String, TreeMap<String, Integer>> loadTable() {

        for ( Class player : playersClasses) {
            for (Maze maze : mazes) {
                TreeMap<String, Integer> playersRes = new TreeMap<>(Comparator.naturalOrder());
                playersRes.put(player.getClass().getSimpleName(), 0);
                reportMap.put(maze.getName(), playersRes);
            }
        }return reportMap;
    }


    public void runMatch(int numThreads) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);



            for (Class<? extends IPlayer> playerClass : playersClasses) {

                Constructor<?> ctor = null;
                try {
                    ctor = playerClass.getConstructor();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                IPlayer player = (IPlayer) ctor.newInstance();


                for (Maze maze : mazes) {
                    GameManager gameManager = new GameManager(maze, player);
                    threadPool.execute(gameManager);
                    TreeMap<String, Integer> gameRes = new TreeMap<>();
                    gameRes.put(player.getClass().getSimpleName(), gameManager.getGameResult());
                    reportMap.put(maze.getName(), gameRes);
                }
            }


        threadPool.shutdown();
        try {
            threadPool.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printReport();
    }


    private void printReport() {


        StringWriter returnString = new StringWriter();
        boolean didPrintHeaders = false;

        for (Map.Entry<String, TreeMap<String, Integer>> pair : reportMap.entrySet()) {

            if (!didPrintHeaders) {
                StringBuilder playersName = new StringBuilder();
                for (Map.Entry<String, Integer> pair1 : pair.getValue().entrySet()) {
                    playersName.append(getPaddedString(pair1.getKey()) + " | ");
                }
                System.out.println(getPaddedString("Maze Name") + " | " + getPaddedString(playersName.toString()));
                didPrintHeaders = true;
            }
            StringBuilder mazeAndRes = new StringBuilder();
            mazeAndRes.append(getPaddedString(pair.getKey())  + " | ");
            for (Map.Entry<String, Integer> pair1 : pair.getValue().entrySet()) {
                mazeAndRes.append(getPaddedString(String.valueOf(pair1.getValue()))).append(" | ");
            }
            System.out.println(mazeAndRes);
        }
    }

    private String getPaddedString(String string){
        return String.format(" %-50s\t",string);
    }

    @Override
    public String toString() {
        return "Match{" +
                "reportMap=" + reportMap +
                '}';
    }

    public static void main(String[] args) throws Exception {


        CommandLineParser commandLineParser = new CommandLineParser(args[1],args[3]);

        if(commandLineParser.validateArguments(args)){

            commandLineParser.init();
            Match match = new Match(commandLineParser.getPlayers(), commandLineParser.getMazes());
          //  match.loadTable();
            match.runMatch(Integer.parseInt(args[5]));
            match.printReport();

        }



    }


}
