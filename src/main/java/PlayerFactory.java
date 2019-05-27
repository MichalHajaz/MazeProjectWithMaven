public class PlayerFactory {

    public PlayerInterface createNewPlayer(Location mazeSize, int maxSteps) {
        if (mazeSize.getColLocation() <= 5 && mazeSize.getRowLocation() <= 5) {
            System.out.println("Playing with a Noob Player");
            return new NoobPlayer();
        } else if (mazeSize.getColLocation() * mazeSize.getRowLocation() >= maxSteps) {
            System.out.println("Playing with a an Intermediate Player");
            return new IntermediatePlayer();
        } else {
            System.out.println("Playing with a an Advanced Player");
            return new AdvancedPlayer();
        }
    }
}