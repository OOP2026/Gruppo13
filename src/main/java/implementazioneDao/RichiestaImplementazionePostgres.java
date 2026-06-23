package implementazioneDao;

import dao.RichiestaDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public class RichiestaImplementazionePostgres implements RichiestaDAO {
    public char getStato(LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException{
        try{
            char s='\0';
            ResultSet x=conn.executeQuery("SELECT Stato from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')");
            if (x.next())
                s= x.getString("Stato").toCharArray()[0];
            return s;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }

        return '\0';
    }
    public Boolean setStato(char stato,LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException{
        try{
            conn.executeQuery("UPDATE Richiesta SET Stato ='"+stato+"'WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')").close();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }

        return false;
    }
    public ResultSet queryViaRichiesta(String query, ConnessioneDatabase conn) throws SQLException{
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }

        return null;
    }
    public ResultSet getAllRichiesta(ConnessioneDatabase conn) throws SQLException{
        try{
            return conn.executeQuery("SELECT * FROM Richiesta");
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
}
