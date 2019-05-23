public class Maze {

    private int maxSteps;
    private int rows;
    private int columns;
    private char[][] maze = new char[rows][columns];

    public Maze(char[][] maze, int maxSteps) {
        this.maze = maze;
        this.maxSteps = maxSteps;
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

    public void setCharAt(Location location, char character) {
        maze[location.getRowLocation()][location.getColLocation()] = character;
    }

}
