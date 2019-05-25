public interface PlayerInterface {

    MoveOption move();

    void hitWall();

    void hitBookmark(int seq);
}
