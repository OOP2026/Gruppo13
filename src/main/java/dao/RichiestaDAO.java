package dao;

import java.sql.*;
import java.time.LocalDate;

public interface RichiestaDAO {
    public char getStato(LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException;
    public boolean setStato(char stato,LocalDate data,String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException;
    public ResultSet queryViaRichiesta(String query,Connection conn) throws SQLException;
    public ResultSet getAllRichiesta(Connection conn) throws SQLException;
}
