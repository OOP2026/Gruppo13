package implementazioneDao;

import dao.TirocinioDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import static implementazioneDao.ExceptionHandler.*;
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
            handleSQLException(e);
        }
        return null;
    }
    public ResultSet queryViaTirocinio(String query, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            handleSQLException(e);
        }
        return null;
    }
    public ResultSet getAll(ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Tirocinio WHERE Id_Ti NOT IN (SELECT ID_Ti from TirocinioEsterno)");
        }
        catch(SQLException e) {
            handleSQLException(e);
        }
        return null;
    }
}
