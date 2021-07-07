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
        executeQuery(session -> {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `users` (" +
                    "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`name` varchar(255) NOT NULL," +
                    "`lastName` varchar(255) NOT NULL," +
                    "`age` tinyint NOT NULL," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;")
                    .executeUpdate();
        });
    }

    @Override
    public void dropUsersTable() {
        executeQuery(s -> s.createSQLQuery("DROP TABLE IF EXISTS `users`;").executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        executeQuery(session -> {
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println(" User с именем – " + name + " добавлен в базу данных");
        });
    }

    @Override
    public void removeUserById(long id) {
        executeQuery(session -> {
            if (session.get(User.class, id) != null) {
                session.delete(session.get(User.class, id));
            }
        });
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        List<User> users = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User");
            users = (List<User>) query.list();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw exception;
            }
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        executeQuery(s -> s.createQuery("DELETE FROM User").executeUpdate());
    }

    private void executeQuery(TransactionSqlQuery transactionSqlQuery) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            transactionSqlQuery.execute(session);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw exception;
            }
        } finally {
            session.close();
        }
    }
}
