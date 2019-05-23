public class Main {

    public static void main(String[] args) {
        char[][] theMaze = {
                {'#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' '},
                {'#', '@', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
                {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
                {'#', ' ', ' ', ' ', '#', ' ', ' ', '$', ' ', '#'},
                {' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', ' '}

        };
        Maze maze = new Maze(theMaze, 10);
        PlayerFactory player = new PlayerFactory();
        GameManager gameManager = new GameManager(maze, player);
        if (gameManager.play()){
            System.out.println("You have won the maze in " + gameManager.getSteps() + " steps!!!");
        }else {
            System.out.println("You didn't complete the maze in the maximum given amount of steps: " + maze.getMaxSteps());
        }
    }
}
