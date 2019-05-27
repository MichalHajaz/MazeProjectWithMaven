package player;

import maze.MoveOption;

public interface PlayerInterface {

    MoveOption move();

    void hitWall();

    void hitBookmark(int seq);
}
