package player;

import maze.MoveOption;

import java.util.*;

public class AdvancedPlayer implements IPlayer {

    private Map<Integer, ArrayList<MoveOption>> bookmarks = new HashMap<>();
    private int sequenceNumber = 0;
    private MoveOption lastMove;
    private boolean isBookmark = false;
    private boolean hitBookmark = false;


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
        isBookmark = true;
    }

    @Override
    public void hitBookmark(int seq) {
        hitBookmark = true;
        bookmarkCheck(seq);
    }


    /**
     * @param sequence This method verifies whether there is a bookmark in this location and adds bookmarks to bookmarks map.
     *                 If no, add a bookmark to the map of bookmarks with the relevant sequence number and the relevant move option.
     *                 If yes, check whether the move option appears in the relevant sequence number.
     *                 If no, add the move to the list of move options.
     *                 If yes, check whether the player is already on a bookmarked location.
     *                 If no, add the last move to the list of move options.
     *                 If yes, check which move was made and add it to the list of move options.
     *                 Add the relevant sequence and its list of move options to the bookmarks map.
     */


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
    }

    private MoveOption moveSelection() {
        if (lastMove != null && lastMove.equals(MoveOption.BOOKMARK)) {
            ArrayList<MoveOption> moves = getMoveOption();
            for (MoveOption move : MoveOption.values()) {

                if (moves.contains(move)) {
                    moves.add(move);
                }

            }
            MoveOption[] movesArray = moves.toArray(new MoveOption[0]);
            return movesArray[new Random().nextInt(movesArray.length - 1)];
        }

        return new MoveOption[]{MoveOption.LEFT, MoveOption.RIGHT, MoveOption.UP, MoveOption.DOWN}[new Random().nextInt(MoveOption.values().length - 1)];
    }

    private ArrayList<MoveOption> getMoveOption() {

        ArrayList<MoveOption> movesOptions = bookmarks.get(sequenceNumber);
        if (movesOptions != null) {
            return movesOptions;
        }
        movesOptions = new ArrayList<>();
        bookmarks.put(sequenceNumber, movesOptions);

        return movesOptions;
    }
}
