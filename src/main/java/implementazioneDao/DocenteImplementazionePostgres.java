package implementazioneDao;

import dao.DocenteDAO;
import database_connection.ConnessioneDatabase;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DocenteImplementazionePostgres implements DocenteDAO {
    public Boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, ConnessioneDatabase conn){
        try{
            conn.executeQuery("UPDATE Richiesta SET Richiesta.Stato = 'V' WHERE Richiesta.Login = "+studente+" AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ="+nometirocinio+" AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = "+docente+")").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        
            
        
        return false;
    }
    public Boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio,String docente,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Richiesta SET Richiesta.Stato = 'X' WHERE Richiesta.Login = "+studente+" AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ="+nometirocinio+" AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = "+docente+")").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Tesi SET Tesi.Stato = 'X' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Tesi SET Tesi.Stato = 'V' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean aggiungiSedutaDiLaurea(LocalDate data, LocalTime ora, String docente, ConnessioneDatabase conn) {
        try{
            conn.executeQuery("INSERT INTO Seduta() VALUES ('"+data+"','"+docente+"')").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean aggiungiVotoSedutaDiLaurea(int voto, LocalDate data,LocalTime ora, String docente,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Seduta SET VotoFinale = '"+voto+"' WHERE Seduta.Data = '"+data.toString()+"' AND Seduta.Ora ='"+ora.toString()+"' AND Seduta.Login = '"+docente+"'").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean aggiungiTirocinio(String nome,String descrizione, LocalDate data,String docente,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("INSERT INTO Tirocinio(Nome,Descrizione,Data,Login) VALUES ('"+nome+"','"+descrizione+"','"+data.toString()+"','"+docente+"')").close();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    @Nullable
    public Boolean isCoordinatore(String docente,ConnessioneDatabase conn) {
        try{
            Boolean y=null;
            ResultSet x=conn.executeQuery("SELECT Coordinatore FROM Docente WHERE Docente.Login = '"+docente+"'");
            if(x.next()){
                y=x.getBoolean("Coordinatore");
            }
            return y;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;

    }
    public Boolean setCoordinatore(boolean x,String docente, boolean coordinatore,ConnessioneDatabase conn) {
        try {
            if(x)
                conn.executeQuery("UPDATE Docente SET Coordinatore=TRUE WHERE Docente.Login = '"+docente+"'").close();
            else
                conn.executeQuery("UPDATE Docente SET Coordinatore=FALSE WHERE Docente.Login = '"+docente+"'").close();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getAllTirocinio(String docente, ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Tirocinio WHERE Tirocinio.Login = '"+docente+"'");
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllSeduta(String docente,ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Seduta WHERE Seduta.Login = '"+docente+"'");
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllDocente(ConnessioneDatabase conn) {
        try{
            return conn.executeQuery("SELECT * FROM Docente");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet queryViaUtente(String query,ConnessioneDatabase conn) {
        try{
            return conn.executeQuery(query);
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        
            
        
        return null;
    }
    public Boolean setPassword(String username, String password,ConnessioneDatabase conn) {

        try{
            conn.executeQuery("UPDATE Docente set Password = '"+password+"' WHERE Docente.Login = '"+username+"'").close();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        
            
        
        return false;
    }
    public Boolean setUsername(String oldusername, String newusername,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Docente set Login = '"+newusername+"' WHERE Docente.Login = '"+oldusername+"'");
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean login(String username,String password,ConnessioneDatabase conn) {
        try{
            conn.executeQuery("UPDATE Docente set Stato = TRUE WHERE Docente.Login = '"+username+"' AND Password = '"+password+"'").close();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    public Boolean logout(String username,ConnessioneDatabase conn) {

        try{
            conn.executeQuery("UPDATE Docente set Stato = FALSE WHERE Docente.Login = '"+username+"'").close();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return false;
    }
    //MAY BE NULL
    public String getNome(String username,ConnessioneDatabase conn) {
        try{
            String s=null;
            ResultSet x= conn.executeQuery("SELECT Nome FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s = x.getString("Nome");
            x.close();
            return s;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        
        return null;
    }
    //MAY BE NULL
    public String getCognome(String username,ConnessioneDatabase conn) {

        try{
            String s=null;
            ResultSet x=conn.executeQuery("SELECT Cognome FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s=x.getString("Cognome");
            x.close();
            return s;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        
            
        return null;
    }
    //MAY BE NULL
    public String getEmail(String username,ConnessioneDatabase conn) {
        try{
            String s=null;
            ResultSet x=conn.executeQuery("SELECT Email FROM Docente WHERE Docente.Login = '"+username+"'");
            if(x.next())
                s=x.getString("Email");
            x.close();
            return s;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
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
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
}

