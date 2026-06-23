package dao;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class DocenteDAO extends UtenteDAO{
    public Boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn);
    public Boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn);
    public Boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) ;
    public Boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) ;
    public Boolean aggiungiSedutaDiLaurea(LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) ;
    public Boolean aggiungiVotoSedutaDiLaurea(int voto, LocalDate data,LocalTime ora,String docente,ConnessioneDatabase conn) ;
    public Boolean aggiungiTirocinio(String nome,String descrizione,LocalDate data,String docente,ConnessioneDatabase conn) ;
    public Boolean isCoordinatore(String docente,ConnessioneDatabase conn) ;
    public Boolean setCoordinatore(boolean x,String docente, boolean coordinatore,ConnessioneDatabase conn) ;
    public ResultSet getAllTirocinio(String docente,ConnessioneDatabase conn) ;
    public ResultSet getAllSeduta(String docente,ConnessioneDatabase conn) ;
    public ResultSet getAllDocente(ConnessioneDatabase conn) ;
}