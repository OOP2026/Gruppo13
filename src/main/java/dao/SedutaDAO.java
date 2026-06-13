package dao;

import java.sql.*;
import java.time.LocalDate;

public interface SedutaDAO {
    public ResultSet getTesi(LocalDate data, String docente, Connection conn) throws SQLException;
    public boolean setTesi(LocalDate data, String docente,String nometirocinio, LocalDate datatirocinio,Connection conn) throws SQLException;
    public int getVotoFinale(LocalDate data,String docente,Connection conn) throws SQLException;
    public boolean setVotoFinale(LocalDate data,String docente,Connection conn) throws SQLException;
    public ResultSet getAllSeduta(Connection conn) throws SQLException;
    public ResultSet queryViaSeduta(String query,Connection conn) throws SQLException;
}
