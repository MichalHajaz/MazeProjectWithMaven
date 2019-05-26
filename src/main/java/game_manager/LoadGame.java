package game_manager;

import files.FileParser;
import maze.Maze;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadGame {

    private List<String> errors;

    public void loadGame(String[] args, FileParser fp) {
        errors = new ArrayList<>();
        Maze maze = null;

        File fileInput = new File(args[0]);
        if (!fileInput.isFile() || !fileInput.exists()){
            addError("The argument: " +  fileInput + " cannot be opened");
        }
        else {
            fp.setErrors(errors);
            maze = fp.getMaze(fileInput);
        }

        File fileOut = new File(args[1]);
        if (fileOut.exists()) {
            addError("The argument: " + fileOut + " is incorrect");
        } if(maze != null){
            start(maze, fileOut);
        } else {
            for (String error : errors){
                System.out.println(error);
            }
        }
    }

    public void addError(String error){
        errors.add(error);
    }

    public void start(Maze maze, File fileOut) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut))) {
            PlayerFactory playerFactory = new PlayerFactory();
            GameManager gameManager = new GameManager(maze, playerFactory);
            gameManager.createOutputFile(fileWriter);
            gameManager.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
