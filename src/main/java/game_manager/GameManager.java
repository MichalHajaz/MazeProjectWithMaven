package game_manager;

import files.OutputFile;
import maze.Location;
import maze.Maze;
import maze.MazeChar;
import maze.MoveOption;
import player.PlayerFactory;
import player.PlayerInterface;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private static final char PLAYER = MazeChar.PLAYER.getValue();
    private static final char WALL = MazeChar.WALL.getValue();
    private static final char PATHWAY = MazeChar.PATHWAY.getValue();
    private Maze maze;
    private PlayerInterface player;
    private Location playerLocation;
    private Location endingLocation;
    private int steps = 0;
    private boolean hasWon = false;
    private boolean overMaxSteps = false;
    private int bookmarkCount = 0;
    private Map<Location, Integer> bookmarks = new HashMap<>();
    private OutputFile outputFile;


    public GameManager(Maze maze, PlayerFactory newPlayer) {

        this.maze = maze;

        player = newPlayer.createNewPlayer(new Location(maze.getRows(), maze.getColumns()), maze.getMaxSteps());
        playerLocation = maze.getPlayerLocation();
        endingLocation = maze.getEndingLocation();
    }

    public boolean isOverMaxSteps() {
        return overMaxSteps;
    }

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
            System.out.println("You Have Won!");
        } else {
            outputFile.setWin('X');
            overMaxSteps = true;
            System.out.println("You Have Lost!");
            System.out.println("You did not win the maze in the given amount of steps.\n" +
                    "Steps used: " + steps + ";\n" +
                    "Maximum number of steps given: " + maze.getMaxSteps());
        }
        outputFile.printMoves();
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
                    System.out.println("You have hit a bookmark at location: " + playerLocation + " with sequence number: " + bookmarks.get(playerLocation));
                    player.hitBookmark(bookmarks.get(playerLocation));
                }
            } else if (newLocation.equals(endingLocation)) {
                System.out.println("You moved " + move);
                hasWon = true;
            } else if (move.equals(MoveOption.BOOKMARK)) {
                bookmarkCount++;
                bookmarks.put(playerLocation, bookmarkCount);
                System.out.println("You have created a bookmark at location: " + playerLocation + " with sequence number: " + bookmarkCount);
            } else {
                System.out.println("You moved " + move);
                setPlayerLocation(newLocation);
                if (bookmarks.containsKey(playerLocation)) {
                    player.hitBookmark(bookmarks.get(playerLocation));
                }
            }
            steps++;
            System.out.println("Steps used so far: " + steps);
            maze.printMaze();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Move");
        }
    }

    private Location moveDirection(MoveOption move) {

        switch (move) {
            case UP:
                return new Location(Math.floorMod(playerLocation.getRowLocation() - 1, maze.getRows()), playerLocation.getColLocation());
            case DOWN:
                return new Location(Math.floorMod(playerLocation.getRowLocation() + 1, maze.getRows()), playerLocation.getColLocation());
            case LEFT:
                return new Location(playerLocation.getRowLocation(), Math.floorMod(playerLocation.getColLocation() - 1, maze.getColumns()));
            case RIGHT:
                return new Location(playerLocation.getRowLocation(), Math.floorMod(playerLocation.getColLocation() + 1, maze.getColumns()));
            case BOOKMARK:
                return playerLocation;
        }
        throw new IllegalArgumentException("");
    }
}
