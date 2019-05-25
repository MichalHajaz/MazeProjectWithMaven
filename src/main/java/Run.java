public class Run {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Missing maze file argument in command line");
        }
        if (args.length < 2) {
            System.out.println("Missing output file argument in command line");
        } else {
            GameLoad gameLoad = new GameLoad();
            FileParser fileParser = new InputFile();
            gameLoad.loadGame(args,fileParser);
        }
    }
}
