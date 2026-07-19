package implementazioneDao;

import dao.TirocinioEsternoDAO;
import database_connection.ConnessioneDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import static utilities.ExceptionHandler.*;
public class TirocinioEsternoImplementazionePostgres extends TirocinioImplementazionePostgres implements TirocinioEsternoDAO {
    public String getNomeAzienda(String docente,String nome, LocalDate data, ConnessioneDatabase conn){
        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT NomeAzienda FROM Tirocinio NATURAL JOIN TirocinioEsterno WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
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
    public String getReferente(String docente, String nome, LocalDate data, ConnessioneDatabase conn){
        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Referente FROM Tirocinio NATURAL JOIN TirocinioEsterno WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
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
    public ResultSet queryViaTirocinioEsterno(String query, ConnessioneDatabase conn){
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    @Override
    public ResultSet getAll(ConnessioneDatabase conn){
        try{
            return conn.executeQuery("SELECT * FROM Tirocinio NATURAL JOIN TirocinioEsterno");
        }
        catch(SQLException e) {
            handleSQLException(e);
        }
        return null;
    }
}
