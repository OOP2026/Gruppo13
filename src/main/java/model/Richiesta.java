package model;

import java.util.Date;

public class Richiesta {
    private char stato;
    private Date data;
    private Studente studente;
    private Tesi tesi;
    public Richiesta(char stato, Date data,Studente studente) {
        this.stato = stato;
        this.data = data;
        this.studente = studente;
    }
    public char getStato() {
        return stato;
    }
    public Date getData() {
        return data;
    }
    public Studente getStudente() {
        return studente;
    }
    public void setStato(char stato) {
        this.stato = stato;
    }

    public Tesi getTesi() {
        return tesi;
    }
    public void setTesi(Tesi tesi) {
        this.tesi = tesi;
    }

}
