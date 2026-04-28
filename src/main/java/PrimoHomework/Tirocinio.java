package PrimoHomework;

public class Tirocinio {
    protected String nome;
    protected String descrizione;
    protected Docente relatore;
    protected Tirocinio(String nome, String descrizione,Docente relatore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.relatore = relatore;
    }
    public String getNome() {return this.nome;}
    public String getDescrizione() {return this.descrizione;}
    public Docente getRelatore(){return this.relatore;}
    
}
