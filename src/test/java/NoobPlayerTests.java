import game_manager.GameManager;
import maze.Maze;
import maze.MoveOption;
import org.junit.Assert;
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



    public void setUp(int maxSteps){
        when(playerFactory.createNewPlayer(maze.getPlayerLocation(),maxSteps)).thenReturn(player);
        gameManager = new GameManager(maze,playerFactory);

    }

    Maze maze = new Maze();
    GameManager gameManager;

    @Test
    public void hitWallTest(){

        setUp(5);
        gameManager.makeMove(MoveOption.UP);
        Assert.assertTrue(player.isHitWall());
    }

    @Test
    public void testRandomMove(){

        gameManager.play();




    }

}
