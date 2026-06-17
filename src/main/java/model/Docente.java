package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Docente extends Utente{
    protected boolean coordinatore;
    public Docente(String nome, String cognome, String password, String login,String  email, boolean coordinatore){
        super(nome, cognome, password, login, email);
        this.coordinatore = coordinatore;
    }
    public boolean accettaTesi(Tesi t){
        if (t.getRichiesta().getTirocinio().getRelatore().equals(this)){
            t.setStato('V');
            return true;
        }
        return false;
    }

    public boolean rifiutaTesi(Tesi t){
        if (t.getRichiesta().getTirocinio().getRelatore().equals(this)){
            t.setStato('X');
            return true;
        }
        return false;
    }

    public Tirocinio aggiungiTirocinio(String nome, String descrizione,LocalDate data){
        return new TirocinioInterno(nome, descrizione, this,data);
    }
    public Tirocinio aggiungiTirocinio(String nome, String descrizione, LocalDate data, String nomeAzienda, String referente){
        return new TirocinioEsterno(nome, descrizione, this,data, nomeAzienda, referente);
    }
    public Boolean isCoordinatore(){
        return coordinatore;
    }
    public void setCoordinatore(boolean coordinatore){
        this.coordinatore = coordinatore;
    }
        public boolean equals(Docente obj) {
        if (obj.getClass()==Docente.class && obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email))
            return true;
        return false;
    }
    @Override
    public String toString() {
        String s="Docente: " + nome + " " +  cognome+ " ";
        if (coordinatore)
            s=s.concat("Coordinatore");
        return s.concat("\n");
    }

    public boolean accettaRichiesta(Richiesta richiesta) {
        if (richiesta.getTirocinio().getRelatore().equals(this)){
            richiesta.setStato('V');
            return true;
        }
        return false;
    }
    public boolean rifiutaRichiesta(Richiesta richiesta) {
        if (richiesta.getTirocinio().getRelatore().equals(this)){
            richiesta.setStato('X');
            return true;
        }
        return false;
    }
    public String visualizzaRichiesta(Richiesta richiesta){
        if(richiesta.getTirocinio().getRelatore().equals(this))
            return richiesta.toString();
        return "";
    }
    public String visualizzaTesi(Tesi tesi) {
        if (tesi.getRichiesta().getTirocinio().getRelatore().equals(this)){
            return tesi.toString();
        }
        return "";
    }
    public String visualizzaSedutaDiLaurea(Seduta seduta) {
        if (seduta.getTesi().getRichiesta().getTirocinio().getRelatore().equals(this)){
            return seduta.toString();
        }
        return null;
    }

    public Seduta aggiungiSedutaDiLaurea(LocalDate data, LocalTime ora){
        return new Seduta(data,ora,this);
    }

    public boolean aggiungiVotoSedutaDiLaurea(Seduta seduta, int v){
        if(seduta.getDocente().equals(this)&&seduta.getTesi()!=null){
            seduta.setVoto(v);
            return true;
        }
        return false;
    }


}
