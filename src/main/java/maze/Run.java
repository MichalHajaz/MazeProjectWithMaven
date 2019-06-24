package maze;

import files.MazeParser;
import game_manager.LoadGame;

public class Run {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Missing input file");
        }
        if (args.length < 2) {
            System.out.println("Missing output file");
        } else {
            LoadGame loadGame = new LoadGame();
            MazeParser fileParser = new MazeParser();
            loadGame.loadGame(args,fileParser);
        }
    }
}
