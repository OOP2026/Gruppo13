package model;

import java.time.LocalDate;

public class Tirocinio {
    protected LocalDate data;
    protected String nome;
    protected String descrizione;
    protected Docente relatore;
    public Tirocinio(String nome, String descrizione, Docente relatore, LocalDate data) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.relatore = relatore;
        this.data = data;
    }
    public String getNome() {return this.nome;}
    public String getDescrizione() {return this.descrizione;}
    public Docente getRelatore(){return this.relatore;}
    public  LocalDate getData() {return this.data;}
    public void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }
    @Override
    public String toString() {
        return "Tirocinio "+nome+":\n"+descrizione+"\nRelatore:"+relatore+"\n";
    }
}
