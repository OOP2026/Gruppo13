package dao;

import database_connection.ConnessioneDatabase;

import java.time.LocalDate;
import java.sql.*;

public interface TirocinioEsternoDAO extends TirocinioDAO{
    public String getNomeAzienda(String docente, String nome, LocalDate data, ConnessioneDatabase conn)throws SQLException;
    public String getReferente(String docente,String nome, LocalDate data,ConnessioneDatabase conn)throws SQLException;
    public ResultSet queryViaTirocinioEsterno(String query, ConnessioneDatabase conn)throws SQLException;
    public ResultSet getAllTirocinioEsterno(ConnessioneDatabase conn)throws SQLException;
}
