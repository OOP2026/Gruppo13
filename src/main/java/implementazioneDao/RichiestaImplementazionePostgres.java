package implementazioneDao;

import dao.RichiestaDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import static utilities.ExceptionHandler.*;

public class RichiestaImplementazionePostgres implements RichiestaDAO {
    public char getStato(LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            char s='\0';
            ResultSet x=conn.executeQuery("SELECT Stato from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')");
            if (x.next())
                s= x.getString("Stato").toCharArray()[0];
            return s;
        }
        catch(SQLException e) {
            handleSQLException(e);
        }

        return '\0';
    }
    public Boolean setStato(char stato,LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Richiesta SET Stato ='"+stato+"'WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')").close();
            return true;
        }
        catch(SQLException e) {
            handleSQLException(e);
        }

        return false;
    }
    public ResultSet queryViaRichiesta(String query, ConnessioneDatabase conn) {
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
            return conn.executeQuery("SELECT * FROM Richiesta r JOIN Tirocinio T on r.ID_Ti = t.ID_Ti");
        }
        catch(SQLException e) {
            handleSQLException(e);
        }
        return null;
    }
}
