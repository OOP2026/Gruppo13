package database_connection;
import java.sql.*;

public class ConnessioneDatabase {
    private Connection conn;
    private static ConnessioneDatabase instance;
    private static final String NOME= "postgres";
    private static final String PW = "Fausty886";
    private static final String URL = "jdbc:postgresql://localhost:5432/Borsa";

    private ConnessioneDatabase() throws SQLException {
            conn = DriverManager.getConnection(URL, NOME, PW);
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.conn.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
        finally {
            statement.close();
            conn.close();
        }
    }
    public int executeUpdate(String query) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            return statement.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            statement.close();
            conn.close();
        }
    }
}