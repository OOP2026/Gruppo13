package database_connection;
import java.sql.*;

public class ConnessioneDatabase {
    private Connection conn;
    private static ConnessioneDatabase instance;
    private String nome = "postgres";
    private String password = "password";
    private String url = "jdbc:postgresql://localhost:5432/Borsa";
    private String driver = "org.postgresql.Driver";

    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, nome, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.conn.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }
    public Connection getConnection() throws SQLException{
        return conn;
    }
    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            ResultSet x = statement.executeQuery();
            return x;
        } catch (SQLException e) {
            System.out.println("Errore nell'esecuzione della query: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally {
            statement.close();
            conn.close();
        }
    }
}