package implementazioneDao;

import dao.TirocinioDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public class TirocinioImplementazionePostgres implements TirocinioDAO {
    public String getDescrizione(String docente, String nome, LocalDate data, ConnessioneDatabase conn){
        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Descrizione FROM Tirocinio WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
            if (x.next())
                y=x.getString("Descrizione");
            x.close();
            return y;
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        return null;
    }
    public ResultSet queryViaTirocinio(String query, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        return null;
    }
    public ResultSet getAllTirocinio(ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Tirocinio");
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        return null;
    }
}
