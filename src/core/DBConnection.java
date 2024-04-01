package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Verify that the connection port and the database name are compatible with the local db you are trying to access
public class DBConnection {
    private static DBConnection instance = null;
    private Connection connection = null;
    private final String LOCAL_PORT = "5432";
    private final String DB_NAME = "rentacar";
    private final String URL = "jdbc:postgresql://localhost:";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "1234";

    private DBConnection() {
        try {
            this.connection = DriverManager.getConnection(
                    URL + LOCAL_PORT + "/" + DB_NAME,
                    USERNAME,
                    PASSWORD
            );
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()){
                instance = new DBConnection();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

        return instance.getConnection();
    }
}
