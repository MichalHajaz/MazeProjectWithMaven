package files;

import maze.Maze;

import java.io.File;
import java.util.List;

public interface FileParser {

        Maze getMaze(File fileInput);

        void setErrors(List<String> errors);

}
