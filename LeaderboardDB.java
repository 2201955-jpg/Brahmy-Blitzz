package FINALSS;

import java.sql.*;

public class LeaderboardDB {

    public static void saveScore(int score) {

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:game.db")) {

            Statement st = conn.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS scores(score INTEGER)");

            PreparedStatement ps =
                    conn.prepareStatement("INSERT INTO scores(score) VALUES(?)");

            ps.setInt(1, score);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}