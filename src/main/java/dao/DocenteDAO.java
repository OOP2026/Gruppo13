package dao;
import java.sql.*;
import java.time.LocalDate;

public interface DocenteDAO extends UtenteDAO{
    public void accettaRichiesta(String studente, String nometirocinio, String datatirocinio,String docente)throws SQLException;
}
