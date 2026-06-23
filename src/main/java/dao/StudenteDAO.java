package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public interface StudenteDAO extends UtenteDAO{
    public String getMatricola(String username, ConnessioneDatabase conn) throws SQLException;
    public Boolean aggiungiTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean aggiungiRichiesta(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn)throws SQLException;
    public Boolean aggiornaTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean prenotaSedutaDiLaurea(String matricola, LocalDate data, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllTesi(String matricola,ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllRichiesta(String matricola,ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllStudente(ConnessioneDatabase conn) throws SQLException;

}

