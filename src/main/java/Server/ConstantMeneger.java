package Server;

/**
 * Created by Admin on 03.03.2017.
 */
public class ConstantMeneger {

    public static final String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    public static final String user = "postgres";
    public static final String password = "postgres";
    /**
     * 1-st Parameter Login, 2-nd Password
     */
    public static final String SQLSelect = "SELECT Nickname FROM main WHERE Login = ? AND Password = ?;";
//    private static final String dbUrl = "jdbc:postgresql://server1:5432/postgres";
//    private static final String user = "postgres";
//    private static final String password = "******";

}
