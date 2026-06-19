package dao;

import java.sql.*;
import java.time.LocalDate;

public interface TesiDAO {
    public char getStato(String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException;
    public boolean setStato(char stato,String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException;
    public ResultSet queryViaTesi(String query, Connection conn) throws SQLException;
    public ResultSet getAllTesi(Connection conn) throws SQLException;
    public boolean setContenuto(String contenuto,String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException;
    public String getContenuto(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException;
}
