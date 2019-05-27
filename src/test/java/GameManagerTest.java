import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameManagerTest {


    private char[][] testMaze = {
            {'#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' '},
            {'#', '@', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {'#', '$', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', '$', ' ', '#'},
            {' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', ' '}

    };



    @Mock
    NoobPlayer player;

    @Mock
    PlayerFactory playerFactory;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp(){

        when(playerFactory.createNewPlayer(maze.getPlayerLocation(),10)).thenReturn(player);
        gameManager = new GameManager(maze,playerFactory);

    }

    Maze maze = new Maze(testMaze, 10);
    GameManager gameManager;



    @Test
    public void moveUpTest() {

        Location up = new Location(-1,0);
        gameManager.makeMove(MoveOption.UP);
        Assert.assertEquals(maze.getPlayerLocation(), up);

    }

    @Test
    public void moveDownTest() {

        Location down = new Location(1,0);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertEquals(maze.getPlayerLocation(), down);

    }

    @Test
    public void moveRightTest() {

        Location right = new Location(0,1);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertEquals(maze.getPlayerLocation(), right);

    }

    @Test
    public void moveLeftTest() {
        Location left = new Location(0, -1);
        gameManager.makeMove(MoveOption.LEFT);
        Assert.assertEquals(maze.getPlayerLocation(), left);

    }


    @Test
    public void testPlayOverSteps() {




    }

    @Test
    public void isHasWonTest() {

        gameManager.makeMove(MoveOption.DOWN);
        gameManager.play();
        Assert.assertTrue(gameManager.isHasWon());
    }

    @Test
    public void moveOutsideTheMazeTest(){


    }


}
