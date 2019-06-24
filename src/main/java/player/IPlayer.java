package player;

import maze.MoveOption;

public interface IPlayer {

    MoveOption move();

    void hitWall();

    void hitBookmark(int seq);
}
