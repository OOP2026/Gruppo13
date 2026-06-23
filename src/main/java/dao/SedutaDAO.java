package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public interface SedutaDAO {
    public ResultSet getTesi(LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) ;
    public Boolean setTesi(LocalDate data,LocalTime ora, String docente,String studente,String nometirocinio, LocalDate datatirocinio,ConnessioneDatabase conn) ;
    public int getVotoFinale(LocalDate data,LocalTime ora,String docente,ConnessioneDatabase conn) ;
    public ResultSet getAllSeduta(ConnessioneDatabase conn) ;
    public ResultSet queryViaSeduta(String query,ConnessioneDatabase conn) ;
}
