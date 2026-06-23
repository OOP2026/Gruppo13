package implementazioneDao;

import java.sql.SQLException;
public class ExceptionHandler {
    public static void handleSQLException(SQLException e){
        System.out.println("Problema nell'esecuzione della query:" + e.getMessage());
    }
    public static void handleNullPointerException(Exception e){
        System.out.println("Problema sull'utilizzo di un puntatore nullo:" + e.getMessage());
    }
    public static void handleInconsistencyException(InconsistencyException e){
        System.out.println("Problema sulla consistenza dei dati:" + e.getMessage());
    }
}
