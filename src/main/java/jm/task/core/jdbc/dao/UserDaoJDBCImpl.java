package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        executeQuery("CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                "`name` varchar(255) NOT NULL," +
                "`lastName` varchar(255) NOT NULL," +
                "`age` tinyint NOT NULL," +
                "PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
    }

    public void dropUsersTable() {
        executeQuery("DROP TABLE IF EXISTS users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        executeQuery("INSERT INTO users(`name`, `lastName`, `age`) VALUES ('" + name + "', '" + lastName + "', " + age + ");");
    }

    public void removeUserById(long id) {
        executeQuery("DELETE FROM `users` WHERE id=" + id);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = Util.getMySQLConnection().createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        executeQuery("TRUNCATE TABLE `users`;");
    }

    private void executeQuery(String sql) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = Util.getMySQLConnection().prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
