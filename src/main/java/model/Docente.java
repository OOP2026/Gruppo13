package model;

public class Docente extends Utente{
    protected boolean coordinatore;
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
        return new TirocinioInterno(nome, descrizione, this);
    }
    public Tirocinio aggiungiTirocinio(String nome, String descrizione, String nomeAzienda, String referente){
        return new TirocinioEsterno(nome, descrizione, this, nomeAzienda, referente);
    }
    public Boolean isCoordinatore(){
        return coordinatore;
    }
    public void setCoordinatore(boolean coordinatore){
        this.coordinatore = coordinatore;
    }
    public boolean equals(Docente obj) {
        if (obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email))
            return true;
        return false;
    }
    @Override
    public String toString() {
        String s="Docente: " + nome + " " +  cognome+ " ");
        if (coordinatore)
            s=s.concat("Coordinatore");
        return s.concat("\n");
    }

}
