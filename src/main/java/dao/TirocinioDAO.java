package dao;

import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public interface TirocinioDAO {
    public String getDescrizione(String docente, String nome, LocalDate data, ConnessioneDatabase conn);
    public ResultSet queryViaTirocinio(String query, ConnessioneDatabase conn) ;

}
