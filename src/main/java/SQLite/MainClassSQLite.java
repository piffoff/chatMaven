package SQLite;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.SQLException;

public class MainClassSQLite {

    public static void main(String[] args) throws SQLException {
        SQLiteConnector connector = new SQLiteConnector();
        System.out.println(connector.insert("Ivan", "18"));
        System.out.println(connector.select());

    }
}
