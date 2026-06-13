package dao;
import java.sql.*;
import java.time.LocalDate;

public interface DocenteDAO extends UtenteDAO{
    public boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn)throws SQLException;
    public boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio,String docente,Connection conn) throws SQLException;
    public boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException;
    public boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException;
    public boolean aggiungiSedutaDiLaurea(LocalDate data,String docente,Connection conn) throws SQLException;
    public boolean aggiungiVotoSedutaDiLaurea(LocalDate data,String docente,Connection conn) throws SQLException;
    public boolean aggiungiTirocinio(LocalDate data,String docente,Connection conn) throws SQLException;
    public boolean rifiutaTirocinio(LocalDate data,String docente,Connection conn) throws SQLException;
    public boolean isCoordinatore(String docente,Connection conn) throws SQLException;
    public boolean setCoordinatore(String docente, boolean coordinatore,Connection conn) throws SQLException;
    public ResultSet getAllTirocinio(String docente,Connection conn) throws SQLException;
    public ResultSet getAllSeduta(String docente,Connection conn) throws SQLException;
    public ResultSet getAllDocente(Connection conn) throws SQLException;
}