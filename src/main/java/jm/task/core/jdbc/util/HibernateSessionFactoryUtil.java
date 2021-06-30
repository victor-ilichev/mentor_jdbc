package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();

                properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mentor_jdbc?autoReconnect=true&useSSL=false");
                //properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.setProperty(Environment.USER, "mentor");
                properties.setProperty(Environment.PASS, "mu_password");
                //properties.setProperty(Environment.DRIVER, "org.mysql.Driver");
                //properties.setProperty(Environment.SHOW_SQL, true); //If you wish to see the generated sql query

                Configuration configuration = new Configuration();//.configure();
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


//        SessionFactory sessionFactory = new Configuration().addProperties(properties).buildSessionFactory();
        return sessionFactory;
    }
}
