package FINALSS;

import java.sql.*;

public class DB {

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:game.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}