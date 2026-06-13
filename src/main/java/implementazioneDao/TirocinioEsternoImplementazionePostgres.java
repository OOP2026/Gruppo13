package implementazioneDao;

import dao.TirocinioEsternoDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TirocinioEsternoImplementazionePostgres extends TirocinioImplementazionePostgres implements TirocinioEsternoDAO {
    public String getNomeAzienda(String docente, LocalDate data, Connection conn)throws SQLException{

    }
    public String getReferente(String docente, LocalDate data,Connection conn)throws SQLException{

    }
    public ResultSet queryViaTirocinioEsterno(String query, Connection conn)throws SQLException{

    }
    public ResultSet getAllTirocinioEsterno(Connection conn)throws SQLException{

    }
}
