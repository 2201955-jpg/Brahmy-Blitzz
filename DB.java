package FINALSS;

import java.io.*;
import java.util.*;

public class DB {

    private static final String FILE_NAME = "leaderboard.txt";

    public static void save(String name, int score) {

        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(name + "," + score);
            bw.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> load() {

        ArrayList<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length == 2) {
                    list.add(parts[0] + " - " + parts[1]);
                }
            }

        } catch (Exception e) {
            // file may not exist yet
        }

        return list;
    }
}