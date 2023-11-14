package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getMySQLConnection();
    private Statement statement;

    {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement.executeUpdate("CREATE TABLE USERS (" +
                    "id INTEGER UNIQUE NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age TINYINT)");
        } catch (SQLException ignored) {
        }
    }

    public void dropUsersTable() {
        try {
            statement.executeUpdate("DROP TABLE USERS");
        } catch (SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement.executeUpdate("INSERT INTO USERS (name, lastname, age)" +
                    "VALUES (\"" + name + "\",\"" + lastName + "\" ,\"" + age + "\")");
        } catch (SQLException ignored) {
        }
    }

    public void removeUserById(long id) {
        try {
            statement.executeUpdate("DELETE from USER where id =" + id);
        } catch (SQLException ignored) {
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test.users");
            while (resultSet.next()) {
                result.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),resultSet.getByte("Age")));
                result.get(result.size() - 1).setId(resultSet.getLong("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("TRUNCATE TABLE USERS");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
