package model;

public class Tirocinio {
    protected String nome;
    protected String descrizione;
    protected Docente relatore;
    public Tirocinio(String nome, String descrizione, Docente relatore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.relatore = relatore;
    }
    public String getNome() {return this.nome;}
    public String getDescrizione() {return this.descrizione;}
    public Docente getRelatore(){return this.relatore;}

    @Override
    public String toString() {
        return "Tirocinio "+nome+":\n"+descrizione+"\nRelatore:"+relatore+"\n";
    }
}
