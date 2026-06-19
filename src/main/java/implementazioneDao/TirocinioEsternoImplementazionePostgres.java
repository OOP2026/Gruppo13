package implementazioneDao;

import dao.TirocinioEsternoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TirocinioEsternoImplementazionePostgres extends TirocinioImplementazionePostgres implements TirocinioEsternoDAO {
    public String getNomeAzienda(String docente,String nome, LocalDate data, Connection conn)throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT NomeAzienda FROM Tirocinio NATURAL JOIN TirocinioEsterno WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if (x.next())
                return x.getString("Descrizione");
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
    public String getReferente(String docente,String nome, LocalDate data,Connection conn)throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Referente FROM Tirocinio NATURAL JOIN TirocinioEsterno WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if (x.next())
                return x.getString("Descrizione");
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
    public ResultSet queryViaTirocinioEsterno(String query, Connection conn)throws SQLException{
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
    public ResultSet getAllTirocinioEsterno(Connection conn)throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Tirocinio NATURAL JOIN TirocinioEsterno");
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
