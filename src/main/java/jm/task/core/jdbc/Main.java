package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        // Создание таблицы User(ов)
        service.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        service.saveUser("John", "Doe", (byte) 21);
        service.saveUser("John", "Malkovich", (byte) 22);
        service.saveUser("Johny", "Depp", (byte) 23);

        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        for (User user: service.getAllUsers()) {
            System.out.println(user);
        }

        // Очистка таблицы User(ов)
        service.cleanUsersTable();

        // Удаление таблицы
        service.dropUsersTable();
    }
}
