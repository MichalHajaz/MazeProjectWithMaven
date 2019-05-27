package files;

import java.io.*;

public class InputFile_Amar {

    public static File PATH_FILE = new File("C:\\Users\\sm930c\\Desktop\\BIQ\\Project\\MazeProjectWithMaven\\src\\main\\resources\\NoobMazeFile.txt");
    public static int MaxSteps;

    public static char[][] linesOfFile(FileReader fr) {

        int col = 0, row = 0;
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        try {
            MaxSteps = Integer.parseInt(br.readLine().replaceAll("[^0-9]", ""));
            System.out.println(MaxSteps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            row = Integer.parseInt(br.readLine().replaceAll("[^0-9]", ""));
            System.out.println(row);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            col = Integer.parseInt(br.readLine().replaceAll("[^0-9]", ""));
            System.out.println(col);
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[][] arr = new char[row][col];
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                arr[i] = line.toCharArray();
                i++;
            }
        } catch (Exception e) {
            System.out.println("error");

        }

        return arr;
    }

    public static FileReader readFile(File file) {
        // TODO Auto-generated method stub
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exists");
            e.printStackTrace();
        }
        return fr;
    }





    synchronized public static void writeToFile(char[][] link) {
        File file = new File(String.valueOf(PATH_FILE));
        try {
            if (file.exists() == false) {
                System.out.println("We had to make a new file.");
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(file, true));
            out.println();
            for (int i = 0; i < link.length; i++) {
                for (int j = 0; j < link[i].length; j++) {
                    out.append(link[i][j]);

                }
                out.println();
            }
            out.append("****************************************");

            out.write(System.getProperty("line.separator"));

            out.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }

    synchronized public static void writeToFile(String link) {
        File file = new File(String.valueOf(PATH_FILE));
        try {
            if (file.exists() == false) {
                System.out.println("We had to make a new file.");
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(file, true));
            out.append(link);
            out.write(System.getProperty("line.separator"));

            out.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }
}
