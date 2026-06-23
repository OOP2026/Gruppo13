package implementazioneDao;

import java.sql.SQLException;
@
public class SQLExceptionHandler {
    public static void handleSQLException(SQLException e){
        System.out.println("Problema nell'esecuzione della query");
        e.printStackTrace();
    }
}
