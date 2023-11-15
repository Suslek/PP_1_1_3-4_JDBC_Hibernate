package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE USERS (" +
                    "id INTEGER UNIQUE NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age TINYINT)");
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE USERS");
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getMySQLConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO USERS (name, lastName, age) Values (?,?,?)");
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE from USER where id = ?")) {
            preparedStatement.setLong(1, id);
        } catch (SQLException ignored) {
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test.users")
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
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

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE USERS");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
