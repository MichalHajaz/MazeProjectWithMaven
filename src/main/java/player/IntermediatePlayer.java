package player;

import maze.MoveOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IntermediatePlayer implements PlayerInterface {

    private Map<Integer, ArrayList<MoveOption>> bookmarks = new HashMap<>();
    private MoveOption lastMove;
    private int sequenceNumber = 0;
    private boolean hitWall = false;
    private boolean isBookmark = false;
    private int bookmarkSequence = 0;

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
        if (hitWall && !movesList.contains(lastMove)) {
            movesList.add(lastMove);
            System.out.println("You have added " + lastMove + " in bookmark sequence " + sequence);
        }
        System.out.println("Unrecommended moves in bookmark sequence " + sequence + " are: " + movesList);
        bookmarks.put(sequence, movesList);
    }

    private MoveOption moveSelection(){
        if (hitWall || isBookmark || (lastMove != null && lastMove.equals(MoveOption.BOOKMARK))) {
            ArrayList<MoveOption> moves = new ArrayList<>();
            int seqNumber;
            if (isBookmark) {
                seqNumber = sequenceNumber;
                isBookmark = false;
            } else {
                seqNumber = sequenceNumber;
            }
            for (MoveOption move : MoveOption.values()) {
                if (!bookmarks.get(seqNumber).contains(move) && !move.equals(MoveOption.BOOKMARK)) {
                    moves.add(move);
                }
            }
            MoveOption[] movesArray = moves.toArray(new MoveOption[0]);
            System.out.println("Select from the following options: " + moves);
            return movesArray[new Random().nextInt(movesArray.length - 1)];
        }
        return new MoveOption[]{MoveOption.LEFT, MoveOption.RIGHT, MoveOption.UP, MoveOption.DOWN}[new Random().nextInt(MoveOption.values().length-1)];
    }

    @Override
    public MoveOption move() {
        if (hitWall && !isBookmark) {
            sequenceNumber++;
            bookmarkCheck(sequenceNumber);
            lastMove = MoveOption.BOOKMARK;
        } else {
            lastMove = moveSelection();
        }
        return lastMove;
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("You have hit a Bookmark!");
        addBookmark(seq);
    }

    private void bookmarkCheck(int sequence) {
        ArrayList<MoveOption> moves;
        if (bookmarks.isEmpty() || !bookmarks.containsKey(sequence)) {
            moves = new ArrayList<>();
            System.out.println("You have created a bookmark in sequence: " + sequence);
        } else {
            moves = bookmarks.get(sequence);
            isBookmark = true;
            bookmarkSequence = sequence;
        }
        if (hitWall && !moves.contains(lastMove)) {
            moves.add(lastMove);
            System.out.println("You have added " + lastMove + " at bookmark sequence " + sequence);
        }
        hitWall = false;
        System.out.println("Unrecommended moves in bookmark sequence " + sequence + " are " + moves);
        bookmarks.put(sequence, moves);
    }

}
