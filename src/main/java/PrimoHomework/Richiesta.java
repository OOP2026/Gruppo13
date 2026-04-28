package PrimoHomework;

import java.util.Date;

public class Richiesta {
    private char stato;
    private Date data;
    private Studente studente;
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

}
