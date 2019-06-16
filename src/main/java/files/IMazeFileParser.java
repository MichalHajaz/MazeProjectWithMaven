package files;

import maze.Maze;

import java.io.File;
import java.util.List;

public interface IMazeFileParser {

        Maze getMaze(File fileInput);

        void setErrors(List<String> errors);

}
