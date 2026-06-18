package implementazioneDao;

import dao.TirocinioDAO;

import java.sql.*;
import java.time.LocalDate;

public class TirocinioImplementazionePostgres implements TirocinioDAO {
    public String getDescrizione(String docente, String nome, LocalDate data, Connection conn)throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Descrizione FROM Tirocinio WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if (x.next())
                return x.getString("Descrizione");
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet queryViaTirocinio(String query, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement(query);
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllTirocinio(Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Tirocinio");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
}
