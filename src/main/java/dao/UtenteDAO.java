package dao;
import database_connection.ConnessioneDatabase;

import java.sql.*;
public interface UtenteDAO {
    public ResultSet queryViaUtente(String query, ConnessioneDatabase conn) throws SQLException;
    public Boolean setPassword(String username,String password,ConnessioneDatabase conn) throws SQLException;
    public Boolean setUsername(String oldusername, String newusername,ConnessioneDatabase conn) throws SQLException;
    public Boolean login(String email,String password,ConnessioneDatabase conn) throws SQLException;
    public Boolean logout(String username,ConnessioneDatabase conn) throws SQLException;
    public String getNome(String username,ConnessioneDatabase conn) throws SQLException;
    public String getCognome(String username,ConnessioneDatabase conn) throws SQLException;
    public String getEmail(String username,ConnessioneDatabase conn) throws SQLException;
    public String getPassword(String username,ConnessioneDatabase conn) throws SQLException;
}
