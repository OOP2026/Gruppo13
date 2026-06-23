package dao;
import database_connection.ConnessioneDatabase;
import implementazioneDao.SQLExceptionHandler;

import java.sql.*;
public abstract class UtenteDAO {
    public ResultSet queryViaUtente(String query, ConnessioneDatabase conn){
        public ResultSet queryViaUtente(String query, ConnessioneDatabase conn) {
            try{
                return conn.executeQuery(query);
            }
            catch(SQLException e){
                SQLExceptionHandler.handleSQLException(e);
            }
            return null;
        }
    };
    public Boolean setPassword(String username, String password, ConnessioneDatabase conn) ;
    public Boolean setUsername(String oldusername, String newusername, ConnessioneDatabase conn) ;
    public Boolean login(String email, String password, ConnessioneDatabase conn) ;
    public Boolean logout(String username, ConnessioneDatabase conn) ;
    public String getNome(String username, ConnessioneDatabase conn) ;
    public String getCognome(String username, ConnessioneDatabase conn) ;
    public String getEmail(String username, ConnessioneDatabase conn) ;
    public String getPassword(String username, ConnessioneDatabase conn) ;
}
