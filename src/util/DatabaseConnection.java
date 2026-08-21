package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MYSQL DRIVER LOADED!");
        } catch (ClassNotFoundException e) {
            System.out.println("MYSQL DRIVER NOT FOUND!");
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/online_quiz_system";
        String username = "root";
        String password = System.getenv("QUIZ_DB_PASSWORD");

        return DriverManager.getConnection(url, username, password);
    }
}