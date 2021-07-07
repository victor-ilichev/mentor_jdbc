package jm.task.core.jdbc.dao;

import org.hibernate.Session;

@FunctionalInterface
public interface TransactionSqlQuery {
    void execute(Session session);
}
