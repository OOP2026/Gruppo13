package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public interface TesiDAO {
    public char getStato(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) ;
    public Boolean setStato(char stato,String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) ;
    public ResultSet queryViaTesi(String query, ConnessioneDatabase conn) ;
    public Boolean setContenuto(String contenuto, String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) ;
    public String getContenuto(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) ;
}
