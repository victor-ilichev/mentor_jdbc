package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                "`name` varchar(255) NOT NULL," +
                "`lastName` varchar(255) NOT NULL," +
                "`age` tinyint NOT NULL," +
                "PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;")
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS `users`;").executeUpdate();
        t.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        transaction.commit();
        System.out.println(" User с именем – " + name + " добавлен в базу данных");
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        if (session.get(User.class, id) != null) {
            session.delete(session.get(User.class, id));
        }
        tx1.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User");
        List<User> users = (List<User>) query.list();
        session.close();

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE `users`;").executeUpdate();
        t.commit();
        session.close();
//        Session session = Util.getSessionFactory().openSession();
//        Transaction t1 = session.beginTransaction();
//        session.createQuery("DELETE FROM User").executeUpdate(); // HQL запрос
////        for (User user : getAllUsers()) {
////            session.delete(user); // CRITERIA API
////        }
//        t1.commit();
//        session.close();
    }
}
