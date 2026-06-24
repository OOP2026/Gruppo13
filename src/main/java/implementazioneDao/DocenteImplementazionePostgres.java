package implementazioneDao;

import dao.DocenteDAO;
import database_connection.ConnessioneDatabase;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import static utilities.ExceptionHandler.*;

public class DocenteImplementazionePostgres extends UtenteImplenentazionePostgres implements DocenteDAO {
    @Override
    public Boolean aggiungiTirocinio(String docente, String nome, String descrizione, LocalDate data, String nomeAzienda, String referente, ConnessioneDatabase conn){
        try{
            conn.executeQuery("INSERT INTO Tirocinio(Nome,Descrizione,Data,Login) VALUES ('"+nome+"','"+descrizione+"','"+data+"','"+docente+"');").close();
            conn.executeQuery("INSERT INTO TirocinioEsterno(NomeAzienda,Referente,ID_Ti) VALUES ('"+nomeAzienda+"','"+referente+"',SELECT TOP 1 ID_Ti FROM Tirocinio WHERE Nome = '"+nome+"' AND  Descrizione = '"+descrizione+"' AND Data = '"+data+"' AND Login = '"+docente+"');");
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn){
        try{
            conn.executeQuery("UPDATE Richiesta SET Richiesta.Stato = 'V' WHERE Richiesta.Login = "+studente+" AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ="+nometirocinio+" AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = "+docente+")").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Richiesta SET Richiesta.Stato = 'X' WHERE Richiesta.Login = "+studente+" AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ="+nometirocinio+" AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = "+docente+")").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Tesi SET Tesi.Stato = 'X' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Tesi SET Tesi.Stato = 'V' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean aggiungiSedutaDiLaurea(LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("INSERT INTO Seduta(Data,Ora,Login) VALUES ('"+data+"','"+ora+"''"+docente+"')").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean aggiungiVotoSedutaDiLaurea(int voto, LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Seduta SET VotoFinale = '"+voto+"' WHERE Seduta.Data = '"+data+"' AND Seduta.Ora ='"+ora.toString()+"' AND Seduta.Login = '"+docente+"'").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean aggiungiTirocinio(String docente,String nome, String descrizione, LocalDate data, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("INSERT INTO Tirocinio(Nome,Descrizione,Data,Login) VALUES ('"+nome+"','"+descrizione+"','"+data+"','"+docente+"')").close();
            return true;
        } catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }

    public Boolean isCoordinatore(String docente, ConnessioneDatabase conn) {
        Boolean result = null;
        try{
            ResultSet x=conn.executeQuery("SELECT Coordinatore FROM Docente WHERE Docente.Login = '"+docente+"'");
            if(x.next()){
                result=x.getBoolean("Coordinatore");
            }
        } catch(SQLException e){
            handleSQLException(e);
        }
        return result;
    }
    public Boolean setCoordinatore(boolean x, String docente, boolean coordinatore, ConnessioneDatabase conn) {
        try {
            if(x)
                conn.executeQuery("UPDATE Docente SET Coordinatore=TRUE WHERE Docente.Login = '"+docente+"'").close();
            else
                conn.executeQuery("UPDATE Docente SET Coordinatore=FALSE WHERE Docente.Login = '"+docente+"'").close();
            return true;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public ResultSet getAllTirocinio(String docente, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Tirocinio WHERE Tirocinio.Login = '"+docente+"'");
        } catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }
    public ResultSet getAllSeduta(String docente, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Seduta WHERE Seduta.Login = '"+docente+"'");
        } catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }
    public ResultSet getAll(ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Docente");
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }

    public Boolean setPassword(String username, String password, ConnessioneDatabase conn) {

        try{
            conn.executeQuery("UPDATE Docente set Password = '"+password+"' WHERE Docente.Login = '"+username+"'").close();
            return true;
        }
        catch(SQLException e){
            handleSQLException(e);
        }

        return false;
    }
    public Boolean setUsername(String oldusername, String newusername, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Docente set Login = '"+newusername+"' WHERE Docente.Login = '"+oldusername+"'");
            return true;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    public Boolean login(String username, String password, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Docente set Stato = TRUE WHERE Docente.Login = '"+username+"' AND Password = '"+password+"'").close();
            return true;
        }
        catch(SQLException e) {
            handleSQLException(e);
        }
        return false;
    }
    public Boolean logout(String username, ConnessioneDatabase conn) {

        try{
            conn.executeQuery("UPDATE Docente set Stato = FALSE WHERE Docente.Login = '"+username+"'").close();
            return true;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return false;
    }
    //MAY BE NULL
    public String getNome(String username, ConnessioneDatabase conn) {
        try{
            String s=null;
            ResultSet x= conn.executeQuery("SELECT Nome FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s = x.getString("Nome");
            x.close();
            return s;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        
        return null;
    }
    //MAY BE NULL
    public String getCognome(String username, ConnessioneDatabase conn) {

        try{
            String s=null;
            ResultSet x=conn.executeQuery("SELECT Cognome FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s=x.getString("Cognome");
            x.close();
            return s;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }
    //MAY BE NULL
    public String getEmail(String username, ConnessioneDatabase conn) {
        try{
            String s=null;
            ResultSet x=conn.executeQuery("SELECT Email FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s=x.getString("Email");
            x.close();
            return s;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }
    //MAY BE NULL
    public String getPassword(String username, ConnessioneDatabase conn) {
        try{
            String s=null;
            ResultSet x=conn.executeQuery("SELECT Password FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s = x.getString("Password");
            x.close();
            return s;
        }
        catch(SQLException e){
            handleSQLException(e);
        }
        return null;
    }
}

