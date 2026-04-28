package PrimoHomework;

public class Docente extends Utente{
    protected Boolean coordinatore;

    public Docente(String nome, String cognome, String password, String login,String  email, Boolean coordinatore){
        super(nome, cognome, password, login, email);
        this.coordinatore = coordinatore;
    }
    public void accettaTesi(Tesi t){
        Tesi.setStato('V');
    }

    public void rifiutaTesi(Tesi t){
        Tesi.setStato('X');
    }

    public Tirocinio aggiungiTirocinio(String nome, String descrizione){
        TirocinioInterno t = new TirocinioInterno(nome, descrizione, this);
        return t;
    }
    public Tirocinio aggiungiTirocinio(String nome, String descrizione, String nomeAzienda, String referente){
        TirocinioEsterno t = new TirocinioEsterno(nome, descrizione, this, nomeAzienda, referente);
        return t;
    }
    public Boolean isCoordinatore(){
        return coordinatore;
    }
}
