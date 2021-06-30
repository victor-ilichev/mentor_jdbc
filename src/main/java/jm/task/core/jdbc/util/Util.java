package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connection;

    public static SessionFactory getSessionFactory() {
//        Properties properties = new Properties();
//
//        properties.setProperty("hibernate.connection.url", "jdbc:mysql://<your-host>:<your-port>/<your-dbname>");
//        properties.setProperty("dialect", "org.hibernate.dialect.PostgresSQL");
//        properties.setProperty("hibernate.connection.username", "<your-user>");
//        properties.setProperty("hibernate.connection.password", "<your-password>");
//        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        //properties.setProperty("show_sql", true); //If you wish to see the generated sql query
//
//        SessionFactory sessionFactory = new Configuration().addProperties(properties).buildSessionFactory();
        return HibernateSessionFactoryUtil.getSessionFactory();
    }

    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "mentor_jdbc";
        String userName = "mentor";
        String password = "mu_password";

        if (null == connection) {
            connection = getMySQLConnection(hostName, dbName, userName, password);
        }

        return connection;
    }

    public static Connection getMySQLConnection(
            String hostName,
            String dbName,
            String userName,
            String password) throws SQLException, ClassNotFoundException {
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        // Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";

        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
