package implementazioneDao;

import dao.SedutaDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SedutaImplementazionePostgres implements SedutaDAO {
    public ResultSet getTesi(LocalDate data, LocalTime ora, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Tesi WHERE Tesi.IdTe=(SELECT ID_Te FROM Seduta WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"')");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public boolean setTesi(LocalDate data, LocalTime ora,String docente,String studente,String nometirocinio, LocalDate datatirocinio,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Seduta SET Seduta.IdTe =(SELECT ID_Te from Tesi WHERE WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"')))WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public int getVotoFinale(LocalDate data,LocalTime ora,String docente,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT VotoFinale FROM Seduta WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"'");
        try{
            ResultSet x = stmt.executeQuery();
            if (x.next())
                return x.getInt("VotoFinale");
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return -1;
    }
    public boolean setVotoFinale(int voto,LocalDate data,LocalTime ora,String docente,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Seduta SET Seduta.VotoFinale="+voto+" WHERE Login='"+docente+"'AND Data='"+data.toString()+"' AND Ora='"+ora.toString()+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getAllSeduta(Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Seduta");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet queryViaSeduta(String query,Connection conn) throws SQLException{
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
}
