public enum MoveOption {

    UP(-1, 0, '↑'), RIGHT(0, 1, '→'), DOWN(1, 0, '↓'), LEFT(0, -1, '←'), BOOKMARK;

    int dir_y;
    int dir_x;
    char direction;

    MoveOption() {
    }

    MoveOption(int dir_y, int dir_x, char direction) {
        this.dir_y = dir_y;
        this.dir_x = dir_x;
        this.direction = direction;
    }

    public int getDir_x() {
        return dir_x;
    }

    public int getDir_y() {
        return dir_y;
    }

    public char getDirection() {
        return direction;
    }

}
