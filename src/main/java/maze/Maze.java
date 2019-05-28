package maze;

public class Maze {

    private int maxSteps;
    private int rows;
    private int columns;
    private char[][] maze = new char[rows][columns];


    public Maze(char[][] maze, int maxSteps) {
        this.maze = maze;
        this.maxSteps = maxSteps;
    }

    public Maze(){

    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public char[][] getMaze() {
        return maze;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    public Location getPlayerLocation() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == '@') {
                    return new Location(row, col);
                }
            }
        }
        return null;
    }

    public Location getEndingLocation() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == '$') {
                    return new Location(row, col);
                }
            }
        }
        return null;
    }

    public void printMaze() {

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {

                System.out.print(maze[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("=====================================");
    }

    public char getCharAt(Location location) {
        return maze[location.getRowLocation()][location.getColLocation()];
    }
}
