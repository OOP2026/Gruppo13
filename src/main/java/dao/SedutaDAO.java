package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public interface SedutaDAO {
    public ResultSet getTesi(LocalDate data, LocalTime ora, String docente, Connection conn) throws SQLException;
    public boolean setTesi(LocalDate data,LocalTime ora, String docente,String studente,String nometirocinio, LocalDate datatirocinio,Connection conn) throws SQLException;
    public int getVotoFinale(LocalDate data,LocalTime ora,String docente,Connection conn) throws SQLException;
    public boolean setVotoFinale(int voto,LocalDate data,LocalTime ora,String docente,Connection conn) throws SQLException;
    public ResultSet getAllSeduta(Connection conn) throws SQLException;
    public ResultSet queryViaSeduta(String query,Connection conn) throws SQLException;
}
