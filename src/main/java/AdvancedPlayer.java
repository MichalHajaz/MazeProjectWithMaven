import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AdvancedPlayer implements PlayerInterface {

    private Map<Integer, ArrayList<MoveOption>> bookmarks = new HashMap<>();
    private int sequenceNumber = 0;
    private MoveOption lastMove;
    private boolean isBookmark = false;
    private boolean hitBookmark = false;

    public Map<Integer, ArrayList<MoveOption>> getBookmarks() {
        return bookmarks;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public MoveOption getLastMove() {
        return lastMove;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public boolean getHitBookmark() {
        return hitBookmark;
    }

    @Override
    public MoveOption move() {
        if (isBookmark) {
            isBookmark = false;
            sequenceNumber++;
            bookmarkCheck(sequenceNumber);
            lastMove = MoveOption.BOOKMARK;
        } else {
            lastMove = moveSelection();
            isBookmark = true;
        }
        return lastMove;
    }

    @Override
    public void hitWall() {
        System.out.println("You have hit Wall");
        isBookmark = true;
    }

    @Override
    public void hitBookmark(int seq) {
        hitBookmark = true;
        bookmarkCheck(seq);
        System.out.println("You have hit Bookmark");
    }

    private void bookmarkCheck(int sequence) {
        ArrayList<MoveOption> moves;
        if (bookmarks.isEmpty() || !bookmarks.containsKey(sequence)) {
            moves = new ArrayList<>();
            moves.add(lastMove);
        } else {
            moves = bookmarks.get(sequence);
            if (!hitBookmark) {
                moves.add(lastMove);
            } else {
                switch (lastMove) {
                    case UP:
                        moves.add(MoveOption.DOWN);
                        break;
                    case DOWN:
                        moves.add(MoveOption.UP);
                        break;
                    case RIGHT:
                        moves.add(MoveOption.LEFT);
                        break;
                    case LEFT:
                        moves.add(MoveOption.RIGHT);
                        break;
                    default:
                        moves.add(lastMove);
                }
            }
        }
        bookmarks.put(sequence, moves);
        System.out.println("You have added a bookmark");
    }

    private MoveOption moveSelection(){
        if (lastMove != null && lastMove.equals(MoveOption.BOOKMARK)) {
            ArrayList<MoveOption> moves = new ArrayList<>();
            for (MoveOption move : MoveOption.values()) {
                if (!bookmarks.get(sequenceNumber).contains(move)) {
                    moves.add(move);
                }
            }
            MoveOption[] movesArray = moves.toArray(new MoveOption[0]);
            return movesArray[new Random().nextInt(movesArray.length - 1)];
        }
        return new MoveOption[]{MoveOption.LEFT, MoveOption.RIGHT, MoveOption.UP, MoveOption.DOWN}[new Random().nextInt(MoveOption.values().length-1)];
    }
}
