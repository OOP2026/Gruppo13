package implementazioneDao;

import dao.DocenteDAO;

import java.sql.*;
import java.time.LocalDate;

public class DocenteImplementazionePostgres implements DocenteDAO {
    public boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn)throws SQLException{

    }
    public boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio,String docente,Connection conn) throws SQLException{

    }
    public boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{

    }
    public boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{

    }
    public boolean aggiungiSedutaDiLaurea(LocalDate data,String docente,Connection conn) throws SQLException{

    }
    public boolean aggiungiVotoSedutaDiLaurea(LocalDate data,String docente,Connection conn) throws SQLException{

    }
    public boolean aggiungiTirocinio(LocalDate data,String docente,Connection conn) throws SQLException{

    }
    public boolean rifiutaTirocinio(LocalDate data,String docente,Connection conn) throws SQLException{

    }
    public boolean isCoordinatore(String docente,Connection conn) throws SQLException{

    }
    public boolean setCoordinatore(String docente, boolean coordinatore,Connection conn) throws SQLException{

    }
    public ResultSet getAllTirocinio(String docente, Connection conn) throws SQLException{

    }
    public ResultSet getAllSeduta(String docente,Connection conn) throws SQLException{

    }
    public ResultSet getAllDocente(Connection conn) throws SQLException{

    }
    public ResultSet queryViaUtente(String query,Connection conn) throws SQLException{

    }
    public boolean setPassword(String password,Connection conn) throws SQLException{

    }
    public boolean setUsername(String oldusername, String newusername,Connection conn) throws SQLException{

    }
    public boolean login(String username,String password,Connection conn) throws SQLException{
    }
    public boolean logout(String username,Connection conn) throws SQLException{

    }
    public String getNome(String username,Connection conn) throws SQLException{

    }
    public String getCognome(String username,Connection conn) throws SQLException{

    }
    public String getEmail(String username,Connection conn) throws SQLException{

    }
    public String getPassword(String username,Connection conn) throws SQLException{

    }
}
