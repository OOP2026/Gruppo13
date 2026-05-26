package model;
import java.util.ArrayList;

public class Docente extends Utente{
    protected boolean coordinatore;

    protected ArrayList<Tirocinio> Tirocini = new ArrayList<>();

    public Docente(String nome, String cognome, String password, String login,String  email, boolean coordinatore){
        super(nome, cognome, password, login, email);
        this.coordinatore = coordinatore;
    }
    public void accettaTesi(Tesi t){
        t.setStato('V');
    }

    public void rifiutaTesi(Tesi t){
        t.setStato('X');
    }

    public Tirocinio aggiungiTirocinio(String nome, String descrizione){
        TirocinioInterno t = new TirocinioInterno(nome, descrizione, this);
        Tirocini.add(t);
        return t;
    }
    public Tirocinio aggiungiTirocinio(String nome, String descrizione, String nomeAzienda, String referente){
        TirocinioEsterno t = new TirocinioEsterno(nome, descrizione, this, nomeAzienda, referente);
        Tirocini.add(t);
        return t;
    }
    public Boolean isCoordinatore(){
        return coordinatore;
    }
    public ArrayList<Tirocinio> visualizzaTirocini(){
        return Tirocini;
    }
    public boolean equals(Docente obj) {
        if (obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email))
            return true;
        return false;
    }

}
