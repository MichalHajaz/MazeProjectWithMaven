import game_manager.GameManager;
import maze.Location;
import maze.Maze;
import maze.MoveOption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
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
            {'#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' '},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '@', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {' ', ' ', '$', ' ', '#', '#', '#', '#', '#', ' '}

    };



    @Mock
    NoobPlayer player;

    @Mock
    PlayerFactory playerFactory;

  /*  @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();*/


    @Before
    public void setUp(){
        when(playerFactory.createNewPlayer(maze.getPlayerLocation(),2)).thenReturn(player);
        gameManager = new GameManager(maze,playerFactory);

    }

    Maze maze = new Maze(testMaze, 2);
    GameManager gameManager;



    @Test
    public void moveUpTest() {

        Location expectedLocation = new Location(1,2);
        gameManager.makeMove(MoveOption.UP);
        Assert.assertEquals(expectedLocation, maze.getPlayerLocation());

    }

    @Test
    public void moveDownTest() {

        Location expectedLocation = new Location(3,2);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertEquals(expectedLocation,maze.getPlayerLocation());

    }

    @Test
    public void moveRightTest() {

        Location expectedLocation = new Location(2,3);
        gameManager.makeMove(MoveOption.RIGHT);
        Assert.assertEquals(expectedLocation,maze.getPlayerLocation());

    }

    @Test
    public void moveLeftTest() {
        Location expectedLocation = new Location(2, 1);
        gameManager.makeMove(MoveOption.LEFT);
        Assert.assertEquals(expectedLocation, maze.getPlayerLocation());

    }


    @Test
    public void testPlayOverSteps() {
        gameManager.makeMove(MoveOption.RIGHT);
        gameManager.makeMove(MoveOption.RIGHT);
        gameManager.makeMove(MoveOption.RIGHT);
        Assert.assertFalse(gameManager.play());

    }

    @Test
    public void isHasWonTest() {

        gameManager.makeMove(MoveOption.DOWN);
        gameManager.makeMove(MoveOption.DOWN);
        Assert.assertTrue(gameManager.isHasWon());
    }

    @Test
    public void moveDirectionTest(){


    }


}
