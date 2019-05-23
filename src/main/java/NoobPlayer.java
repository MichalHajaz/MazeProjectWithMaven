import java.util.Random;

public class NoobPlayer implements PlayerInterface {


    private boolean hitWall = false;
    private MoveOption nextMove;

    @Override
    public void hitWall() {
        hitWall = true;
        System.out.println("You hit a Wall!");
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("You hit a Bookmark!");
    }

    @Override
    public MoveOption move() {

        nextMove = randomMove();
        return nextMove;
    }


    private MoveOption randomMove() {
        MoveOption[] moveOptions = MoveOption.values();
        Random generator = new Random();
        if (isHitWall()) {
            MoveOption moveOption;
            do {
                moveOption = moveOptions[generator.nextInt(moveOptions.length - 1)];
            }
            while (moveOption.equals(getNextMove()));
            return moveOption;

        }
        return moveOptions[generator.nextInt(moveOptions.length - 1)];
    }

    public MoveOption getNextMove() {
        return nextMove;
    }

    public boolean isHitWall() {
        return hitWall;
    }


}
