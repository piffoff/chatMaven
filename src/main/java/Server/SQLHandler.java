package Server;

import java.sql.*;

/**
 * Класс отвечает за свясь с базой данных
 */
public class SQLHandler {
    private static Connection conn;                     // соединение с базой данных
    private static PreparedStatement stmt;              // объект, отвечающий за отправку запросов

    public static void connect() {                      // подключение к БД
        try {
            Class.forName("org.sqlite.JDBC");           // регистрация драйвера
            conn = DriverManager.getConnection("jdbc:sqlite:ClientsDB.db"); // открытие соединения
        } catch (Exception c) {
            System.out.println("Ошибка соединения с базой данных");
        }
    }

    public static void disconnect() {                  // отключение от БД
        try {
            conn.close();                              // закрываем соединение
        } catch (Exception c) {
            System.out.println("Ошибка соединения с базой данных");
        }
    }
    // поиск ника по логину/паролю
    public static String getNickByLoginPassword(String login, String password) {
        String w = null;
        try {
            // формируем запрос на выборку ника по логину/паролю
            stmt = conn.prepareStatement("SELECT Nickname FROM main WHERE Login = ? AND Password = ?;");
            stmt.setString(1, login);                      // указываем логин в запросе
            stmt.setString(2, password);              // указываем пароль в запросе
            ResultSet rs = stmt.executeQuery(); // выполняем запрос и получаем результат
            if(rs.next())
                w = rs.getString("Nickname");

        } catch (SQLException e) {
            System.out.println("SQL Query Error");
            e.printStackTrace();
        }
        return w;
    }
}
