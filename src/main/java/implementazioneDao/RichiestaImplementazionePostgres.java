package implementazioneDao;

import dao.RichiestaDAO;

import java.sql.*;
import java.time.LocalDate;

public class RichiestaImplementazionePostgres implements RichiestaDAO {
    public char getStato(LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Stato from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')");
        try{
            ResultSet x=stmt.executeQuery();
            if (x.next())
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
    public boolean setStato(char stato,LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Richiesta SET Stato ='"+stato+"'WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')");
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
    public ResultSet queryViaRichiesta(String query, Connection conn) throws SQLException{
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
    public ResultSet getAllRichiesta(Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Richiesta");
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
}
