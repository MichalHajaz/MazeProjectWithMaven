package maze;

import files.IMazeFileParser;
import files.MazeFileParser;
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
            MazeFileParser fileParser = new MazeFileParser();
            loadGame.loadGame(args,fileParser);
        }
    }
}
