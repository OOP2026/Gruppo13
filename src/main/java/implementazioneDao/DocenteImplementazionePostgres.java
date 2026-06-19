package implementazioneDao;

import dao.DocenteDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DocenteImplementazionePostgres implements DocenteDAO {
    public boolean accettaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn)throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Richiesta SET Richiesta.Stato = 'V' WHERE Richiesta.Login = "+studente+" AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ="+nometirocinio+" AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = "+docente+")");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean rifiutaRichiesta(String studente, String nometirocinio, LocalDate datatirocinio,String docente,Connection conn) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Richiesta SET Richiesta.Stato = 'X' WHERE Richiesta.Login = "+studente+" AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ="+nometirocinio+" AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = "+docente+")");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean rifiutaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Tesi SET Tesi.Stato = 'X' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean accettaTesi(String studente, String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE Tesi SET Tesi.Stato = 'V' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = '"+studente+"' AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean aggiungiSedutaDiLaurea(LocalDate data, LocalTime ora, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Seduta() VALUES ('"+data+"','"+docente+"')");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean aggiungiVotoSedutaDiLaurea(int voto, LocalDate data,LocalTime ora, String docente,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Seduta SET VotoFinale = '"+voto+"' WHERE Seduta.Data = '"+data.toString()+"' AND Seduta.Ora ='"+ora.toString()+"' AND Seduta.Login = '"+docente+"'");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean aggiungiTirocinio(String nome,String descrizione, LocalDate data,String docente,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("INSERT INTO Tirocinio(Nome,Descrizione,Data,Login) VALUES ('"+nome+"','"+descrizione+"','"+data.toString()+"','"+docente+"')");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean isCoordinatore(String docente,Connection conn) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("SELECT Coordinatore FROM Docente WHERE Docente.Login = '"+docente+"'");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;

    }
    public boolean setCoordinatore(boolean x,String docente, boolean coordinatore,Connection conn) throws SQLException{
        PreparedStatement stmt;
        if(x)
            stmt=conn.prepareStatement("UPDATE Docente SET Coordinatore=TRUE WHERE Docente.Login = '"+docente+"'");
        else
            stmt=conn.prepareStatement("UPDATE Docente SET Coordinatore=FALSE WHERE Docente.Login = '"+docente+"'");
        try{
            stmt.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public ResultSet getAllTirocinio(String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Tirocinio WHERE Tirocinio.Login = '"+docente+"'");
        try{
            return stmt.executeQuery();
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    public ResultSet getAllSeduta(String docente,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Seduta WHERE Seduta.Login = '"+docente+"'");
        try{
            return stmt.executeQuery();
        } catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    public ResultSet getAllDocente(Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Docente");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    public ResultSet queryViaUtente(String query,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement(query);
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    public boolean setPassword(String username, String password,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Docente set Password = '"+password+"' WHERE Docente.Login = '"+username+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean setUsername(String oldusername, String newusername,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Docente set Login = '"+newusername+"' WHERE Docente.Login = '"+oldusername+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean login(String username,String password,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Docente set Stato = TRUE WHERE Docente.Login = '"+username+"' AND Password = '"+password+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    public boolean logout(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Docente set Stato = FALSE WHERE Docente.Login = '"+username+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return false;
    }
    //MAY BE NULL
    public String getNome(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Nome FROM Docente WHERE Docente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Nome");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    //MAY BE NULL
    public String getCognome(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Cognome FROM Docente WHERE Docente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Cognome");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    //MAY BE NULL
    public String getEmail(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Email FROM Docente WHERE Docente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Email");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
    //MAY BE NULL
    public String getPassword(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Password FROM Docente WHERE Docente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Password");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        finally {
            stmt.close();
        }
        return null;
    }
}
