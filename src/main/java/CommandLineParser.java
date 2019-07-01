import files.MazeParser;
import maze.Maze;
import org.reflections.Reflections;
import player.IPlayer;

import java.io.File;

import java.util.ArrayList;

import java.util.Objects;
import java.util.Set;

public class CommandLineParser {

    private String mazeFolder;
    private String playerPackage;
    private Set<Class<? extends IPlayer>> playersClasses;
    private ArrayList<Maze> mazes = new ArrayList<>();
    private ArrayList<Exception> allFailureMazes = new ArrayList<>();



    public CommandLineParser(String mazeFolder, String playerP) {
        this.mazeFolder = mazeFolder;
        playerPackage = playerP;
    }


    public Set<Class<? extends IPlayer>> getPlayers() {
        return playersClasses;
    }

    public ArrayList<Maze> getMazes() {
        return mazes;
    }

    private void loaderMazes() {

        File file = new File(mazeFolder);

        for (File fileName : Objects.requireNonNull(file.listFiles((dir, name) -> name.endsWith(".txt")))) {
            MazeParser mazeParser = new MazeParser();
            Maze maze = mazeParser.getMaze(fileName);
            if (maze != null) {
                mazes.add(maze);
            }
        }
    }

    private void initPlayersClasses() {

        Reflections reflections = new Reflections("player");
        playersClasses = reflections.getSubTypesOf(IPlayer.class);
        System.out.println(playersClasses);
        playersClasses.removeIf((playersClass)->playersClass.isInterface());
        System.out.println(playersClasses);
    }

    public void init() {

        try {
            initPlayersClasses();
            loaderMazes();

        } catch (Exception e) {
            allFailureMazes.add(e);
            System.out.println(allFailureMazes + " " +  "Player/Maze not loaded!!!!");
        }
    }

    public boolean validateArguments(String[] args) {

        if (args.length == 0) {
            System.out.println("No arguments in command line");
            return false;
        }

        if (args.length % 2 != 0) {
            System.out.println("Incorrect number of arguments in command line");
            return false;
        }

        if (args.length == 2) {
            System.out.println("Missing arguments in command line");
            return false;
        }
        if (args.length > 6) {
            System.out.println("Too many arguments in command line");
            return false;
        }
        if (!args[0].equals("-mazes_folder")) {
            System.out.println("Incorrect maze folder argument in command line. Should be '-mazes_folder'");
            return false;
        }

        if (!args[2].equals("-players")) {
            System.out.println("Incorrect players argument in command line. Should be '-players'");
            return false;
        }

        if (args.length > 4) {
            try {
                if (!args[4].equals("-threads")) {
                    System.out.println("Incorrect threads argument in command line. Should be '-threads'");
                    return false;
                } else if (!(Integer.parseInt(args[5]) > 0)) {
                    System.out.println("Incorrect number of threads. Should be larger than 0");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("argument should be a valid number larger than 0");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommandLineParser{" +
                "allFailureMazes=" + allFailureMazes +
                '}';
    }
}
