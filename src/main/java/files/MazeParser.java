package files;

import maze.Maze;
import maze.MazeChar;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MazeParser {

    private static final char PLAYER = MazeChar.PLAYER.getValue();
    private static final char WIN = MazeChar.WIN.getValue();
    private static final char WALL = MazeChar.WALL.getValue();
    private static final char PATHWAY = MazeChar.PATHWAY.getValue();
    private static final int MIN_MAZE_SIZE = 5;
    private static final int MAX_STEPS_LINE = 2;
    private static final int ROWS_LINE = 3;
    private static final int COLUMNS_LINE = 4;

    private ArrayList<String> mazeResult = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    private static String addCols(String str, int remain) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < remain; i++) {
            sb.append(PATHWAY);
        }
        return sb.toString();
    }

    private static String addRows(int cols) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            row.append(PATHWAY);
        }
        return row.toString();
    }

    public Maze getMaze(File fileInput) {
        Maze maze = null;
        readFile(fileInput);

        if (mazeResult.size() < MIN_MAZE_SIZE) {
            errors.add("Insufficient size. Maze cannot be created");
            return null;
        }


        String name = mazeResult.get(0);
        int maxSteps = lineNumber("MaxSteps", MAX_STEPS_LINE);
        int rows = lineNumber("Rows", ROWS_LINE);
        int cols = lineNumber("Cols", COLUMNS_LINE);

        boolean isMaxStepsValid = isMaxStepsValid(maxSteps);
        boolean isRowsAndColsValid = isRowsAndColsValid(rows, cols);
        boolean isMazeValid = isMazeValid();

        if (isMaxStepsValid && isRowsAndColsValid && isMazeValid) {
            maze = new Maze();
            maze.setMaxSteps(maxSteps);
            maze.setRows(rows);
            maze.setColumns(cols);
            maze.setMaze(generateMaze(rows, cols));
            maze.setName(name);
        } else {
            errors.add(("Invalid maze data. Maze cannot be created"));
        }

        return maze;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    private void readFile(File fileInput) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    mazeResult.add(line);
                }
            }
        }
        catch(FileNotFoundException e){
            errors.add(("File not found. Exception: " + e));
        } catch(IOException e){
            errors.add(("Reading from file failed: " + e));
        }
    }

    private int lineNumber(String key, int lineNumber) {
        int index = lineNumber - 1;
        if (index < mazeResult.size()) {
            String line = mazeResult.get(index).trim();
            String[] strs = line.split("=");
            String num = strs[1].trim();
            if (strs.length != 2 || !strs[0].trim().equals(key) || !num.matches("[0-9]+")) {
                errors.add(("Bad maze file header: expected in line " + lineNumber + "; " + key + " = <num>" + "\n" + ". Actual: " + line));
                return -1;
            }
            try {
                return Integer.parseInt(num);
            } catch (NumberFormatException e) {
                errors.add(("Invalid number " + num + " in line " + lineNumber));
            }
        }
        return -1;
    }

    private boolean isMazeValid() {
        boolean mazeValid = false;
        boolean charValid = true;
        int countPlayerChar = 0;
        int countEndChar = 0;

        for (int i = 4; i < mazeResult.size(); i++) {
            String line = mazeResult.get(i);

            for (int j = 0; j < line.length(); j++) {
                char mazeChar = line.charAt(j);

                if (mazeChar == PLAYER) {
                    countPlayerChar++;
                } else if (mazeChar == WIN) {
                    countEndChar++;
                } else if (mazeChar != WALL && mazeChar != PATHWAY) {
                    errors.add(("Wrong character in maze: " + mazeChar + " in row " + (i + 1) + ", col " + (j + 1)));
                    charValid = false;
                }
            }
        }

        if (isCharCountValid(countPlayerChar, PLAYER) && isCharCountValid(countEndChar, WIN) && charValid) {
            mazeValid = true;
        }
        return mazeValid;
    }

    private boolean isCharCountValid(int count, char mazeChar) {
        boolean isValid = false;
        if (count == 0) {
            errors.add(("Missing " + mazeChar + " in maze"));
        } else if (count > 1) {
            errors.add(("More than one " + mazeChar + " in maze"));

        } else {
            isValid = true;
        }
        return isValid;
    }

    private char[][] generateMaze(int rows, int cols) {
        char[][] mazeMap = new char[rows][cols];
        ArrayList<String> mazeArray = new ArrayList<>();
        for (int i = 4; i < mazeResult.size(); i++) {
            mazeArray.add(mazeResult.get(i));
        }

        for (int i = 0; i < mazeArray.size(); i++) {
            String str = mazeArray.get(i);
            if (str.length() < cols) {
                int remain = cols - str.length();
                mazeArray.set(i, addCols(str, remain));
            }
        }

        if (rows > mazeArray.size()) {
            int rowsToAdd = rows - mazeArray.size();
            for (int i = 0; i < rowsToAdd; i++) {
                mazeArray.add(addRows(cols));
            }
        }

        for (int row = 0; row < mazeMap.length; row++) {
            for (int col = 0; col < mazeMap[row].length; col++) {
                mazeMap[row][col] = mazeArray.get(row).charAt(col);
            }
        }
        return mazeMap;
    }

    private boolean isMaxStepsValid(int steps) {
        if (steps == 0) {
            errors.add(("Bad maze file header: expected in line 2 - MaxSteps bigger than 0 "
                    + "\n" + "got: " + mazeResult.get(1)));
            return false;
        }
        return true;
    }

    private boolean isRowsAndColsValid(int row, int col) {
        if ((row < 1 && col < 2) || (row < 2 && col < 1)) {
            errors.add(("Bad maze file header: expected in lines 3,4 - minimum 1 row and 2 columns or 2 rows and 1 column in a maze "
                    + "\n" + "got: " + mazeResult.get(2) + " " + mazeResult.get(3)));
            return false;
        }
        return true;
    }
}
