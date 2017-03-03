package Server;

import java.sql.*;

import static Server.ConstantMeneger.*;

/**
 * Класс отвечает за свясь с базой данных
 */
public class SQLHandler {
    private static Connection conn;                     // соединение с базой данных
    private static PreparedStatement stmt;              // объект, отвечающий за отправку запросов

    public static void connect() {                      // подключение к БД
        try {
           // Class.forName("org.sqlite.JDBC");           // регистрация драйвера
            DriverManager.registerDriver(new org.postgresql.Driver());
            conn = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("postgresql database connected successful");
        } catch (Exception c) {
            System.out.println("Ошибка открытия соединения с базой данных");
        }
    }

    public static void disconnect() {                  // отключение от БД
        try {
            conn.close();                              // закрываем соединение
            System.out.println("Posgresql database successful disconected");
        } catch (Exception c) {
            System.out.println("Ошибка закрытия соединения с базой данных");
        }
    }

    public static String getNickByLoginPassword(String login, String password) {
        String w = null;
        try {
            // формируем запрос на выборку ника по логину/паролю
            stmt = conn.prepareStatement(SQLSelect);
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();         // выполняем запрос и получаем результат
            if(rs.next())
                w = rs.getString("Nickname");

        } catch (SQLException e) {
            System.out.println("SQL Query Error");
            e.printStackTrace();
        }
        return w;
    }
}
