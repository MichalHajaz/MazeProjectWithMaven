import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IntermediatePlayer implements PlayerInterface {

    private Map<Integer, ArrayList<MoveOption>> bookmarks = new HashMap<>();
    private MoveOption nextMove;
    private int sequenceNumber = 0;
    private boolean hitWall = false;

    @Override
    public void hitWall() {
        System.out.println("You have hit a Wall!");
        hitWall = true;
    }

    private void addBookmark(int sequence) {
        ArrayList<MoveOption> movesList;
        if (bookmarks.isEmpty() || bookmarks.containsKey(sequence)) {
            movesList = new ArrayList<>();
            System.out.println("You created a bookmark in sequence " + sequence);
        } else {
            movesList = bookmarks.get(sequence);
        }
        if (hitWall && !movesList.contains(nextMove)) {
            movesList.add(nextMove);
            System.out.println("You have added " + nextMove + " in bookmark sequence " + sequence);
        }
        System.out.println("Unrecommended moves in bookmark sequence" + sequence + " are: " + movesList);
        bookmarks.put(sequence, movesList);
    }

    @Override
    public MoveOption move() {
        if (hitWall) {
            sequenceNumber++;
            addBookmark(sequenceNumber);
            nextMove = MoveOption.BOOKMARK;
        } else {
            nextMove = smartMove();
        }
        return nextMove;
    }

    private MoveOption smartMove() {
        if (hitWall || nextMove.equals(MoveOption.BOOKMARK)) {
            ArrayList<MoveOption> movesList = new ArrayList<>();

            for (MoveOption moveOption : MoveOption.values()) {
                if (!bookmarks.get(sequenceNumber).contains(moveOption) && !moveOption.equals(MoveOption.BOOKMARK)) {
                    movesList.add(moveOption);
                }
            }
            MoveOption[] moveOptions = movesList.toArray(new MoveOption[0]);
            System.out.println("You have these moves to choose from: " + movesList);
            return moveOptions[new Random().nextInt(moveOptions.length - 1)];
        }
        return new MoveOption[]{MoveOption.LEFT, MoveOption.RIGHT, MoveOption.UP, MoveOption.DOWN}[new Random().nextInt(MoveOption.values().length - 1)];
    }

    public MoveOption getNextMove() {
        return nextMove;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public boolean isHitWall() {
        return hitWall;
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("You have hit a Bookmark!");
        addBookmark(seq);


    }

}
