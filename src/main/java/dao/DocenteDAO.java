package dao;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public interface DocenteDAO extends UtenteDAO{
    public Boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn)throws SQLException;
    public Boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) throws SQLException;
    public Boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean aggiungiSedutaDiLaurea(LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) throws SQLException;
    public Boolean aggiungiVotoSedutaDiLaurea(int voto, LocalDate data,LocalTime ora,String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean aggiungiTirocinio(String nome,String descrizione,LocalDate data,String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean isCoordinatore(String docente,ConnessioneDatabase conn) throws SQLException;
    public Boolean setCoordinatore(boolean x,String docente, boolean coordinatore,ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllTirocinio(String docente,ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllSeduta(String docente,ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllDocente(ConnessioneDatabase conn) throws SQLException;
}