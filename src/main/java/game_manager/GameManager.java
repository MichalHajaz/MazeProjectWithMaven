package game_manager;

import files.OutputFile;
import maze.Location;
import maze.Maze;
import maze.MazeChar;
import maze.MoveOption;
import player.PlayerFactory;
import player.IPlayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager implements Runnable {

    private static final char PLAYER = MazeChar.PLAYER.getValue();
    private static final char WALL = MazeChar.WALL.getValue();
    private static final char PATHWAY = MazeChar.PATHWAY.getValue();
    private Maze maze;
    private IPlayer player;
    private Location playerLocation;
    private Location endingLocation;
    private int steps = 0;
    private boolean hasWon = false;
    private boolean overMaxSteps = false;
    private int bookmarkCount = 0;
    private Map<Location, Integer> bookmarks = new HashMap<>();
    private OutputFile outputFile;
    private int gameResult = 0;


    public GameManager(Maze maze, IPlayer player) {
        this.maze = maze;
        this.player = player;
        playerLocation = maze.getPlayerLocation();
        endingLocation = maze.getEndingLocation();
    }

    public GameManager(Maze maze, PlayerFactory newPlayer) {

        try {
            this.maze = maze;
        } catch (Exception e) {
            e.printStackTrace();
        }
        player = newPlayer.createNewPlayer(new Location(maze.getRows(), maze.getColumns()), maze.getMaxSteps());
        playerLocation = maze.getPlayerLocation();
        endingLocation = maze.getEndingLocation();
    }

    public Maze getMaze() { return maze; }

    public IPlayer getPlayer() { return player; }

    public int getGameResult() { return gameResult; }

    public boolean isOverMaxSteps() { return overMaxSteps; }

    public boolean isHasWon() {
        return hasWon;
    }


    private void setPlayerLocation(Location nextLocation) {
        maze.getMaze()[playerLocation.getRowLocation()][playerLocation.getColLocation()] = PATHWAY;
        maze.getMaze()[nextLocation.getRowLocation()][nextLocation.getColLocation()] = PLAYER;
        this.playerLocation = nextLocation;
    }

    public void createOutputFile(BufferedWriter outputFile) {
        this.outputFile = new OutputFile(outputFile);
    }

    public void play() {

        while (!hasWon && steps < maze.getMaxSteps()) {
            MoveOption move = player.move();
            makeMove(move);
            outputFile.updateMoves(move);
        }
        if (hasWon) {
            outputFile.setWin('!');
            //System.out.println("You Have Won!");
            gameResult = steps;

        } else {
            outputFile.setWin('X');
            overMaxSteps = true;
            gameResult = -1;
        }
        try {
            outputFile.exportToFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not export to file");
        }

    }


    public void makeMove(MoveOption move) {
        Location newLocation = moveDirection(move);
        try {
            char charInLocation = maze.getCharAt(newLocation);
            if (charInLocation == WALL) {
                player.hitWall();
                if (bookmarks.containsKey(playerLocation)) {
                    player.hitBookmark(bookmarks.get(playerLocation));
                }
            } else if (newLocation.equals(endingLocation)) {
                hasWon = true;
            } else if (move.equals(MoveOption.BOOKMARK)) {
                bookmarkCount++;
                bookmarks.put(playerLocation, bookmarkCount);
            } else {
                setPlayerLocation(newLocation);
                if (bookmarks.containsKey(playerLocation)) {
                    player.hitBookmark(bookmarks.get(playerLocation));
                }
            }
            steps++;
        } catch (Exception e) {
            System.out.println("Move is outside maze");
        }
    }

    private Location moveDirection(MoveOption move) {

        if ((playerLocation.getRowLocation() + move.getDir_y()) < 0) {
            return new Location(maze.getMaze().length - 1, playerLocation.getColLocation());
        } else if ((playerLocation.getRowLocation() + move.getDir_y()) > maze.getMaze().length - 1) {
            return new Location(0, playerLocation.getColLocation());
        } else if ((playerLocation.getColLocation() + move.getDir_x()) < 0) {
            return new Location(playerLocation.getRowLocation(), maze.getMaze()[0].length - 1);
        } else if ((playerLocation.getColLocation() + move.getDir_x()) > maze.getMaze()[0].length - 1) {
            return new Location(playerLocation.getRowLocation(), 0);
        } else
            return new Location(playerLocation.getRowLocation() + move.getDir_y(), playerLocation.getColLocation() + move.getDir_x());
    }

    @Override
    public void run() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.toString() + ".txt"))) {
            createOutputFile(fileWriter);
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
