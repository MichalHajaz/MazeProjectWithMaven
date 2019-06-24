
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
    private Map<String, Map<String, Integer>> reportMap = new HashMap<>();



    public Match(Set<Class<? extends IPlayer>> players, List<Maze> mazes) {
        this.playersClasses = players;
        this.mazes = mazes;
    }


    private Map<String, Map<String, Integer>> loadTable() {

        for ( Class player : playersClasses) {
            for (Maze maze : mazes) {
                Map<String, Integer> playersRes = new HashMap<>();
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
                    Map<String, Integer> gameRes = new HashMap<>();
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
        for (Map.Entry<String, Map<String, Integer>> pair : reportMap.entrySet()){

            HashMap<String, Integer> hm = (HashMap<String, Integer>) pair.getValue();
            String key = (String) pair.getKey();
            returnString.append("|\t").append(key).append("\t|");
            for (HashMap.Entry<String, Integer> pair2 : hm.entrySet()){
                returnString.append("|\t").append(String.valueOf(pair2.getValue())).append("\t|");
            }

            returnString.append("\r\n");
        }
        returnString.toString();
    }

    @Override
    public String toString() {
        return "Match{" +
                "reportMap=" + reportMap +
                '}';
    }

    public static void main(String[] args) throws Exception {


        CommandLineParser commandLineParser = new CommandLineParser(args[1],args[3]);

        //if(commandLineParser.validate(args)){

            commandLineParser.init();
            Match match = new Match(commandLineParser.getPlayers(), commandLineParser.getMazes());
          //  match.loadTable();
            match.runMatch(Integer.parseInt(args[5]));
           // match.printReport();

        //}

    }


}
