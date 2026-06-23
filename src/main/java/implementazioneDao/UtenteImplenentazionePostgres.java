package implementazioneDao;

import database_connection.ConnessioneDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;

import static implementazioneDao.ExceptionHandler.handleSQLException;


public class UtenteImplenentazionePostgres {
    public ResultSet queryViaUtente(String query, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }
}
