package maze;

public enum MoveOption {

    UP(-1, 0, 'U'), RIGHT(0, 1, 'R'), DOWN(1, 0, 'D'), LEFT(0, -1, 'L'), BOOKMARK('B');

    int dir_y;
    int dir_x;
    char direction;

    MoveOption(char direction) {
        this.direction = direction;
    }

    MoveOption(int dir_y, int dir_x, char direction) {
        this.dir_y = dir_y;
        this.dir_x = dir_x;
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

}
