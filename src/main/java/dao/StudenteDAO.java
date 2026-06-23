package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public abstract class StudenteDAO extends UtenteDAO{
    public String getMatricola(String username, ConnessioneDatabase conn) ;
    public Boolean aggiungiTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) ;
    public Boolean aggiungiRichiesta(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn);
    public Boolean aggiornaTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) ;
    public Boolean prenotaSedutaDiLaurea(String matricola, LocalDate data, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) ;
    public ResultSet getAllTesi(String matricola,ConnessioneDatabase conn) ;
    public ResultSet getAllRichiesta(String matricola,ConnessioneDatabase conn) ;
    public ResultSet getAllStudente(ConnessioneDatabase conn) ;

}

