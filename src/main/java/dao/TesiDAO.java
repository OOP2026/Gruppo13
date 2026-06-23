package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public interface TesiDAO {
    public char getStato(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException;
    public Boolean setStato(char stato,String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException;
    public ResultSet queryViaTesi(String query, ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllTesi(ConnessioneDatabase conn) throws SQLException;
    public Boolean setContenuto(String contenuto,String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException;
    public String getContenuto(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException;
}
