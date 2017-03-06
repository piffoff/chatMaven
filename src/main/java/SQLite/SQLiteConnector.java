package SQLite;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/*
SQL table1
ID  Name    Age
1   Sergey  28
2   Elio    25
*/

public class SQLiteConnector {
    private static final String URL = "jdbc:sqlite:D:/Downloads/SQLite/base.db ";
    private Connection connection;

    public SQLiteConnector() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(URL);
    }

    public List<String> select (){
        try(Statement statement = this.connection.createStatement()){
            List<String> res = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("Select * from table1");
            StringBuilder builder = new StringBuilder();
            while (resultSet.next()){
                builder.setLength(0);
                builder.append(String.format("id=[}", resultSet.getInt(1)));
                builder.append(String.format("id=[}", resultSet.getString("Name")));
                builder.append(String.format("id=[}", resultSet.getString("Age")));
                res.add(builder.toString());
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean insert (String name, String age){

        try {
            PreparedStatement statement = this.connection.prepareStatement("insert into table1(Name, Age) values(?,?);");
            statement.setObject(1, name);
            statement.setObject(2, age);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
