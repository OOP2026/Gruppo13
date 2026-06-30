package dao;
import database_connection.ConnessioneDatabase;
public interface UtenteDAO{
    public Boolean setPassword(String username, String password, ConnessioneDatabase conn) ;
    public Boolean setUsername(String oldusername, String newusername, ConnessioneDatabase conn) ;
    public Boolean login(String email, String password, ConnessioneDatabase conn) ;
    public Boolean logout(String username, ConnessioneDatabase conn) ;
    public String getNome(String username, ConnessioneDatabase conn) ;
    public String getCognome(String username, ConnessioneDatabase conn) ;
    public String getEmail(String username, ConnessioneDatabase conn) ;
    public String getPassword(String username, ConnessioneDatabase conn) ;
}
