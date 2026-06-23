package implementazioneDao;

import dao.TesiDAO;
import database_connection.ConnessioneDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TesiImplementazionePostgres implements TesiDAO {
    public char getStato(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException{
        try{
            char y='\0';
            ResultSet x=conn.executeQuery("SELECT TOP 1 FROM Tesi WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))");
            if(x.next())
                y=x.getString("Stato").toCharArray()[0];
            x.close();
            return y;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return '\0';
    }
    public Boolean setStato(char stato, String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException{
        try{
            conn.executeQuery("UPDATE Tesi SET Stato='"+stato+"' WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))").close();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet queryViaTesi(String query, ConnessioneDatabase conn) throws SQLException{
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllTesi(ConnessioneDatabase conn) throws SQLException{
        try{
            return conn.executeQuery("SELECT * FROM Tesi");
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public Boolean setContenuto(String contenuto,String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException{
        try{
            conn.executeQuery("UPDATE Tesi SET Contenuto='"+contenuto+"' WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))").close();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public String getContenuto(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException{

        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Contenuto FROM TESI WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))");
            if(x.next())
                y=x.getString("Contenuto");
            x.close();
            return y;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
}
