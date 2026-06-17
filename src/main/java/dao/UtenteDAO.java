package dao;
import java.sql.*;
public interface UtenteDAO {
    public ResultSet queryViaUtente(String query,Connection conn) throws SQLException;
    public boolean setPassword(String username,String password,Connection conn) throws SQLException;
    public boolean setUsername(String oldusername, String newusername,Connection conn) throws SQLException;
    public boolean login(String username,String password,Connection conn) throws SQLException;
    public boolean logout(String username,Connection conn) throws SQLException;
    public String getNome(String username,Connection conn) throws SQLException;
    public String getCognome(String username,Connection conn) throws SQLException;
    public String getEmail(String username,Connection conn) throws SQLException;
    public String getPassword(String username,Connection conn) throws SQLException;
}
