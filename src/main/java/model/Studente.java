package model;

import java.util.ArrayList;
import java.util.Date;

public class Studente extends Utente {
    private String matricola;
    private ArrayList<Richiesta> richieste = new ArrayList<>();

    public Studente(String nome, String cognome, String password, String login, String email, String matricola) {
        super(nome, cognome, password, login, email);
        this.matricola = matricola;
    }

    public Richiesta faiRichiesta(char stato, Date data, Tirocinio tirocinio) {
        Richiesta r=new Richiesta(stato,data,this,tirocinio);
        richieste.add(r);
        return r;
    }

    public String getMatricola() {
        return matricola;
    }

    public boolean aggiungiTesi(Richiesta r,char stato,String contenuto){
        if (richieste.contains(r)){
            Tesi t =new Tesi(stato,contenuto);
            r.setTesi(t);
            return true;
        }
        return false;
    }
    public boolean aggiornaTesi(Richiesta r,String contenuto){
        Tesi t=r.getTesi();
        if (richieste.contains(r)&&t!=null){
            t.setContenuto(contenuto);
            t.setStato('?');
            return true;
        }
        return false;
    }
    public void visualizzaRichiesta(Richiesta r){
        if(richieste.contains(r)){
            System.out.println("Stato richiesta: "+r.getStato());
        }
    }
    public boolean equals(Studente obj) {
        if (obj.getCognome().equals(cognome) && obj.getNome().equals(nome) && obj.getPassword().equals(password) && obj.getLogin().equals(login) && obj.getEmail().equals(email) && obj.getMatricola().equals(matricola))
            return true;
        return false;
    }

}
