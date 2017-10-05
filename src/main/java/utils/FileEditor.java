package utils;

import java.io.*;

public class FileEditor {
    public static void saveArrayToFile(double[][] answers, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(String.valueOf(answers.length));
            bw.newLine();
            bw.write(String.valueOf(answers[0].length));
            bw.newLine();
            for (int i = 0; i < answers.length; i++) {
                for (int j = 0; j < answers[0].length; j++) {
                    bw.write(String.valueOf(answers[i][j]));
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] loadArrayFromFile(String path) {
        double[][] arr = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int rows = Integer.parseInt(br.readLine());
            int cols = Integer.parseInt(br.readLine());
            arr = new double[rows][cols];
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    arr[i][j] = Double.parseDouble(br.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
