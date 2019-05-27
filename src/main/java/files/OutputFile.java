package files;

import maze.MoveOption;

import java.io.BufferedWriter;
import java.io.IOException;

public class OutputFile {

    private StringBuilder moves = new StringBuilder();
    private BufferedWriter br;

    public OutputFile(BufferedWriter file) {
        this.br = file;
    }

    public void updateMoves(MoveOption moveOption) {
        moves.append(moveOption.getDirection());
        moves.append('\n');
    }

    public void setWin(char c) {
        moves.append(c);
    }

    public void exportToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(br)) {
            writer.write(moves.toString());
        } catch (IOException e) {
            throw new IOException(e);
        }

    }

    public void printMoves() {
        System.out.print(moves.toString());
    }
}
