package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Finds postgresql driver class and returns a connection to it
 */
public class JDBCConnectionManager {

    private static String url = "jdbc:postgresql://localhost:5433/interstellar";
    private static String user = "postgres";
    private static String password = "postgres";

    /**
     * connect to db
     * @return
     */
    public static Connection getDBConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url,user,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void closeDBConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
