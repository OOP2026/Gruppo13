package implementazioneDao;

import dao.SedutaDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SedutaImplementazionePostgres implements SedutaDAO {
    public ResultSet getTesi(LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT 1 FROM Tesi WHERE Tesi.IdTe=(SELECT ID_Te FROM Seduta WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"')");
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }

        return null;
    }
    public Boolean setTesi(LocalDate data, LocalTime ora,String docente,String studente,String nometirocinio, LocalDate datatirocinio,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Seduta SET Seduta.IdTe =(SELECT ID_Te from Tesi WHERE WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')))WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"'").close();
            return true;
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }

        return false;
    }
    public int getVotoFinale(LocalDate data,LocalTime ora,String docente,ConnessioneDatabase conn) {
        try{
            int v=-1;
            ResultSet x = conn.executeQuery("SELECT VotoFinale FROM Seduta WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"'");
            if (x.next())
                v=x.getInt("VotoFinale");
            return v;
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        return -2;
    }

    public ResultSet getAllSeduta(ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Seduta");
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }

        return null;
    }
    public ResultSet queryViaSeduta(String query,ConnessioneDatabase conn) {
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }

        return null;
    }
}
