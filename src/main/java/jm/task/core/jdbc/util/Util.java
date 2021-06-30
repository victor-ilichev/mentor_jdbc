package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();

                properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mentor_jdbc?autoReconnect=true&useSSL=false");
                properties.setProperty(Environment.USER, "mentor");
                properties.setProperty(Environment.PASS, "mu_password");

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build()
                        ;

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }

        return sessionFactory;
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
            String password) throws SQLException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";

        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
