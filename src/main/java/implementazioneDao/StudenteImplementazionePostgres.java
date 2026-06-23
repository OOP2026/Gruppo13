package implementazioneDao;

import dao.StudenteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class StudenteImplementazionePostgres extends StudenteDAO {
    public Boolean setPassword(String username, String password,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Studente set Password = '"+password+"' WHERE Docente.Login = '"+username+"'").close();
            return true;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        return false;
    }
    public Boolean setUsername(String oldusername, String newusername,ConnessioneDatabase conn) {

        try{
            conn.executeQuery("UPDATE Studente set Login = '"+newusername+"' WHERE Studente.Login = '"+oldusername+"'").close();
            return true;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return false;
    }
    public Boolean login(String username,String password,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Studente set Stato = TRUE WHERE Studente.Login = '"+username+"' AND Password = '"+password+"'").close();
            return true;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return false;
    }
    public Boolean logout(String username,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Studente set Stato = FALSE WHERE Studente.Login = '"+username+"'").close();
            return true;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return false;
    }
    //MAY BE NULL
    public String getNome(String username,ConnessioneDatabase conn) {
        try{
            String y=null;
            ResultSet x = conn.executeQuery("SELECT Nome FROM Studente WHERE Studente.Login = '"+username+"'");
            if(x.next())
                y= x.getString("Nome");
            x.close();
            return y;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        return null;
    }
    //MAY BE NULL
    public String getCognome(String username,ConnessioneDatabase conn) {
        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Cognome FROM Studente WHERE Studente.Login = '"+username+"'");
            if(x.next())
                y=x.getString("Cognome");
            x.close();
            return y;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return null;
    }
    //MAY BE NULL
    public String getEmail(String username,ConnessioneDatabase conn) {

        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Email FROM Studente WHERE Studente.Login = '"+username+"'");
            if(x.next())
                y=x.getString("Email");
            x.close();
            return y;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return null;
    }
    //MAY BE NULL
    public String getPassword(String username,ConnessioneDatabase conn) {

        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Password FROM Studente WHERE Studente.Login = '"+username+"'");
            if(x.next())
                y=x.getString("Password");
            x.close();
            return y;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return null;
    }
    public String getMatricola(String username,ConnessioneDatabase conn) {

        try{
            String y=null;
            ResultSet x=conn.executeQuery("SELECT Matricola FROM Studente WHERE Studente.Login = '"+username+"'");
            if(x.next())
                y=x.getString("Password");
            x.close();
            return y;
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return null;
    }
    public Boolean aggiungiTesi(String matricola, String contenuto, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("INSERT INTO Tesi(Contenuto,ID_Ri) VALUES ('"+contenuto+"', (SELECT ID_Ri FROM Richiesta R JOIN Tirocinio T ON T.ID_Ti=R.ID_Ti WHERE T.Nome='"+nometirocinio+"' AND T.Data='"+datatirocinio.toString()+"'AND T.Login = '"+docente+"')))").close();
            return true;
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return false;
    }
    public Boolean aggiungiRichiesta(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn){
        try{
            conn.executeQuery("INSERT INTO Richiesta(Login,Data,ID_Ti) VALUES ((SELECT Login FROM Studente WHERE Matricola ='"+matricola+"'),'"+LocalDate.now(ZoneId.systemDefault())+"',(SELECT ID_Ti FROM Tirocinio T WHERE T.Nome='"+nometirocinio+"' AND T.Data='"+datatirocinio.toString()+"' AND T.Login='"+docente+"'))").close();
            return true;
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return false;
    }
    public Boolean aggiornaTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) {
           try{
               conn.executeQuery("UPDATE Tesi SET Tesi.Contenuto = '"+contenuto+"' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = (SELECT Login FROM Studente S WHERE S.Matricola='"+matricola+"') AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))").close();
               return true;
            }
           catch(SQLException e){
                SQLExceptionHandler.handleSQLException(e);
           }
            return false;
    }
    public Boolean prenotaSedutaDiLaurea(String matricola, LocalDate data, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Seduta SET ID_Te=(SELECT ID_Te FROM Tesi WHERE ID_Ri = (SELECT ID_RI FROM Richiesta WHERE Login='"+matricola+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Nome='"+nometirocinio+"' AND Data='"+datatirocinio.toString()+"' AND Login='"+docente+"'))) WHERE Login='"+docente+"' AND Data='"+data.toString()+"'").close();
            return true;
        }
        catch(SQLException e) {
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return false;
    }
    public ResultSet getAllTesi(String matricola,ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Tesi WHERE ID_Ri=ANY(SELECT ID_Ri from Richiesta WHERE Login=(SELECT Login FROM Studente WHERE Matricola ='"+matricola+"'))");
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        return null;
    }
    public ResultSet getAllRichiesta(String matricola,ConnessioneDatabase conn) {
       try{
            return conn.executeQuery("SELECT * from Richiesta WHERE Login=(SELECT Login FROM Studente WHERE Matricola ='"+matricola+"')");
       }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return null;
    }
    public ResultSet getAllStudente(ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Studente");
        }
        catch(SQLException e){
            SQLExceptionHandler.handleSQLException(e);
        }
        
        return null;
    }
}
