package implementazioneDao;

import dao.TirocinioDAO;

import java.sql.*;
import java.time.LocalDate;

public class TirocinioImplementazionePostgres implements TirocinioDAO {
    public String getDescrizione(String docente, String nome, LocalDate data, Connection conn)throws SQLException{

    }
    public ResultSet queryViaTirocinio(String query, Connection conn) throws SQLException{

    }
    public ResultSet getAllTirocinio(Connection conn) throws SQLException{

    }
}
