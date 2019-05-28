import game_manager.GameManager;
import maze.Maze;
import maze.MoveOption;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import player.NoobPlayer;
import player.PlayerFactory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoobPlayerTests {


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
    public void hitWallTest(){

        gameManager.makeMove(MoveOption.UP);
        Mockito.doAnswer((answer) -> {return null;}).when(player).hitWall();
        verify(player, Mockito.times(1)).hitWall();

    }

    public void testRandomMove(){

        gameManager.play();

    }

    public void hitBookmarks(){



    }
}
