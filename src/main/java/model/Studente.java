package model;

import java.time.*;

public class Studente extends Utente {
    private String matricola;

    public Studente(String nome, String cognome, String password, String login, String email, String matricola) {
        super(nome, cognome, password, login, email);
        this.matricola = matricola;
    }

    public Richiesta faiRichiesta(LocalDate data, Tirocinio tirocinio) {
        if(tirocinio!=null)
            return new Richiesta(data,this,tirocinio);
        return null;
    }

    public String getMatricola() {
        return matricola;
    }

    public Tesi aggiungiTesi(Richiesta r,String contenuto){
        if (r!=null){
            return new Tesi(contenuto,r);
        }
        return null;
    }

    public String visualizzaRichiesta(Richiesta richiesta){
        if(richiesta.getStudente().equals(this)){
            return ("Stato richiesta: "+richiesta.getStato());
        }
        return "";
    }
    public boolean isSameStudente(Studente obj) {
        return (obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email) && obj.getMatricola().equals(matricola));
    }

    public boolean aggiornaTesi(Tesi tesi,String contenuto) {
        if (tesi.getRichiesta().getStudente().equals(this)){
            tesi.setContenuto(contenuto);
            tesi.setStato('?');
            return true;
        }
        return false;
    }

    public String visualizzaTesi(Tesi tesi) {
        if (tesi.getRichiesta().getStudente().equals(this)){
            return tesi.toString();
        }
        return "";
    }

    public boolean prenotaSedutadiLaurea(Seduta s, Tesi t) {
        if(t!=null&&t.getRichiesta().getStudente().equals(this)&&t.getStato()=='V'){
            s.setTesi(t);
            return true;
        }
        return false;
    }

    public String visualizzaSedutaDiLaurea(Seduta s){
        if(s.getTesi()!=null||s.getTesi().getRichiesta().getStudente().equals(this)){
            return s.toString();
        }
        return null;
    }

}
