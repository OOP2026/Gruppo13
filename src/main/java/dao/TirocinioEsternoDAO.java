package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.*;

public interface TirocinioEsternoDAO extends TirocinioDAO{
    public String getNomeAzienda(String docente,String nome, LocalDate data,Connection conn)throws SQLException;
    public String getReferente(String docente,String nome, LocalDate data,Connection conn)throws SQLException;
    public ResultSet queryViaTirocinioEsterno(String query, Connection conn)throws SQLException;
    public ResultSet getAllTirocinioEsterno(Connection conn)throws SQLException;
}
