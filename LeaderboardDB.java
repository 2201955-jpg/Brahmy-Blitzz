package FINALSS;

import java.util.ArrayList;

public class LeaderboardDB {

    public static void saveScore(String name, int score) {
        DB.save(name, score);
    }

    public static ArrayList<String> getScores() {
        return DB.load();
    }
}