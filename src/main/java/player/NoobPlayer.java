package player;

import maze.MoveOption;

import java.util.Random;

public class NoobPlayer implements IPlayer {


    private boolean hitWall = false;
    private MoveOption nextMove;

    public MoveOption getNextMove() {
        return nextMove;
    }

    @Override
    public void hitWall() {
        hitWall = true;
    }

    @Override
    public void hitBookmark(int seq) {
        //System.out.println("You hit a Bookmark!");
    }

    @Override
    public MoveOption move() {
        nextMove = randomMove();
        hitWall = false;
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

    public boolean isHitWall() {
        return hitWall;
    }

}
