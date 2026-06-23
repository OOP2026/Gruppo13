package implementazioneDao;

import dao.TirocinioDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public class TirocinioImplementazionePostgres implements TirocinioDAO {
    public String getDescrizione(String docente, String nome, LocalDate data, ConnessioneDatabase conn)throws SQLException{
        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Descrizione FROM Tirocinio WHERE Nome='"+nome+"' AND Data='"+data+"' AND Docente='"+docente+"'");
            if (x.next())
                y=x.getString("Descrizione");
            x.close();
            return y;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet queryViaTirocinio(String query, ConnessioneDatabase conn) throws SQLException{
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllTirocinio(ConnessioneDatabase conn) throws SQLException{
        try{
            return conn.executeQuery("SELECT * FROM Tirocinio");
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
}
