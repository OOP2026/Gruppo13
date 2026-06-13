package implementazioneDao;

import dao.StudenteDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StudenteImplementazionePostgres implements StudenteDAO {
    public ResultSet queryViaUtente(String query, Connection conn) throws SQLException {

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
    public String getMatricola(String matricola,Connection conn) throws SQLException{

    }
    public boolean aggiungiTesi(String matricola, String contenuto, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{

    }
    public boolean aggiungiRichiesta(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn)throws SQLException{

    }
    public boolean aggiornaTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{

    }
    public boolean prenotaSedutaDiLaurea(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{

    }
    public ResultSet getAllTesi(String matricola,Connection conn) throws SQLException{

    }
    public ResultSet getAllRichiesta(String matricola,Connection conn) throws SQLException{

    }
    public ResultSet getAllStudente(Connection conn) throws SQLException{

    }
}
