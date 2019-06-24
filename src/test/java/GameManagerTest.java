
import game_manager.GameManager;
import maze.Location;
import maze.Maze;
import maze.MoveOption;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import player.NoobPlayer;
import player.PlayerFactory;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameManagerTest {


    private char[][] testMaze = {
            {'#', '#', '#', ' ', '#', ' ', ' ', ' ', ' ', ' '},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '@', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {' ', ' ', '$', ' ', '#', '#', '#', '#', '#', ' '}

    };



    @Mock
    NoobPlayer player;

    @Mock
    PlayerFactory playerFactory;



    public void setUp(int maxSteps){
        when(playerFactory.createNewPlayer(maze.getPlayerLocation(),maxSteps)).thenReturn(player);
        gameManager = new GameManager(maze,playerFactory);

    }

    Maze maze = new Maze();
    GameManager gameManager;




    @Test
    public void moveUpTest() {

        setUp(2);
        Location expectedLocation = new Location(1,2);
        gameManager.makeMove(MoveOption.UP);
        Assert.assertEquals(expectedLocation, maze.getPlayerLocation());

    }

    @Test
    public void moveDownTest() {

        setUp(2);
        Location expectedLocation = new Location(3,2);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertEquals(expectedLocation,maze.getPlayerLocation());

    }

    @Test
    public void moveRightTest() {

        setUp(2);
        Location expectedLocation = new Location(2,3);
        gameManager.makeMove(MoveOption.RIGHT);
        Assert.assertEquals(expectedLocation,maze.getPlayerLocation());

    }

    @Test
    public void moveLeftTest() {

        setUp(2);
        Location expectedLocation = new Location(2, 1);
        gameManager.makeMove(MoveOption.LEFT);
        Assert.assertEquals(expectedLocation, maze.getPlayerLocation());

    }


    @Test
    public void testPlayOverSteps() {

        setUp(1);
        gameManager.play();
        gameManager.play();
        Assert.assertTrue(gameManager.isOverMaxSteps());


    }

    @Test
    public void isHasWonTest() {

        setUp(2);
        gameManager.makeMove(MoveOption.DOWN);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertTrue(gameManager.isHasWon());
    }

    @Test
    public void moveDirectionTest(){

        setUp(10);

        Location expectedLocation = new Location(0, 3);

        gameManager.makeMove(MoveOption.RIGHT);
        gameManager.makeMove(MoveOption.DOWN);
        gameManager.makeMove(MoveOption.DOWN);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertEquals(expectedLocation,maze.getPlayerLocation());


    }


}
