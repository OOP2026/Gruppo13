package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public interface TirocinioDAO {
    public String getDescrizione(String docente, String nome, LocalDate data, ConnessioneDatabase conn)throws SQLException;
    public ResultSet queryViaTirocinio(String query,ConnessioneDatabase conn) throws SQLException;
    public ResultSet getAllTirocinio(ConnessioneDatabase conn) throws SQLException;
}
