package model;

import java.time.LocalDate;

public class Richiesta {
    private char stato='?';
    private LocalDate data;
    private Studente studente;
    private Tirocinio tirocinio;
    public Richiesta(LocalDate data,Studente studente,Tirocinio tirocinio) {
        this.data = data;
        this.studente = studente;
        this.tirocinio = tirocinio;
    }

    public Tirocinio getTirocinio() {
        return tirocinio;
    }

    public char getStato() {
        return stato;
    }
    public LocalDate getData() {
        return data;
    }
    public Studente getStudente() {
        return studente;
    }
    public void setStato(char stato) {
        this.stato = stato;
    }
    public String toString(){
        return ("Richiesta del "+data.toString()+":"+tirocinio.toString()+" stato:"+stato+"\n");
    }

}
