package dao;

import java.sql.*;
import java.time.LocalDate;

public interface StudenteDAO extends UtenteDAO{
    public String getMatricola(String username,Connection conn) throws SQLException;
    public boolean aggiungiTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException;
    public boolean aggiungiRichiesta(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn)throws SQLException;
    public boolean aggiornaTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException;
    public boolean prenotaSedutaDiLaurea(String matricola, LocalDate data, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException;
    public ResultSet getAllTesi(String matricola,Connection conn) throws SQLException;
    public ResultSet getAllRichiesta(String matricola,Connection conn) throws SQLException;
    public ResultSet getAllStudente(Connection conn) throws SQLException;

}

