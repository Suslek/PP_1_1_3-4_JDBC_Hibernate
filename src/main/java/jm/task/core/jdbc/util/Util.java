package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String hostName = "localhost";
    private static final String dbName = "test";
    private static final String userName = "root";
    private static final String password = "root";

    private static Connection connection = null;

    public static Connection getMySQLConnection() {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
