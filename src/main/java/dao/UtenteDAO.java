package dao;
import java.sql.*;

public interface UtenteDAO {
    public abstract ResultSet getAllUtente() throws SQLException;
    public abstract ResultSet queryViaUtente(String query) throws SQLException;
    public abstract void setPassword(String password) throws SQLException;
    public abstract void setUsername(String oldusername, String newusername) throws SQLException;
    public void login(String username,String password) throws SQLException;
    public void logout(String username) throws SQLException;
    public String getNome(String username) throws SQLException;
    public String getCognome(String username) throws SQLException;
    public String getEmail(String username) throws SQLException;
    public String getPassword(String username) throws SQLException;
}
