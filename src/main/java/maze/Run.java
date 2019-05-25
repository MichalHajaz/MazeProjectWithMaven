package maze;

import files.FileParser;
import files.InputFile;
import game_manager.GameLoad;

public class Run {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Missing input file");
        }
        if (args.length < 2) {
            System.out.println("Missing output file");
        } else {
            GameLoad gameLoad = new GameLoad();
            FileParser fileParser = new InputFile();
            gameLoad.loadGame(args,fileParser);
        }
    }
}
