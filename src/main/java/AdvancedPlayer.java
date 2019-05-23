public class AdvancedPlayer implements PlayerInterface {

    @Override
    public MoveOption move() {
        return null;
    }

    @Override
    public void hitWall() {
        System.out.println("You have hit a Wall!");
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("You have hit a Bookmark!");
    }
}
