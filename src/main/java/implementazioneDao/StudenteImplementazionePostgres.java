package implementazioneDao;

import dao.StudenteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StudenteImplementazionePostgres implements StudenteDAO {
    public ResultSet queryViaUtente(String query,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement(query);
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return null;
        }
    }
    public boolean setPassword(String username, String password,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Studente set Password = '"+password+"' WHERE Docente.Login = '"+username+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    public boolean setUsername(String oldusername, String newusername,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Studente set Login = '"+newusername+"' WHERE Studente.Login = '"+oldusername+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    public boolean login(String username,String password,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Studente set Stato = TRUE WHERE Studente.Login = '"+username+"' AND Password = '"+password+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    public boolean logout(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Studente set Stato = FALSE WHERE Studente.Login = '"+username+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    //MAY BE NULL
    public String getNome(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Nome FROM Studente WHERE Studente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Nome");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    //MAY BE NULL
    public String getCognome(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Cognome FROM Studente WHERE Studente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Cognome");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    //MAY BE NULL
    public String getEmail(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Email FROM Studente WHERE Studente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Email");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    //MAY BE NULL
    public String getPassword(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Password FROM Studente WHERE Studente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Password");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public String getMatricola(String username,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT Matricola FROM Studente WHERE Studente.Login = '"+username+"'");
        try{
            ResultSet x=stmt.executeQuery();
            if(x.next())
                return x.getString("Password");
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public boolean aggiungiTesi(String matricola, String contenuto, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("INSERT INTO Tesi(Contenuto,ID_Ri) VALUES ('"+contenuto+"', (SELECT ID_Ri FROM Richiesta R JOIN Tirocinio T ON T.ID_Ti=R.ID_Ti WHERE T.Nome='"+nometirocinio+"' AND T.Data='"+datatirocinio.toString()+"'AND T.Login = '"+docente+"')))");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    public boolean aggiungiRichiesta(String matricola, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn)throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("INSERT INTO Richiesta(Login,Data,ID_Ti) VALUES ((SELECT Login FROM Studente WHERE Matricola ='"+matricola+"'),'"+LocalDate.now().toString()+"',(SELECT ID_Ti FROM Tirocinio T WHERE T.Nome='"+nometirocinio+"' AND T.Data='"+datatirocinio.toString()+"' AND T.Login='"+docente+"'))");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    public boolean aggiornaTesi(String matricola,String contenuto,String nometirocinio, LocalDate datatirocinio, String docente,Connection conn) throws SQLException{
           PreparedStatement stmt = conn.prepareStatement("UPDATE Tesi SET Tesi.Contenuto = '"+contenuto+"' WHERE ID_Ri=(Select ID_Ri from Richiesta WHERE Richiesta.Login = (SELECT Login FROM Studente S WHERE S.Matricola='"+matricola+"') AND Richiesta.ID_Ti = (SELECT ID_Ti FROM Tirocinio WHERE Tirocinio.Nome ='"+nometirocinio+"' AND Tirocinio.data = '"+datatirocinio.toString()+"' AND Tirocinio.Login = '"+docente+"'))");
           try{
               stmt.executeQuery();
               return true;
            }
           catch(SQLException e){
                System.out.println("Errore nell'esecuzione della query\n");
                e.printStackTrace();
                return false;
            }
    }
    public boolean prenotaSedutaDiLaurea(String matricola, LocalDate data, String nometirocinio, LocalDate datatirocinio, String docente, Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("UPDATE Seduta SET ID_Te=(SELECT ID_Te FROM Tesi WHERE ID_Ri = (SELECT ID_RI FROM Richiesta WHERE Login='"+matricola+"' AND ID_Ti=(SELECT ID_Ti FROM Tirocinio WHERE Nome='"+nometirocinio+"' AND Data='"+datatirocinio.toString()+"' AND Login='"+docente+"'))) WHERE Login='"+docente+"' AND Data='"+data.toString()+"'");
        try{
            stmt.executeQuery();
            return true;
        }
        catch(SQLException e) {
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
            return false;
        }
    }
    public ResultSet getAllTesi(String matricola,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Tesi WHERE ID_Ri=ANY(SELECT ID_Ri from Richiesta WHERE Login=(SELECT Login FROM Studente WHERE Matricola ='"+matricola+"'))");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllRichiesta(String matricola,Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * from Richiesta WHERE Login=(SELECT Login FROM Studente WHERE Matricola ='"+matricola+"')");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllStudente(Connection conn) throws SQLException{
        PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Studente");
        try{
            return stmt.executeQuery();
        }
        catch(SQLException e){
            System.out.println("Errore nell'esecuzione della query\n");
            e.printStackTrace();
        }
        return null;
    }
}
