import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private static final char PLAYER = MazeChar.PLAYER.getValue();
    private static final char END = MazeChar.WIN.getValue();
    private static final char WALL = MazeChar.WALL.getValue();
    private static final char PASS = MazeChar.PATHWAY.getValue();
    private Maze maze;
    private PlayerInterface player;
    private Location playerLocation;
    private Location endingLocation;
    private int steps;
    private boolean hasWon = false;
    private int bookmarkCount;
    private Map<Location, Integer> bookmarks = new HashMap<>();


    public GameManager(Maze maze, PlayerFactory newPlayer) {
        this.maze = maze;
        player = newPlayer.createNewPlayer(new Location(maze.getRows(), maze.getColumns()), maze.getMaxSteps());
        playerLocation = maze.getPlayerLocation();
        endingLocation = maze.getEndingLocation();
    }

    public Location getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(Location playerLocation) {
        this.playerLocation = playerLocation;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public Map<Location, Integer> getBookmarks() {
        return bookmarks;
    }

    public boolean play() {

        while (!hasWon && steps < maze.getMaxSteps()) {
            maze.setCharAt(maze.getPlayerLocation(), '@');
            maze.printMaze();
            MoveOption move = player.move();
            makeMove(move);
        }
        return hasWon;
    }

    public void makeMove(MoveOption move) {
        Location newLocation = moveOutsideMaze(move);
        char charInLocation = maze.getCharAt(newLocation);
        if (charInLocation == WALL) {
            player.hitWall();
            if (bookmarks.containsKey(playerLocation)) {
                System.out.println("Player hit a bookmark at location: " + playerLocation + " with sequence number: " + bookmarks.get(playerLocation));
                player.hitBookmark(bookmarks.get(playerLocation));
            }
        } else if (newLocation.equals(endingLocation)) {
            System.out.println("Player moved " + move);
            hasWon = true;
        } else if (move.equals(MoveOption.BOOKMARK)) {
            bookmarkCount++;
            bookmarks.put(playerLocation, bookmarkCount);
            System.out.println("Player created a bookmark at location: " + playerLocation + " with sequence number: " + bookmarkCount);
        } else {
            System.out.println("Player moved " + move);
            maze.setCharAt(getPlayerLocation(), move.direction);
            setPlayerLocation(newLocation);
            maze.setCharAt(getPlayerLocation(), PLAYER);
            if (bookmarks.containsKey(playerLocation)) {
                player.hitBookmark(bookmarks.get(playerLocation));
            }
        }
        steps++;
        System.out.println("Steps used so far: " + steps);
    }

    private Location moveOutsideMaze(MoveOption move) {

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
}
