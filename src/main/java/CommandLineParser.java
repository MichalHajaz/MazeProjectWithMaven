import files.MazeParser;
import maze.Maze;
import org.reflections.Reflections;
import player.IPlayer;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class CommandLineParser {

    private String mazeFolder;
    private String playerPackage;
    private Set<Class<? extends IPlayer>> playersClasses;
    private ArrayList <Maze> mazes = new ArrayList<>();


    public CommandLineParser(String mazeFolder, String playerP) {
        this.mazeFolder = mazeFolder;
        playerPackage = playerP;
    }

    public String getMazeFolder() {
        return mazeFolder;
    }

    public String getPlayerPackage() {
        return playerPackage;
    }

    public Set<Class<? extends IPlayer>> getPlayers() {
        return playersClasses;
    }

    public ArrayList<Maze> getMazes() {
        return mazes;
    }

    private void loaderMazes() {

        File file = new File(mazeFolder);

        for (File fileName : Objects.requireNonNull(file.listFiles((dir, name) -> name.endsWith(".txt")))){
            MazeParser mazeParser = new MazeParser();
            Maze maze = mazeParser.getMaze(fileName);
            if (maze != null) {
                mazes.add(maze);
            }
        }
    }

    private void initPlayersClasses() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{

        Reflections reflections = new Reflections("player");
        playersClasses = reflections.getSubTypesOf(IPlayer.class);


    }



    public void init()throws Exception{

        try {
            initPlayersClasses();
            loaderMazes();

        }
        catch (Exception e){
            throw new Exception("Player/Maze not loaded!!!!",e);


        }

    }


}
