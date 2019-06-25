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

    public int getDir_y() { return dir_y; }

    public void setDir_y(int dir_y) { this.dir_y = dir_y; }

    public int getDir_x() { return dir_x; }

    public void setDir_x(int dir_x) { this.dir_x = dir_x; }

    public void setDirection(char direction) { this.direction = direction; }

    public char getDirection() {
        return direction;
    }

}
