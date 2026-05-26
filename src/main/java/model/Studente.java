package model;

import java.util.Date;

public class Studente extends Utente {
    private String matricola;

    public Studente(String nome, String cognome, String password, String login, String email, String matricola) {
        super(nome, cognome, password, login, email);
        this.matricola = matricola;
    }

    public Richiesta faiRichiesta(Date data, Tirocinio tirocinio) {
        if(tirocinio!=null)
            return new Richiesta('?',data,this,tirocinio);
        return null;
    }

    public String getMatricola() {
        return matricola;
    }

    public Tesi aggiungiTesi(Richiesta r,String contenuto){
        if (r!=null){
            return new Tesi('?',contenuto,r);
        }
        return null;
    }

    public String visualizzaRichiesta(Richiesta richiesta){
        if(richiesta.getStudente().equals(this)){
            return ("Stato richiesta: "+richiesta.getStato());
        }
        return "";
    }
    public boolean equals(Studente obj) {
        if (obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email) && obj.getMatricola().equals(matricola))
            return true;
        return false;
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
}
