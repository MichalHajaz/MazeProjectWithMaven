package maze;

public enum MazeChar {

    PLAYER('@'),
    WIN('$'),
    WALL('#'),
    PATHWAY(' ');

    private char value;

    public char getValue() {
        return this.value;
    }

    MazeChar(char value) {
        this.value = value;
    }


}
