package implementazioneDao;

import dao.TesiDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TesiImplementazionePostgres implements TesiDAO {
    public char getStato(LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{

    }
    public boolean setStato(LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
    }
    public ResultSet queryViaTesi(String query, Connection conn) throws SQLException{

    }
    public ResultSet getAllTesi(Connection conn) throws SQLException{

    }
    public boolean setContenuto(String contenuto,LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{

    }
    public String getContenuto(LocalDate data, String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{

    }
}
