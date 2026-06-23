package dao;

import database_connection.ConnessioneDatabase;

import java.time.LocalDate;
import java.sql.*;

public interface TirocinioEsternoDAO extends TirocinioDAO{
    public String getNomeAzienda(String docente, String nome, LocalDate data, ConnessioneDatabase conn);
    public String getReferente(String docente,String nome, LocalDate data,ConnessioneDatabase conn);
    public ResultSet queryViaTirocinioEsterno(String query, ConnessioneDatabase conn);
    public ResultSet getAllTirocinioEsterno(ConnessioneDatabase conn);
}
