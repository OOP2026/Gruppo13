package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public interface RichiestaDAO {
    public char getStato(LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException;
    public Boolean setStato(char stato,LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException;
    public ResultSet queryViaRichiesta(String query, ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllRichiesta(ConnessioneDatabase conn) throws SQLException;
}
