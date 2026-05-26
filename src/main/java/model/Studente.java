package model;

import java.util.Date;

public class Studente extends Utente {
    private String matricola;

    public Studente(String nome, String cognome, String password, String login, String email, String matricola) {
        super(nome, cognome, password, login, email);
        this.matricola = matricola;
    }

    public Richiesta faiRichiesta(char stato, Date data, Tirocinio tirocinio) {
        return new Richiesta(stato,data,this,tirocinio);
    }

    public String getMatricola() {
        return matricola;
    }

    public Tesi aggiungiTesi(Richiesta r,char stato,String contenuto){
        if (r!=null){
            return new Tesi(stato,contenuto,r);
        }
        return null;
    }
    public boolean aggiornaTesi(Tesi t,Richiesta r,String contenuto){
        if (r.getStudente().equals(this)){
            t.setContenuto(contenuto);
            t.setStato('?');
            return true;
        }
        return false;
    }
    public void visualizzaRichiesta(Richiesta r){
        if(r.getStudente().equals(this)){
            System.out.println("Stato richiesta: "+r.getStato());
        }
    }
    public boolean equals(Studente obj) {
        if (obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email) && obj.getMatricola().equals(matricola))
            return true;
        return false;
    }

}
