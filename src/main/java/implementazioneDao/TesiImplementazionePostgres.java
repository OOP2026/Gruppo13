package implementazioneDao;

import dao.TesiDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TesiImplementazionePostgres implements TesiDAO {
    public char getStato(String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT 1 FROM Tesi WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Stato").toCharArray()[0];
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return '\0';
    }
    public boolean setStato(char stato, String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Tesi SET Stato='"+stato+"' WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public ResultSet queryViaTesi(String query, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement(query);
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    public ResultSet getAllTesi(Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Tesi");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    public boolean setContenuto(String contenuto,String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Tesi SET Contenuto='"+contenuto+"' WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public String getContenuto(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{

        PreparedStatement stmt=conn.prepareStatement("SELECT Contenuto FROM TESI WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+studente+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Login='"+docente+"' AND Data='"+datatirocinio.toString()+"' AND Nome='"+nometirocinio+"'))");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Contenuto");
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
}
